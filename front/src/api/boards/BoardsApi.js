import BasesApi from "@/api/BasesApi.js";

export default class {
    static async getBoards() {
        return await BasesApi.boardsApi.get("");
    }

    static async createBoard(title) {
        return await BasesApi.boardsApi.post("", {
            title: title
        });
    }

    static async deleteBoard(id) {
        return await BasesApi.boardsApi.delete(`/${id}`);
    }

    static async editBoard(id, title) {
        return await BasesApi.boardsApi.put(`/${id}`, {
            title
        });
    }

    static async test() {
        return await BasesApi.boardsApi.get("/test");
    }
}