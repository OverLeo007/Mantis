<script>
import Card from "@/components/Card.vue";
import axios from "axios";

const baseURL = "http://26.171.167.108:8080/api/tasks";
const api = axios.create({
  baseURL: baseURL
})

export default {
  name: "CardHandler",
  components: {Card},
  props: {
    data: Object,
    listId: Number,
  },
  data() {
    return {
      cardList: this.data,
    }
  },
  methods: {
    async deleteCard(id) {
      try {
        await api.delete(`/${id}`);
        this.cardList = this.cardList.filter(x => {
          return x.id !== id;
        })
      } catch (error) {
        console.log(error);
      }
    },
    async createCard() {
      let nextTaskPosition = Math.max(...this.cardList.map(x => x.taskPosition));
      if (nextTaskPosition === -Infinity) nextTaskPosition = 0;
      try {
        const response = await api.post("", {
          taskTitle: "untitled",
          taskPosition: nextTaskPosition + 1,
          listId: this.listId,
        });
        this.cardList.push(response.data);
        this.cardList.at(-1).taskPreferences = JSON.parse(this.cardList.at(-1).taskPreferences);
      } catch (error) {
        console.log(error);
      }
    },
  },
  mounted() {
    this.cardList.forEach(x => x.taskPreferences = JSON.parse(x.taskPreferences));
  }
}
</script>

<template>
  <div class="cards-list-body">
    <Card v-for="card in cardList" :data="card" :key="card.id" @delete-card="deleteCard"></Card>
  </div>
  <v-card-actions class="cards-list-actions">
    <v-btn @click="createCard" class="add-card-btn" size="large" append-icon="mdi-plus">Добавить карточку</v-btn>
  </v-card-actions>
</template>

<style scoped>
.cards-list-body {
  padding-left: 20px;
  padding-right: 20px;
}
</style>