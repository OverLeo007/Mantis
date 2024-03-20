<script>
import EditableCardTitle from "@/components/EditableCardTitle.vue";
import axios from "axios";

const baseURL = 'http://26.171.167.108:8080/api/boards';
const api = axios.create({
  baseURL: baseURL,
})

export default {
  name: "Board",
  components: {EditableCardTitle},
  props: {
    data: Object,
  },
  data() {
    return {
      board: this.data,
    }
  },
  methods: {
    async editBoard() {
      try {
        await api.put(`/${this.board.id}`, {
          title: this.board.title,
        })
        this.$emit('boardEdited');
      } catch (error) {
        console.log(error);
      }
    }
  }
}
</script>

<template>
  <v-card class="board">
    <div class="board-body">
      <EditableCardTitle
          :data="board"
          :field-to-edit="'title'"
          :function-stop-editing="editBoard"
          @delete="$emit('deleteBoard', board.id)"
      >
      </EditableCardTitle>
    </div>
  </v-card>
</template>

<style scoped>
.board-body {
  padding-left: 20px;
  padding-right: 20px;
}

.board {
  position: relative;
  min-width: 400px;
  max-width: 100%;
  max-height: 200px;
  margin-right: 35px;

  border-radius: 20px;
  border: 1px solid #C0CFD3;
  background: #F7FBFC;
}

.board:hover {
  transform: translateY(-5px);
  background: rgba(255, 255, 255, 0.18);
  border-radius: 16px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(5.1px);
  -webkit-backdrop-filter: blur(5.1px);
  border: 1px solid rgba(0, 0, 0, 0.4);
}

.board:active {
  box-shadow: 0 4px 5px rgba(68, 114, 196, 0.8);
  transform: scale(0.98);
}

.board-title {
  display: flex;
  justify-content: space-between;
}
</style>