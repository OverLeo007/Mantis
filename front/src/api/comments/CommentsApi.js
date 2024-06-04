import BasesApi from "@/api/BasesApi.js";


export default class {
    static async createComment(taskId, text) {
        return await BasesApi.commentsApi.post("", {
            taskId,
            text
        });
    }
    static async getComments(taskId) {
        return await BasesApi.commentsApi.get("", {
            params: {task_id: taskId}
        });
    }
    static async deleteComment(id) {
        return await BasesApi.commentsApi.delete(`/${id}`);
    }
}
