<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="voitureJhipsteRApp.option.home.createOrEditLabel"
          data-cy="OptionCreateUpdateHeading"
          v-text="t$('voitureJhipsteRApp.option.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="option.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="option.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.option.nameOption')" for="option-nameOption"></label>
            <input
              type="text"
              class="form-control"
              name="nameOption"
              id="option-nameOption"
              data-cy="nameOption"
              :class="{ valid: !v$.nameOption.$invalid, invalid: v$.nameOption.$invalid }"
              v-model="v$.nameOption.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.option.priceOption')" for="option-priceOption"></label>
            <input
              type="number"
              class="form-control"
              name="priceOption"
              id="option-priceOption"
              data-cy="priceOption"
              :class="{ valid: !v$.priceOption.$invalid, invalid: v$.priceOption.$invalid }"
              v-model.number="v$.priceOption.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.option.car')" for="option-car"></label>
            <select class="form-control" id="option-car" data-cy="car" name="car" v-model="option.car">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="option.car && carOption.id === option.car.id ? option.car : carOption"
                v-for="carOption in cars"
                :key="carOption.id"
              >
                {{ carOption.id }}
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
<script lang="ts" src="./option-update.component.ts"></script>
