import axios from "axios";

const baseAddress = import.meta.env.VITE_API_URL;

const boardsAddress = `${baseAddress}/api/s/boards`;
const tasksAddress = `${baseAddress}/api/s/tasks`;
const listsAddress = `${baseAddress}/api/s/lists`;
const authAddress = `${baseAddress}/api/auth`;
const commentsAddress = `${baseAddress}/api/s/comments`

const boardsApi = axios.create({
    baseURL: boardsAddress
});
const tasksApi = axios.create({
    baseURL: tasksAddress
})
const listsApi = axios.create({
    baseURL: listsAddress
})
const authApi = axios.create({
    baseURL: authAddress
})
const commentsApi = axios.create({
    baseURL: commentsAddress
})

function addToken() {
    [boardsApi, tasksApi, listsApi, authApi, commentsApi].forEach(api => {
        api.interceptors.request.use(config => {
            if (localStorage.getItem("auth_token")) {
                config.headers.Authorization = `Bearer ${localStorage.getItem("auth_token")}`;
            }

            return config;
        }, error => {
            return Promise.reject(error);
        });
    });
}

export default {
    boardsApi,
    tasksApi,
    listsApi,
    authApi,
    commentsApi,
    addToken
}