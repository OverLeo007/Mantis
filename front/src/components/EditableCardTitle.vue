<script>
export default {
  name: "EditableCardTitle",
  props: {
    data: Object,
    fieldToEdit: String,
    functionStopEditing: Function,
    args: Object,
    hideDeleteButton: Boolean,
  },
  data() {
    return {
      isEditTitle: false,
      editedTitle: "",
    }
  },
  methods: {
    startEditing() {
      this.editedTitle = this.data[this.fieldToEdit];
      this.isEditTitle = true;
    },
    async stopEditing() {
      this.data[this.fieldToEdit] = this.editedTitle;
      this.isEditTitle = false
      await this.functionStopEditing?.(...this.args ?? []);
    }
  },
  mounted() {
  }
}
</script>

<template>
  <v-card-title class="card-title" v-if="!isEditTitle" @click.stop="startEditing">
    <div>
      {{ data[fieldToEdit] }}
    </div>
    <v-icon v-show="!hideDeleteButton" class="card-sheet-icon" icon="mdi-trash-can" @click.stop="$emit('delete', data.id)"></v-icon>
  </v-card-title>
  <v-text-field v-else @blur="stopEditing()" @keyup.enter="stopEditing()" autofocus v-model="editedTitle"></v-text-field>
</template>

<style scoped>
.card-title {
  display: flex;
  justify-content: space-between;
}
</style>