/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AgenceDetails from './agence-details.vue';
import AgenceService from './agence.service';
import AlertService from '@/shared/alert/alert.service';

type AgenceDetailsComponentType = InstanceType<typeof AgenceDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const agenceSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Agence Management Detail Component', () => {
    let agenceServiceStub: SinonStubbedInstance<AgenceService>;
    let mountOptions: MountingOptions<AgenceDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      agenceServiceStub = sinon.createStubInstance<AgenceService>(AgenceService);

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
          agenceService: () => agenceServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        agenceServiceStub.find.resolves(agenceSample);
        route = {
          params: {
            agenceId: '' + 123,
          },
        };
        const wrapper = shallowMount(AgenceDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.agence).toMatchObject(agenceSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        agenceServiceStub.find.resolves(agenceSample);
        const wrapper = shallowMount(AgenceDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
