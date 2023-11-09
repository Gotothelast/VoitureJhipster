<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="option">
        <h2 class="jh-entity-heading" data-cy="optionDetailsHeading">
          <span v-text="t$('voitureJhipsteRApp.option.detail.title')"></span> {{ option.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('voitureJhipsteRApp.option.nameOption')"></span>
          </dt>
          <dd>
            <span>{{ option.nameOption }}</span>
          </dd>
          <dt>
            <span v-text="t$('voitureJhipsteRApp.option.priceOption')"></span>
          </dt>
          <dd>
            <span>{{ option.priceOption }}</span>
          </dd>
          <dt>
            <span v-text="t$('voitureJhipsteRApp.option.car')"></span>
          </dt>
          <dd>
            <div v-if="option.car">
              <router-link :to="{ name: 'CarView', params: { carId: option.car.id } }">{{ option.car.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="option.id" :to="{ name: 'OptionEdit', params: { optionId: option.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./option-details.component.ts"></script>
