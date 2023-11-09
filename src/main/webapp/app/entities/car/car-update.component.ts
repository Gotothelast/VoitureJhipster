import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CarService from './car.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MechanicService from '@/entities/mechanic/mechanic.service';
import { type IMechanic } from '@/shared/model/mechanic.model';
import AgenceService from '@/entities/agence/agence.service';
import { type IAgence } from '@/shared/model/agence.model';
import { type ICar, Car } from '@/shared/model/car.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CarUpdate',
  setup() {
    const carService = inject('carService', () => new CarService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const car: Ref<ICar> = ref(new Car());

    const mechanicService = inject('mechanicService', () => new MechanicService());

    const mechanics: Ref<IMechanic[]> = ref([]);

    const agenceService = inject('agenceService', () => new AgenceService());

    const agences: Ref<IAgence[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      mechanicService()
        .retrieve()
        .then(res => {
          mechanics.value = res.data;
        });
      agenceService()
        .retrieve()
        .then(res => {
          agences.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      carName: {},
      carModel: {},
      carReference: {},
      carYear: {},
      carPrice: {},
      mechanic: {},
      options: {},
      agence: {},
    };
    const v$ = useVuelidate(validationRules, car as any);
    v$.value.$validate();

    return {
      carService,
      alertService,
      car,
      previousState,
      isSaving,
      currentLanguage,
      mechanics,
      agences,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.car.id) {
        this.carService()
          .update(this.car)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('voitureJhipsteRApp.car.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.carService()
          .create(this.car)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('voitureJhipsteRApp.car.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
