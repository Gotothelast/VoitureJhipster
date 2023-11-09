<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="car">
        <h2 class="jh-entity-heading" data-cy="carDetailsHeading">
          <span v-text="t$('voitureJhipsteRApp.car.detail.title')"></span> {{ car.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('voitureJhipsteRApp.car.carName')"></span>
          </dt>
          <dd>
            <span>{{ car.carName }}</span>
          </dd>
          <dt>
            <span v-text="t$('voitureJhipsteRApp.car.carModel')"></span>
          </dt>
          <dd>
            <span>{{ car.carModel }}</span>
          </dd>
          <dt>
            <span v-text="t$('voitureJhipsteRApp.car.carReference')"></span>
          </dt>
          <dd>
            <span>{{ car.carReference }}</span>
          </dd>
          <dt>
            <span v-text="t$('voitureJhipsteRApp.car.carYear')"></span>
          </dt>
          <dd>
            <span>{{ car.carYear }}</span>
          </dd>
          <dt>
            <span v-text="t$('voitureJhipsteRApp.car.carPrice')"></span>
          </dt>
          <dd>
            <span>{{ car.carPrice }}</span>
          </dd>
          <dt>
            <span v-text="t$('voitureJhipsteRApp.car.mechanic')"></span>
          </dt>
          <dd>
            <div v-if="car.mechanic">
              <router-link :to="{ name: 'MechanicView', params: { mechanicId: car.mechanic.id } }">{{ car.mechanic.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="car.id" :to="{ name: 'CarEdit', params: { carId: car.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./car-details.component.ts"></script>
