/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MechanicDetails from './mechanic-details.vue';
import MechanicService from './mechanic.service';
import AlertService from '@/shared/alert/alert.service';

type MechanicDetailsComponentType = InstanceType<typeof MechanicDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const mechanicSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Mechanic Management Detail Component', () => {
    let mechanicServiceStub: SinonStubbedInstance<MechanicService>;
    let mountOptions: MountingOptions<MechanicDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      mechanicServiceStub = sinon.createStubInstance<MechanicService>(MechanicService);

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
          mechanicService: () => mechanicServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        mechanicServiceStub.find.resolves(mechanicSample);
        route = {
          params: {
            mechanicId: '' + 123,
          },
        };
        const wrapper = shallowMount(MechanicDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.mechanic).toMatchObject(mechanicSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        mechanicServiceStub.find.resolves(mechanicSample);
        const wrapper = shallowMount(MechanicDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
