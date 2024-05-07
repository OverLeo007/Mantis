<script>
import Board from "@/components/Board.vue";
import BoardsApi from "@/api/boards/BoardsApi.js";


export default {
  name: "Boards",
  components: {Board},
  data() {
    return {
      boards: Object,
    }
  },
  methods: {
    async getBoards() {
      try {
        const response = await BoardsApi.getBoards();
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
    async createBoard() {
      try {
        await BoardsApi.createBoard(
            "untitled"
        )
        await this.getBoards();
        this.$emit('boardUpdated', this.boards);
      } catch (error) {
        console.log(error);
      }
    },
    async deleteBoard(id) {
      try {
        await BoardsApi.deleteBoard(id);
        this.boards = this.boards.filter(x => {
          return x.id !== id;
        })
        this.$emit('boardUpdated', this.boards);
      } catch (error) {
        console.log(error);
      }
    }
  },
  async mounted() {
    await this.getBoards();
  }
}
</script>

<template>
  <div class="board-container">
    <div class="add-board-button">
      <v-btn id="add-card-button" size="large" append-icon="mdi-plus" @click="createBoard">Доски</v-btn>
    </div>
    <div class="main-container-board">
      <Board @click="goToBoardById(board.id, board.title)"
             v-for="board in boards"
             :data="board"
             @board-edited="$emit('boardUpdated', this.boards)"
             @delete-board="deleteBoard"
             :key="board.id">
      </Board>
    </div>
  </div>
</template>

<style scoped>
.board-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.main-container-board {
  flex: 1;
  display: flex;
  padding: 35px;
  height: 100%;
  overflow-x: auto;
  scrollbar-width: none;
}

.add-board-button {
  margin-left: 35px;
  margin-top: 35px;
}
</style>