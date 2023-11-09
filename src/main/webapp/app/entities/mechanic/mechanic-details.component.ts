import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MechanicService from './mechanic.service';
import { type IMechanic } from '@/shared/model/mechanic.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MechanicDetails',
  setup() {
    const mechanicService = inject('mechanicService', () => new MechanicService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const mechanic: Ref<IMechanic> = ref({});

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

    return {
      alertService,
      mechanic,

      previousState,
      t$: useI18n().t,
    };
  },
});
