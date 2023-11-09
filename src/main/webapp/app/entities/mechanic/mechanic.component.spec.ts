/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Mechanic from './mechanic.vue';
import MechanicService from './mechanic.service';
import AlertService from '@/shared/alert/alert.service';

type MechanicComponentType = InstanceType<typeof Mechanic>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Mechanic Management Component', () => {
    let mechanicServiceStub: SinonStubbedInstance<MechanicService>;
    let mountOptions: MountingOptions<MechanicComponentType>['global'];

    beforeEach(() => {
      mechanicServiceStub = sinon.createStubInstance<MechanicService>(MechanicService);
      mechanicServiceStub.retrieve.resolves({ headers: {} });

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
          mechanicService: () => mechanicServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        mechanicServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Mechanic, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(mechanicServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.mechanics[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: MechanicComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Mechanic, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        mechanicServiceStub.retrieve.reset();
        mechanicServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        mechanicServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeMechanic();
        await comp.$nextTick(); // clear components

        // THEN
        expect(mechanicServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(mechanicServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
