import BasesApi from "@/api/BasesApi.js";
import axios from "axios";

export default class {
    static async register(username, email, password) {
        return await BasesApi.authApi.post("register", {
            username,
            email,
            password
        });
    }

    static async login(username, password) {
        localStorage.removeItem('auth_token');

        return await BasesApi.authApi.post("login", {
            username,
            password
        });
    }

    static async canUserAccess() {
        try {
            await BasesApi.authApi.get("");
            return true;
        } catch (error) {
            return false;
        }
    }
}