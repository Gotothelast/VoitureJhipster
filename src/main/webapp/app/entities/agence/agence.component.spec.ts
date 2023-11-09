/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Agence from './agence.vue';
import AgenceService from './agence.service';
import AlertService from '@/shared/alert/alert.service';

type AgenceComponentType = InstanceType<typeof Agence>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Agence Management Component', () => {
    let agenceServiceStub: SinonStubbedInstance<AgenceService>;
    let mountOptions: MountingOptions<AgenceComponentType>['global'];

    beforeEach(() => {
      agenceServiceStub = sinon.createStubInstance<AgenceService>(AgenceService);
      agenceServiceStub.retrieve.resolves({ headers: {} });

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
          agenceService: () => agenceServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        agenceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Agence, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(agenceServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.agences[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: AgenceComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Agence, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        agenceServiceStub.retrieve.reset();
        agenceServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        agenceServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeAgence();
        await comp.$nextTick(); // clear components

        // THEN
        expect(agenceServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(agenceServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
