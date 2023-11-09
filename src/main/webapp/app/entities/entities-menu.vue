<template>
  <div>
    <b-dropdown-item to="/car">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.car')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/agence">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.agence')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/mechanic">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.mechanic')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/option">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.option')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
