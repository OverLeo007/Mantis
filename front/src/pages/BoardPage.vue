<script>
import axios from "axios";
import List from "@/components/List.vue";

const baseURL = "http://26.171.167.108:8080/api/lists"
const api = axios.create({
  baseURL: baseURL
})

export default {
  components: {List},
  inject: ["headerData"],
  name: "BoardPage",
  data() {
    return {
      lists: Array,
    }
  },
  methods: {
    async getLists() {
      const boardId = this.$route.query.boardId;
      try {
        const response = await api.get("", { params: {board: boardId} });
        this.lists = response.data;
        this.lists.sort((a, b) => a.listPosition - b.listPosition);
        this.lists.forEach(x => x.tasks.sort((a, b) => a.taskPosition - b.taskPosition));
      } catch (error) {
        console.log(error);
      }
    },
    async createList() {
      await api.post("", {
        title: "untitled",
        boardId: this.headerData.boardId,
      });
      await this.getLists();
    },
    async deleteList(id) {
      try {
        await api.delete(`/${id}`);
        this.lists = this.lists.filter(x => {
          return x.id !== id;
        })
      } catch (error) {
        console.log(error);
      }
    }
  },
  async mounted() {
    this.headerData.boardTitle = this.$route.query.boardTitle;
    this.headerData.boardId = this.$route.query.boardId;
    await this.getLists();
  },
}
</script>

<template>
  <div class="main-container">
    <List
        v-for="list in lists"
        :data="list"
        :board-id="$route.query.boardId"
        :key="list.id"
        @delete-list="deleteList"
    >
    </List>
    <v-btn id="add-card-button" size="large" append-icon="mdi-plus" @click="createList">Добавить колонку</v-btn>
  </div>
</template>

<style scoped>
.main-container {
  display: flex;
  padding: 35px;
  height: 100%;
  overflow-x: auto;
  scrollbar-width: none;
}
</style>