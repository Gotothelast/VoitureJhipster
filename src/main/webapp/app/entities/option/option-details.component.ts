import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import OptionService from './option.service';
import { type IOption } from '@/shared/model/option.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OptionDetails',
  setup() {
    const optionService = inject('optionService', () => new OptionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const option: Ref<IOption> = ref({});

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

    return {
      alertService,
      option,

      previousState,
      t$: useI18n().t,
    };
  },
});
