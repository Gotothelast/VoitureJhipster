/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Car from './car.vue';
import CarService from './car.service';
import AlertService from '@/shared/alert/alert.service';

type CarComponentType = InstanceType<typeof Car>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Car Management Component', () => {
    let carServiceStub: SinonStubbedInstance<CarService>;
    let mountOptions: MountingOptions<CarComponentType>['global'];

    beforeEach(() => {
      carServiceStub = sinon.createStubInstance<CarService>(CarService);
      carServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          carService: () => carServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        carServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Car, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(carServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.cars[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: CarComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Car, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        carServiceStub.retrieve.reset();
        carServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        carServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeCar();
        await comp.$nextTick(); // clear components

        // THEN
        expect(carServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(carServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
