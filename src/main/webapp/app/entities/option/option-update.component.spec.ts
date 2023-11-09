/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OptionUpdate from './option-update.vue';
import OptionService from './option.service';
import AlertService from '@/shared/alert/alert.service';

import CarService from '@/entities/car/car.service';

type OptionUpdateComponentType = InstanceType<typeof OptionUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const optionSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<OptionUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Option Management Update Component', () => {
    let comp: OptionUpdateComponentType;
    let optionServiceStub: SinonStubbedInstance<OptionService>;

    beforeEach(() => {
      route = {};
      optionServiceStub = sinon.createStubInstance<OptionService>(OptionService);
      optionServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          optionService: () => optionServiceStub,
          carService: () =>
            sinon.createStubInstance<CarService>(CarService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(OptionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.option = optionSample;
        optionServiceStub.update.resolves(optionSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(optionServiceStub.update.calledWith(optionSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        optionServiceStub.create.resolves(entity);
        const wrapper = shallowMount(OptionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.option = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(optionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        optionServiceStub.find.resolves(optionSample);
        optionServiceStub.retrieve.resolves([optionSample]);

        // WHEN
        route = {
          params: {
            optionId: '' + optionSample.id,
          },
        };
        const wrapper = shallowMount(OptionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.option).toMatchObject(optionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        optionServiceStub.find.resolves(optionSample);
        const wrapper = shallowMount(OptionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
