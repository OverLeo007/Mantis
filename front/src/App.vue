<script>
import Board from "@/components/Board.vue";
import axios from "axios";

const boardsURL = 'http://26.171.167.108:8080/api/boards';
const api = axios.create({
  baseURL: boardsURL,
})

export default {
  components: {Board},
  data() {
    return {
      boards: Object,
      drawer: null,
      icon: null,
      headerData: {},
    }
  },

  provide() {
    return {
      headerData: this.headerData,
    }
  },

  computed: {
    drawerIcon() {
      return this.drawer ? 'mdi-chevron-left' : 'mdi-chevron-right';
    }
  },

  methods: {
    async getBoards() {
      try {
        const response = await api.get("");
        this.boards = response.data;
      } catch (error) {
        console.log(error);
      }
    },

    goToBoardById(boardId, boardTitle) {
      this.$router.push({
        name: "board",
        params: {id: boardId},
        query: { boardTitle: boardTitle, boardId: boardId }});
    },

    goToBoards() {
      this.$router.push({path: "/"})
    },

    updateBoardList(newBoards) {
      this.boards = newBoards
    },

    toggleDrawer() {
      this.drawer = !this.drawer;
    }
  },

  async mounted() {
    await this.getBoards();
  },
}
</script>

<template>
  <v-app id="inspire">
    <v-navigation-drawer class="left-sidebar" v-model="drawer">
      <v-list-item :height="65" title="Логотип"></v-list-item>
      <v-divider></v-divider>
      <v-list-item :height="65" title="Доски" @click="goToBoards"></v-list-item>
      <v-list-item v-for="board in boards"
                   :key="board.id"
                   :title="board.title"
                   :height="65"
                   @click="goToBoardById(board.id, board.title)"
                   ></v-list-item>
    </v-navigation-drawer>

    <v-app-bar color="#B9D7EA" class="header">
      <v-app-bar-nav-icon @click="toggleDrawer">
        <v-icon>{{ drawerIcon  }}</v-icon>
      </v-app-bar-nav-icon>
      <v-app-bar-title>{{ $route.path === "/" ? "Доски" : "Доска: " + headerData.boardTitle }}</v-app-bar-title>
    </v-app-bar>

    <v-main class="main">
      <router-view :key="$route.fullPath"
                    @board-updated="updateBoardList"></router-view>
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
  height: 65px;
}
</style>
