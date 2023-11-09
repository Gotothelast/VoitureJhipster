import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MechanicService from './mechanic.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IMechanic, Mechanic } from '@/shared/model/mechanic.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MechanicUpdate',
  setup() {
    const mechanicService = inject('mechanicService', () => new MechanicService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const mechanic: Ref<IMechanic> = ref(new Mechanic());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMechanic = async mechanicId => {
      try {
        const res = await mechanicService().find(mechanicId);
        mechanic.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.mechanicId) {
      retrieveMechanic(route.params.mechanicId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      motor: {},
      power: {},
      km: {},
      car: {},
    };
    const v$ = useVuelidate(validationRules, mechanic as any);
    v$.value.$validate();

    return {
      mechanicService,
      alertService,
      mechanic,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.mechanic.id) {
        this.mechanicService()
          .update(this.mechanic)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('voitureJhipsteRApp.mechanic.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.mechanicService()
          .create(this.mechanic)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('voitureJhipsteRApp.mechanic.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
