import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import CarService from './car.service';
import { type ICar } from '@/shared/model/car.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Car',
  setup() {
    const { t: t$ } = useI18n();
    const carService = inject('carService', () => new CarService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const cars: Ref<ICar[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveCars = async () => {
      isFetching.value = true;
      try {
        const res = await carService().retrieve();
        cars.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveCars();
    };

    onMounted(async () => {
      await retrieveCars();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ICar) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeCar = async () => {
      try {
        await carService().delete(removeId.value);
        const message = t$('voitureJhipsteRApp.car.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveCars();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      cars,
      handleSyncList,
      isFetching,
      retrieveCars,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeCar,
      t$,
    };
  },
});
