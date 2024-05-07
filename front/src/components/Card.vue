<script>
import EditableCardTitle from "@/components/EditableCardTitle.vue";
import axios from "axios";
import { DateTime, Duration } from "luxon";
import TimeConverter from "../utils/TimeConverter.js";
import TasksApi from "@/api/tasks/TasksApi.js";

// const baseURL = 'http://26.171.167.108:8080/api/tasks'
// const api = axios.create({
//   baseURL: baseURL
// })



export default {
  name: "Card",
  computed: {
    TimeConverter() {
      return TimeConverter
    }
  },
  components: {EditableCardTitle},
  props: {
    data: Object,
  },
  emits: ['deleteCard'],
  data() {
    return {
      card: this.data,
      startDate: String,
      endDate: String,
      isStartDate: false,
      isEndDate: false,
      dialog: false,
      swatches: [
        ['#FF0000', '#AA0000', '#550000'],
        ['#FFFF00', '#AAAA00', '#555500'],
        ['#00FF00', '#00AA00', '#005500'],
        ['#00FFFF', '#00AAAA', '#005555'],
        ['#0000FF', '#0000AA', '#000055'],
      ],
      color: null,
      cardText: "Добавить описание",
      mavonText: this.data.taskText,
    }
  },
  methods: {
    async editCard() {
      try {
        this.isStartDate ? this.card.taskPreferences['startDate'] = this.startDate : this.card.taskPreferences['startDate'] = null
        this.isEndDate ? this.card.taskPreferences['endDate'] = this.endDate : this.card.taskPreferences['endDate'] = null

        if (this.card.taskPreferences['startDate'] && this.card.taskPreferences['endDate']) {
          const startDate = DateTime.fromISO(this.card.taskPreferences['startDate']);
          const endDate = DateTime.fromISO(this.card.taskPreferences['endDate']);
          if (startDate.year > endDate.year) {
            const newEndDate = endDate.plus({years: 1}).toISO().substring(0, 16);

            this.card.taskPreferences['endDate'] = newEndDate;
            this.endDate = newEndDate;
          }
        }

        // const response = await api.put("", {
        //   id: this.card.id,
        //   taskTitle: this.card.taskTitle,
        //   taskText: this.mavonText,
        //   taskPosition: this.card.taskPosition,
        //   dueDate: Date.now(),
        //   taskPreferences: this.card.taskPreferences
        // })
        await TasksApi.editTask(
            this.card.id,
            this.card.taskTitle,
            this.mavonText,
            this.card.taskPosition,
            Date.now(),
            this.card.taskPreferences
        )
      } catch (error) {
        console.log(error);
      }
    }
  },
  watch: {
    'card.taskPreferences'(newValue) {
      !newValue.startDate ? this.startDate = DateTime.now().toISO().substring(0, 16) : this.startDate = newValue.startDate;
      !newValue.endDate ? this.endDate = DateTime.now().toISO().substring(0, 16) : this.endDate = newValue.endDate;

      this.isStartDate = Boolean(newValue.startDate)
      this.isEndDate = Boolean(newValue.endDate)
    }
  },
  mounted() {

  }
}
</script>

<template>
  <v-card class="card" @click="dialog = true">
    <v-sheet class="card-sheet" :color="card.taskPreferences.color" width="100%" height="50">
      <v-icon @click.stop="$emit('deleteCard', card.id)" class="card-sheet-icon" icon="mdi-trash-can"></v-icon>
    </v-sheet>
    <EditableCardTitle :data="card" :field-to-edit="'taskTitle'" :function-stop-editing="editCard" :hide-delete-button="true"></EditableCardTitle>
    <div class="card-time">
      <span v-if="this.card.taskPreferences['startDate'] && this.card.taskPreferences['endDate']">
        {{ TimeConverter.twoDeadlines(this.card.taskPreferences['startDate'], this.card.taskPreferences['endDate']) }}
      </span>
      <span v-else-if="card.taskPreferences['startDate']">
        Дата начала: {{ TimeConverter.deadline(card.taskPreferences['startDate']) }}
      </span>
      <span v-else-if="card.taskPreferences['endDate']">
        Дата окончания: {{ TimeConverter.deadline(card.taskPreferences['endDate']) }}
      </span>
      <v-icon
          v-if="this.card.taskPreferences['startDate'] || this.card.taskPreferences['endDate']"
          class="card-time-icon"
          icon="mdi-clock-time-ten-outline"
          size="small"
      >
      </v-icon>
    </div>
  </v-card>

  <!--Modal-->
  <v-dialog
      v-model="dialog"
      persistent
      width="auto"
      class="dialog"
  >
    <v-card class="dialog-card-edit" >
      <v-card-title class="card-title">
        {{ card.taskTitle }}
      </v-card-title>

      <div class="dialog-card-content">
        <div class="main-content">
          <mavon-editor
              class="card-text-area"
              v-model="mavonText"
              language="ru"
              :scroll-style="false"
          >

          </mavon-editor>

          <div class="date-content">
            <div>
              <div class="date-label">
                <input class="date-checkbox" type="checkbox" v-model="isStartDate"/>
                <h3>Start Date</h3>
              </div>
              <input
                  class="datepicker"
                  type="datetime-local"
                  v-model="startDate"
                  :disabled="!isStartDate"
              />
            </div>
            <div>
              <div class="date-label">
                <input class="date-checkbox" type="checkbox" v-model="isEndDate"/>
                <h3>End Date</h3>
              </div>
              <input
                  class="datepicker"
                  type="datetime-local"
                  v-model="endDate"
                  :min="this.startDate"
                  :disabled="!isEndDate"
              />
            </div>
          </div>
        </div>
        <div class="second-content">
          <v-color-picker
              class="ma-2"
              :swatches="swatches"
              v-model="card.taskPreferences.color"
              show-swatches
          ></v-color-picker>
          <v-card-actions class="card-actions">
            <v-btn
                class="modal-close-button"
                color="primary"
                @click="dialog = false; editCard()"
            >
              Готово
            </v-btn>
          </v-card-actions>
        </div>
      </div>
    </v-card>
  </v-dialog>
</template>

<style scoped>
.dialog-card-content {
  display: flex;
  justify-content: space-between;
}

.second-content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.card-sheet {
  display: flex;
  align-items: center;
  justify-content: end;
}

.card-sheet-icon {
  margin-right: 10px;
}

.card {
  border-radius: 20px;
  border: 1px solid #C0CFD3;
  margin-bottom: 10px;
  background: #FFF;
  min-height: 120px;
}

.dialog-card-edit {
  border-radius: 10px !important;
  background-color: #F7FBFC;
}

.card-text {
  padding-left: 24px;
}

.card-title {
  padding-left: 24px;
}

.card-text-area {
  margin-left: 24px;
  margin-right: 20px;
  height: 400px;
}

.modal-close-button {
  width: 215px;
  height: 50px;
  flex-shrink: 0;
  border-radius: 20px;
  border: 1px solid #C0CFD3;
  background: #D6E6F2;
}

.card-actions {
  margin: auto;
}

.date-content {
  display: flex;
  justify-content: flex-end;
  margin-right: 18px;
  gap: 20px;
}

.datepicker {
  background: #D6E6F2;
  border-radius: 10px;
  width: 180px;
  border: solid #C0CFD3 1px;
  padding: 6px 12px;
}

.date-label {
  display: flex;
  gap: 10px;
  align-items: center;
}

.date-checkbox {
  transform: scale(1.2);
  margin-left: 6px;
}

.card-time {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-right: 20px;
  color: #445365;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 16px;
}

.card-time-icon {
  margin-left: 10px;
}

</style>