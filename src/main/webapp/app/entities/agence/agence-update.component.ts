import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AgenceService from './agence.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IAgence, Agence } from '@/shared/model/agence.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AgenceUpdate',
  setup() {
    const agenceService = inject('agenceService', () => new AgenceService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const agence: Ref<IAgence> = ref(new Agence());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveAgence = async agenceId => {
      try {
        const res = await agenceService().find(agenceId);
        agence.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.agenceId) {
      retrieveAgence(route.params.agenceId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nameAgence: {},
      adressAgence: {},
      telAgence: {},
      cars: {},
    };
    const v$ = useVuelidate(validationRules, agence as any);
    v$.value.$validate();

    return {
      agenceService,
      alertService,
      agence,
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
      if (this.agence.id) {
        this.agenceService()
          .update(this.agence)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('voitureJhipsteRApp.agence.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.agenceService()
          .create(this.agence)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('voitureJhipsteRApp.agence.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
