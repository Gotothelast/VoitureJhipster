import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import OptionService from './option.service';
import { type IOption } from '@/shared/model/option.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Option',
  setup() {
    const { t: t$ } = useI18n();
    const optionService = inject('optionService', () => new OptionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const options: Ref<IOption[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveOptions = async () => {
      isFetching.value = true;
      try {
        const res = await optionService().retrieve();
        options.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveOptions();
    };

    onMounted(async () => {
      await retrieveOptions();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IOption) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeOption = async () => {
      try {
        await optionService().delete(removeId.value);
        const message = t$('voitureJhipsteRApp.option.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveOptions();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      options,
      handleSyncList,
      isFetching,
      retrieveOptions,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeOption,
      t$,
    };
  },
});
