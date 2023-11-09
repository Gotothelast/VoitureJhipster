<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="voitureJhipsteRApp.car.home.createOrEditLabel"
          data-cy="CarCreateUpdateHeading"
          v-text="t$('voitureJhipsteRApp.car.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="car.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="car.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.car.carName')" for="car-carName"></label>
            <input
              type="text"
              class="form-control"
              name="carName"
              id="car-carName"
              data-cy="carName"
              :class="{ valid: !v$.carName.$invalid, invalid: v$.carName.$invalid }"
              v-model="v$.carName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.car.carModel')" for="car-carModel"></label>
            <input
              type="text"
              class="form-control"
              name="carModel"
              id="car-carModel"
              data-cy="carModel"
              :class="{ valid: !v$.carModel.$invalid, invalid: v$.carModel.$invalid }"
              v-model="v$.carModel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.car.carReference')" for="car-carReference"></label>
            <input
              type="text"
              class="form-control"
              name="carReference"
              id="car-carReference"
              data-cy="carReference"
              :class="{ valid: !v$.carReference.$invalid, invalid: v$.carReference.$invalid }"
              v-model="v$.carReference.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.car.carYear')" for="car-carYear"></label>
            <input
              type="number"
              class="form-control"
              name="carYear"
              id="car-carYear"
              data-cy="carYear"
              :class="{ valid: !v$.carYear.$invalid, invalid: v$.carYear.$invalid }"
              v-model.number="v$.carYear.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.car.carPrice')" for="car-carPrice"></label>
            <input
              type="number"
              class="form-control"
              name="carPrice"
              id="car-carPrice"
              data-cy="carPrice"
              :class="{ valid: !v$.carPrice.$invalid, invalid: v$.carPrice.$invalid }"
              v-model.number="v$.carPrice.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.car.mechanic')" for="car-mechanic"></label>
            <select class="form-control" id="car-mechanic" data-cy="mechanic" name="mechanic" v-model="car.mechanic">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="car.mechanic && mechanicOption.id === car.mechanic.id ? car.mechanic : mechanicOption"
                v-for="mechanicOption in mechanics"
                :key="mechanicOption.id"
              >
                {{ mechanicOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.car.agence')" for="car-agence"></label>
            <select class="form-control" id="car-agence" data-cy="agence" name="agence" v-model="car.agence">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="car.agence && agenceOption.id === car.agence.id ? car.agence : agenceOption"
                v-for="agenceOption in agences"
                :key="agenceOption.id"
              >
                {{ agenceOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./car-update.component.ts"></script>
