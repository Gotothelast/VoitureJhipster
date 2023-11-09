/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MechanicUpdate from './mechanic-update.vue';
import MechanicService from './mechanic.service';
import AlertService from '@/shared/alert/alert.service';

type MechanicUpdateComponentType = InstanceType<typeof MechanicUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const mechanicSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MechanicUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Mechanic Management Update Component', () => {
    let comp: MechanicUpdateComponentType;
    let mechanicServiceStub: SinonStubbedInstance<MechanicService>;

    beforeEach(() => {
      route = {};
      mechanicServiceStub = sinon.createStubInstance<MechanicService>(MechanicService);
      mechanicServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          mechanicService: () => mechanicServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(MechanicUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.mechanic = mechanicSample;
        mechanicServiceStub.update.resolves(mechanicSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(mechanicServiceStub.update.calledWith(mechanicSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        mechanicServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MechanicUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.mechanic = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(mechanicServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        mechanicServiceStub.find.resolves(mechanicSample);
        mechanicServiceStub.retrieve.resolves([mechanicSample]);

        // WHEN
        route = {
          params: {
            mechanicId: '' + mechanicSample.id,
          },
        };
        const wrapper = shallowMount(MechanicUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.mechanic).toMatchObject(mechanicSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        mechanicServiceStub.find.resolves(mechanicSample);
        const wrapper = shallowMount(MechanicUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
