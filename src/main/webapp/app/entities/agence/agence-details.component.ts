import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import AgenceService from './agence.service';
import { type IAgence } from '@/shared/model/agence.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AgenceDetails',
  setup() {
    const agenceService = inject('agenceService', () => new AgenceService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const agence: Ref<IAgence> = ref({});

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

    return {
      alertService,
      agence,

      previousState,
      t$: useI18n().t,
    };
  },
});
