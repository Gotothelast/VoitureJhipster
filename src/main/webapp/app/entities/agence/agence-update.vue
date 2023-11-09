<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="voitureJhipsteRApp.agence.home.createOrEditLabel"
          data-cy="AgenceCreateUpdateHeading"
          v-text="t$('voitureJhipsteRApp.agence.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="agence.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="agence.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.agence.nameAgence')" for="agence-nameAgence"></label>
            <input
              type="text"
              class="form-control"
              name="nameAgence"
              id="agence-nameAgence"
              data-cy="nameAgence"
              :class="{ valid: !v$.nameAgence.$invalid, invalid: v$.nameAgence.$invalid }"
              v-model="v$.nameAgence.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.agence.adressAgence')" for="agence-adressAgence"></label>
            <input
              type="text"
              class="form-control"
              name="adressAgence"
              id="agence-adressAgence"
              data-cy="adressAgence"
              :class="{ valid: !v$.adressAgence.$invalid, invalid: v$.adressAgence.$invalid }"
              v-model="v$.adressAgence.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('voitureJhipsteRApp.agence.telAgence')" for="agence-telAgence"></label>
            <input
              type="number"
              class="form-control"
              name="telAgence"
              id="agence-telAgence"
              data-cy="telAgence"
              :class="{ valid: !v$.telAgence.$invalid, invalid: v$.telAgence.$invalid }"
              v-model.number="v$.telAgence.$model"
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
<script lang="ts" src="./agence-update.component.ts"></script>
