<template>
  <div>
    <h2 id="page-heading" data-cy="CarHeading">
      <span v-text="t$('voitureJhipsteRApp.car.home.title')" id="car-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('voitureJhipsteRApp.car.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CarCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-car">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('voitureJhipsteRApp.car.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && cars && cars.length === 0">
      <span v-text="t$('voitureJhipsteRApp.car.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="cars && cars.length > 0">
      <table class="table table-striped" aria-describedby="cars">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('voitureJhipsteRApp.car.carName')"></span></th>
            <th scope="row"><span v-text="t$('voitureJhipsteRApp.car.carModel')"></span></th>
            <th scope="row"><span v-text="t$('voitureJhipsteRApp.car.carReference')"></span></th>
            <th scope="row"><span v-text="t$('voitureJhipsteRApp.car.carYear')"></span></th>
            <th scope="row"><span v-text="t$('voitureJhipsteRApp.car.carPrice')"></span></th>
            <th scope="row"><span v-text="t$('voitureJhipsteRApp.car.mechanic')"></span></th>
            <th scope="row"><span v-text="t$('voitureJhipsteRApp.car.agence')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="car in cars" :key="car.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CarView', params: { carId: car.id } }">{{ car.id }}</router-link>
            </td>
            <td>{{ car.carName }}</td>
            <td>{{ car.carModel }}</td>
            <td>{{ car.carReference }}</td>
            <td>{{ car.carYear }}</td>
            <td>{{ car.carPrice }}</td>
            <td>
              <div v-if="car.mechanic">
                <router-link :to="{ name: 'MechanicView', params: { mechanicId: car.mechanic.id } }">{{ car.mechanic.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="car.agence">
                <router-link :to="{ name: 'AgenceView', params: { agenceId: car.agence.id } }">{{ car.agence.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CarView', params: { carId: car.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CarEdit', params: { carId: car.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(car)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="voitureJhipsteRApp.car.delete.question" data-cy="carDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-car-heading" v-text="t$('voitureJhipsteRApp.car.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-car"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeCar()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./car.component.ts"></script>
