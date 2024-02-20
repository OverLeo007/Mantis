<script>
export default {
  data() {
    return {
      drawer: null,
      headerData: {},
    }
  },
  provide() {
    return {
      headerData: this.headerData,
    }
  },
}
</script>

<template>
  <v-app id="inspire">
    <v-navigation-drawer class="left-sidebar" v-model="drawer">
      <v-list-item :height="65" title="Логотип"></v-list-item>
      <v-divider></v-divider>
    </v-navigation-drawer>

    <v-app-bar color="#B9D7EA" class="header" :height="65">
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-app-bar-title>{{ $route.path === "/" ? "Доски" : "Доска: " + headerData.boardTitle }}</v-app-bar-title>
    </v-app-bar>

    <v-main class="main">
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<style scoped>
.v-list-item {
  text-align: center;
}

.v-list-item :deep(.v-list-item-title) {
  font-size: 20px;
}

#add-card-button {
  border-radius: 20px;
}

.left-sidebar {
  background: #D6E6F2;
  border: 1px solid #C0CFD3;
}

.header {
  border: 1px solid #C0CFD3;
}
</style>
