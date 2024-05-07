import axios from "axios";

const baseAddress = 'http://26.171.167.108:8080';
//console.log(process.env.VUE_APP_API_URL);

const boardsAddress = `${baseAddress}/api/s/boards`;
const tasksAddress = `${baseAddress}/api/s/tasks`;
const listsAddress = `${baseAddress}/api/s/lists`;
const authAddress = `${baseAddress}/api/auth`

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

function addToken() {
    [boardsApi, tasksApi, listsApi, authApi].forEach(api => {
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
    addToken
}