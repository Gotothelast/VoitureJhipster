/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Option from './option.vue';
import OptionService from './option.service';
import AlertService from '@/shared/alert/alert.service';

type OptionComponentType = InstanceType<typeof Option>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Option Management Component', () => {
    let optionServiceStub: SinonStubbedInstance<OptionService>;
    let mountOptions: MountingOptions<OptionComponentType>['global'];

    beforeEach(() => {
      optionServiceStub = sinon.createStubInstance<OptionService>(OptionService);
      optionServiceStub.retrieve.resolves({ headers: {} });

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
          optionService: () => optionServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        optionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Option, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(optionServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.options[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: OptionComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Option, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        optionServiceStub.retrieve.reset();
        optionServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        optionServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeOption();
        await comp.$nextTick(); // clear components

        // THEN
        expect(optionServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(optionServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
