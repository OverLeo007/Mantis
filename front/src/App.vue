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
      authIcon: 'mdi-account-outline',
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
    },

    authorise() {
      this.$router.push({path: "/login"})
    },

    showBar() {
      let curPath = this.$route.path;
      if (curPath === '/login' || curPath === '/signin') {
        this.drawer = false
      }
      return curPath !== '/login' && curPath !== '/signin'
    },

    showView() {
      let curPath = this.$route.path;
      return curPath.startsWith('/board') || curPath === '/calendar'
    }
  },

  async mounted() {
    await this.getBoards();
  },
}
</script>

<template>
  <v-app id="inspire">
    <v-navigation-drawer class="left-sidebar"  v-model="drawer">
      <v-list-item :height="65" title="Логотип"></v-list-item>
      <v-divider></v-divider>
      <v-list-item id="desks" class="sidebar-button" title="Доски" @click="goToBoards"></v-list-item>
      <div v-show="showView()">
        <v-list-item class="sidebar-btngroup-name"  title="Режим просмотра"></v-list-item>
        <v-list-item class="sidebar-button" title="Колонки" @click=""></v-list-item>
        <v-list-item class="sidebar-button" title="Календарь" @click=""></v-list-item>
      </div>

      <v-list-item class="sidebar-btngroup-name" title="Доступные доски"></v-list-item>

      <v-list-item v-for="board in boards"
                   class="sidebar-button"
                   :key="board.id"
                   :title="board.title"
                   @click="goToBoardById(board.id, board.title)"
                   ></v-list-item>
    </v-navigation-drawer>

    <v-app-bar v-show="showBar()" color="#B9D7EA" class="header">
      <v-app-bar-nav-icon @click="toggleDrawer">
        <v-icon>{{ drawerIcon }}</v-icon>
      </v-app-bar-nav-icon>
      <v-app-bar-title>{{ $route.path === "/" ? "Доски" : "Доска: " + headerData.boardTitle }}</v-app-bar-title>
      <v-app-bar-nav-icon style="margin-right:10px;" @click="authorise">
        <v-icon>{{ authIcon }}</v-icon>
      </v-app-bar-nav-icon>
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
  font-size: 18px;
}

.sidebar-button {
  padding-left: 35px;
  height: 28px;
  text-align: start;
}

.sidebar-btngroup-name {
  padding-left: 25px;
  text-align: start;
  margin-top: 10px;
  //height: 10px;
}

.sidebar-btngroup-name:deep(.v-list-item-title) {
  font-weight: bold;
  font-size: 18px;
  height: 25px;
}

#add-card-button {
  border-radius: 20px;
}

#desks {
  padding-left: 25px;
  margin-top: 10px
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
