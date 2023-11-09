import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MechanicService from './mechanic.service';
import { type IMechanic } from '@/shared/model/mechanic.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Mechanic',
  setup() {
    const { t: t$ } = useI18n();
    const mechanicService = inject('mechanicService', () => new MechanicService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const mechanics: Ref<IMechanic[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMechanics = async () => {
      isFetching.value = true;
      try {
        const res = await mechanicService().retrieve();
        mechanics.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMechanics();
    };

    onMounted(async () => {
      await retrieveMechanics();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMechanic) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMechanic = async () => {
      try {
        await mechanicService().delete(removeId.value);
        const message = t$('voitureJhipsteRApp.mechanic.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMechanics();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      mechanics,
      handleSyncList,
      isFetching,
      retrieveMechanics,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMechanic,
      t$,
    };
  },
});
