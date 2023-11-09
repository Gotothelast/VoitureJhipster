/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OptionDetails from './option-details.vue';
import OptionService from './option.service';
import AlertService from '@/shared/alert/alert.service';

type OptionDetailsComponentType = InstanceType<typeof OptionDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const optionSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Option Management Detail Component', () => {
    let optionServiceStub: SinonStubbedInstance<OptionService>;
    let mountOptions: MountingOptions<OptionDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      optionServiceStub = sinon.createStubInstance<OptionService>(OptionService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          optionService: () => optionServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        optionServiceStub.find.resolves(optionSample);
        route = {
          params: {
            optionId: '' + 123,
          },
        };
        const wrapper = shallowMount(OptionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.option).toMatchObject(optionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        optionServiceStub.find.resolves(optionSample);
        const wrapper = shallowMount(OptionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
