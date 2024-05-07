<script>
  import {
    CalendarView, CalendarViewHeader
  } from "vue-simple-calendar";

  import 'vue-simple-calendar/dist/style.css'
  import 'vue-simple-calendar/dist/css/default.css'

  export default {
    name: "CalendarPage",
    data() {
      return {
        showDate: new Date(),
        items: [
          {
            id: "e1",
            startDate: this.thisMonth(15, 18, 30),
          },
          {
            id: "e2",
            startDate: this.thisMonth(15),
            title: "Single-day item with a long title",
          },
          {
            id: "e3",
            startDate: this.thisMonth(7, 9, 25),
            endDate: this.thisMonth(10, 16, 30),
            title: "Multi-day item with a long title and times",
          },
          {
            id: "e4",
            startDate: this.thisMonth(20),
            title: "My Birthday!",
            classes: "birthday",
            url: "https://en.wikipedia.org/wiki/Birthday",
          },
          {
            id: "e5",
            startDate: this.thisMonth(5),
            endDate: this.thisMonth(12),
            title: "Multi-day item",
            classes: "purple",
            tooltip: "This spans multiple days",
          },
          {
            id: "foo",
            startDate: this.thisMonth(29),
            title: "Same day 1",
            classes: "orange"
          },
        ]
      }
    },
    components: {
      CalendarView,
      CalendarViewHeader
    },
    methods: {
      setShowDate(d) {
        this.showDate = d
      },
      thisMonth(d, h, m) {
        const t = new Date()
        return new Date(t.getFullYear(), t.getMonth(), d, h || 0, m || 0)
      },
    }
  }
</script>

<template>
  <calendar-view
      :items="items"
      :starting-day-of-week="1"
      :show-date="showDate"
      class="theme-default"
  >
    <template #header="{ headerProps }">
      <calendar-view-header
          slot="header"
          :header-props="headerProps"
          @input="setShowDate"
      />
    </template>
  </calendar-view>
</template>

<style scoped>
</style>