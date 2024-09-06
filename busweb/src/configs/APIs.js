import axios from "axios";
import cookie from "react-cookies";

const BASE_URL = "http://localhost:8080/BusPlanner/api/";

export const endpoints = {
    "login": "/login",
    "current-user": "/current-user",
    "update": "/user/current-user",
    "register": "/register",
    "find":"/route/find",
    "like-route":(routeId) => `/user/current-user/like/${routeId}/`,
    "liked-routes" : "/user/current-user/liked-routes",
    "unlike-route":(routeId) => `/user/current-user/unlike/${routeId}/`,
    "list-route": "/route/routelist",
}

export const authAPIs = () => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': cookie.load('authToken')
        }
    })
}

export default axios.create({
    baseURL: BASE_URL
})