import BasesApi from "@/api/BasesApi.js";

export default class {
    static async getList(id) {
        return await BasesApi.listsApi.get(`/${id}`);
    }

    static async getListsByBoard(boardId) {
        return await BasesApi.listsApi.get("", { params: {board: boardId} });
    }

    static async createList(title, boardId) {
        return await BasesApi.listsApi.post("", {
            title,
            boardId
        });
    }

    static async editList(id, title, listPosition) {
        return await BasesApi.listsApi.put(`/${id}`, {
            title,
            listPosition
        });
    }

    static async deleteList(id) {
        return await BasesApi.listsApi.delete(`/${id}`);
    }
}