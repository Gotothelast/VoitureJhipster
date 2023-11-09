/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AgenceUpdate from './agence-update.vue';
import AgenceService from './agence.service';
import AlertService from '@/shared/alert/alert.service';

type AgenceUpdateComponentType = InstanceType<typeof AgenceUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const agenceSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<AgenceUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Agence Management Update Component', () => {
    let comp: AgenceUpdateComponentType;
    let agenceServiceStub: SinonStubbedInstance<AgenceService>;

    beforeEach(() => {
      route = {};
      agenceServiceStub = sinon.createStubInstance<AgenceService>(AgenceService);
      agenceServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          agenceService: () => agenceServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(AgenceUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.agence = agenceSample;
        agenceServiceStub.update.resolves(agenceSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(agenceServiceStub.update.calledWith(agenceSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        agenceServiceStub.create.resolves(entity);
        const wrapper = shallowMount(AgenceUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.agence = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(agenceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        agenceServiceStub.find.resolves(agenceSample);
        agenceServiceStub.retrieve.resolves([agenceSample]);

        // WHEN
        route = {
          params: {
            agenceId: '' + agenceSample.id,
          },
        };
        const wrapper = shallowMount(AgenceUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.agence).toMatchObject(agenceSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        agenceServiceStub.find.resolves(agenceSample);
        const wrapper = shallowMount(AgenceUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
