/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CarDetails from './car-details.vue';
import CarService from './car.service';
import AlertService from '@/shared/alert/alert.service';

type CarDetailsComponentType = InstanceType<typeof CarDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const carSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Car Management Detail Component', () => {
    let carServiceStub: SinonStubbedInstance<CarService>;
    let mountOptions: MountingOptions<CarDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      carServiceStub = sinon.createStubInstance<CarService>(CarService);

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
          carService: () => carServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        carServiceStub.find.resolves(carSample);
        route = {
          params: {
            carId: '' + 123,
          },
        };
        const wrapper = shallowMount(CarDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.car).toMatchObject(carSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        carServiceStub.find.resolves(carSample);
        const wrapper = shallowMount(CarDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
