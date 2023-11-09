import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OptionService from './option.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CarService from '@/entities/car/car.service';
import { type ICar } from '@/shared/model/car.model';
import { type IOption, Option } from '@/shared/model/option.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OptionUpdate',
  setup() {
    const optionService = inject('optionService', () => new OptionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const option: Ref<IOption> = ref(new Option());

    const carService = inject('carService', () => new CarService());

    const cars: Ref<ICar[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveOption = async optionId => {
      try {
        const res = await optionService().find(optionId);
        option.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.optionId) {
      retrieveOption(route.params.optionId);
    }

    const initRelationships = () => {
      carService()
        .retrieve()
        .then(res => {
          cars.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nameOption: {},
      priceOption: {},
      car: {},
    };
    const v$ = useVuelidate(validationRules, option as any);
    v$.value.$validate();

    return {
      optionService,
      alertService,
      option,
      previousState,
      isSaving,
      currentLanguage,
      cars,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.option.id) {
        this.optionService()
          .update(this.option)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('voitureJhipsteRApp.option.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.optionService()
          .create(this.option)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('voitureJhipsteRApp.option.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
