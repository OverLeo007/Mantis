import BasesApi from "@/api/BasesApi.js";

export default class {
    static async getTask(id) {
        return await BasesApi.tasksApi.get(`/${id}`);
    }

    static async getTasks(listId) {
        return await BasesApi.tasksApi.get("", {
            params: {list_id: listId}
        });
    }

    static async createTask(taskTitle, taskPosition, listId) {
        return await BasesApi.tasksApi.post("", {
            taskTitle,
            taskPosition,
            listId
        });
    }

    static async editTask(id, taskTitle, taskText, taskPosition, dueDate, taskPreferences) {
        return await BasesApi.tasksApi.put("", {
            id,
            taskTitle,
            taskText,
            taskPosition,
            dueDate,
            taskPreferences
        });
    }

    static async deleteTask(id) {
        return await BasesApi.tasksApi.delete(`/${id}`);
    }
}