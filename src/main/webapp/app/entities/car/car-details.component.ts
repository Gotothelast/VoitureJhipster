import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CarService from './car.service';
import { type ICar } from '@/shared/model/car.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CarDetails',
  setup() {
    const carService = inject('carService', () => new CarService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const car: Ref<ICar> = ref({});

    const retrieveCar = async carId => {
      try {
        const res = await carService().find(carId);
        car.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.carId) {
      retrieveCar(route.params.carId);
    }

    return {
      alertService,
      car,

      previousState,
      t$: useI18n().t,
    };
  },
});
