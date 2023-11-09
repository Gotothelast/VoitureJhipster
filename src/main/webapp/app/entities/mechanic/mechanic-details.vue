<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="mechanic">
        <h2 class="jh-entity-heading" data-cy="mechanicDetailsHeading">
          <span v-text="t$('voitureJhipsteRApp.mechanic.detail.title')"></span> {{ mechanic.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('voitureJhipsteRApp.mechanic.motor')"></span>
          </dt>
          <dd>
            <span>{{ mechanic.motor }}</span>
          </dd>
          <dt>
            <span v-text="t$('voitureJhipsteRApp.mechanic.power')"></span>
          </dt>
          <dd>
            <span>{{ mechanic.power }}</span>
          </dd>
          <dt>
            <span v-text="t$('voitureJhipsteRApp.mechanic.km')"></span>
          </dt>
          <dd>
            <span>{{ mechanic.km }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="mechanic.id" :to="{ name: 'MechanicEdit', params: { mechanicId: mechanic.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./mechanic-details.component.ts"></script>
