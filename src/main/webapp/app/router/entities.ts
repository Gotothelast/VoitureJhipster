import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Car = () => import('@/entities/car/car.vue');
const CarUpdate = () => import('@/entities/car/car-update.vue');
const CarDetails = () => import('@/entities/car/car-details.vue');

const Agence = () => import('@/entities/agence/agence.vue');
const AgenceUpdate = () => import('@/entities/agence/agence-update.vue');
const AgenceDetails = () => import('@/entities/agence/agence-details.vue');

const Mechanic = () => import('@/entities/mechanic/mechanic.vue');
const MechanicUpdate = () => import('@/entities/mechanic/mechanic-update.vue');
const MechanicDetails = () => import('@/entities/mechanic/mechanic-details.vue');

const Option = () => import('@/entities/option/option.vue');
const OptionUpdate = () => import('@/entities/option/option-update.vue');
const OptionDetails = () => import('@/entities/option/option-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'car',
      name: 'Car',
      component: Car,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'car/new',
      name: 'CarCreate',
      component: CarUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'car/:carId/edit',
      name: 'CarEdit',
      component: CarUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'car/:carId/view',
      name: 'CarView',
      component: CarDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'agence',
      name: 'Agence',
      component: Agence,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'agence/new',
      name: 'AgenceCreate',
      component: AgenceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'agence/:agenceId/edit',
      name: 'AgenceEdit',
      component: AgenceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'agence/:agenceId/view',
      name: 'AgenceView',
      component: AgenceDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mechanic',
      name: 'Mechanic',
      component: Mechanic,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mechanic/new',
      name: 'MechanicCreate',
      component: MechanicUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mechanic/:mechanicId/edit',
      name: 'MechanicEdit',
      component: MechanicUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mechanic/:mechanicId/view',
      name: 'MechanicView',
      component: MechanicDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'option',
      name: 'Option',
      component: Option,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'option/new',
      name: 'OptionCreate',
      component: OptionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'option/:optionId/edit',
      name: 'OptionEdit',
      component: OptionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'option/:optionId/view',
      name: 'OptionView',
      component: OptionDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
