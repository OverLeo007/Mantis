<script>
import axios from "axios";
import EditableCardTitle from "@/components/EditableCardTitle.vue";
import Card from "@/components/Card.vue";
import CardHandler from "@/components/CardHandler.vue";

const baseURL = "http://26.171.167.108:8080/api/lists"
const api = axios.create({
  baseURL: baseURL,
})

export default {
  name: "List",
  components: {CardHandler, Card, EditableCardTitle},
  props: {
    data: Object,
    boardId: String,
  },
  data() {
    return {
      list: this.data,
    }
  },
  methods: {
    async editList() {
      try {
        const response = await api.put(`/${this.list.id}`, {
          id: this.list.id,
          title: this.list.title,
          listPosition: this.list.listPosition,
        })
      } catch (error) {
        console.log(error);
      }
    },
  },
  mounted() {
  }
}
</script>

<template>
  <v-card class="cards-list">
    <EditableCardTitle :data="list" :field-to-edit="'title'" :function-stop-editing="editList" @delete="$emit('deleteList', list.id)"></EditableCardTitle>
    <CardHandler :data="list.tasks" :list-id="list.id"></CardHandler>
  </v-card>
</template>

<style scoped>
.cards-list {
  min-width: 365px;
  max-width: 100%;
  margin-right: 35px;
  height: fit-content;

  border-radius: 20px;
  border: 1px solid #C0CFD3;
  background: #F7FBFC;
}
</style>