import { defineComponent, provide } from 'vue';

import CarService from './car/car.service';
import AgenceService from './agence/agence.service';
import MechanicService from './mechanic/mechanic.service';
import OptionService from './option/option.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('carService', () => new CarService());
    provide('agenceService', () => new AgenceService());
    provide('mechanicService', () => new MechanicService());
    provide('optionService', () => new OptionService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
