<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="voitureJhipsteRApp.mechanic.home.createOrEditLabel"
          data-cy="MechanicCreateUpdateHeading"
          v-text="t$('voitureJhipsteRApp.mechanic.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="mechanic.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="mechanic.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.mechanic.motor')" for="mechanic-motor"></label>
            <input
              type="text"
              class="form-control"
              name="motor"
              id="mechanic-motor"
              data-cy="motor"
              :class="{ valid: !v$.motor.$invalid, invalid: v$.motor.$invalid }"
              v-model="v$.motor.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.mechanic.power')" for="mechanic-power"></label>
            <input
              type="number"
              class="form-control"
              name="power"
              id="mechanic-power"
              data-cy="power"
              :class="{ valid: !v$.power.$invalid, invalid: v$.power.$invalid }"
              v-model.number="v$.power.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.mechanic.km')" for="mechanic-km"></label>
            <input
              type="number"
              class="form-control"
              name="km"
              id="mechanic-km"
              data-cy="km"
              :class="{ valid: !v$.km.$invalid, invalid: v$.km.$invalid }"
              v-model.number="v$.km.$model"
            />
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
<script lang="ts" src="./mechanic-update.component.ts"></script>
