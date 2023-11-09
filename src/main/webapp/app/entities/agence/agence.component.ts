import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import AgenceService from './agence.service';
import { type IAgence } from '@/shared/model/agence.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Agence',
  setup() {
    const { t: t$ } = useI18n();
    const agenceService = inject('agenceService', () => new AgenceService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const agences: Ref<IAgence[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveAgences = async () => {
      isFetching.value = true;
      try {
        const res = await agenceService().retrieve();
        agences.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveAgences();
    };

    onMounted(async () => {
      await retrieveAgences();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IAgence) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeAgence = async () => {
      try {
        await agenceService().delete(removeId.value);
        const message = t$('voitureJhipsteRApp.agence.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveAgences();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      agences,
      handleSyncList,
      isFetching,
      retrieveAgences,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeAgence,
      t$,
    };
  },
});
