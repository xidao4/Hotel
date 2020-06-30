import { axios } from '@/utils/request'

const api = {
    msgPre: '/api/message'
}

export function sendMessageAPI(param) {
    return axios({
        url:`${api.msgPre}/sendMessage`,
        method: 'POST',
        data: param
    })
}

export function getQuesByIdAPI(data){
    return axios({
        url:`${api.msgPre}/${data.toId}/getQuesById`,
        method: 'GET',
    })
}

export function getChatByIdAPI(data){
    return axios({
        url:`${api.msgPre}/${data.id}/getChatById`,
        method: 'GET',
    })
}

export function getQuesForClientByIdAPI(data) {
    return axios({
        url:`${api.msgPre}/${data.fromId}/getQuesForClientById`,
        method: 'GET',
    })
}

export function getPrivateAdAPI(data){
    return axios({
        url:`${api.msgPre}/${data.toId}/getPrivateAd`,
        method: 'GET',
    })
}

export function getAdSentByIdAPI(data){
    return axios({
        url:`${api.msgPre}/${data.fromId}/getAdSentById`,
        method: 'GET',
    })
}

export function changeMessageStatusAPI(data) {
    return axios({
        url:`${api.msgPre}/${data.id}/${data.status}/changeMessageStatus`,
        method: 'GET',
    })
}

export function getBroadcastListAPI(){
    return axios({
        url:`${api.msgPre}/getBroadcastList`,
        method: 'GET',
    })
}

export function sendBroadcastAPI(data) {
    return axios({
        url:`${api.msgPre}/sendBroadcast`,
        method: 'POST',
        data: data
    })
}

export function changeBroadcastStatusAPI(data) {
    return axios({
        url:`${api.msgPre}/${data.id}/${data.status}/changeBroadcastStatus`,
        method: 'GET',
    })
}

export function updatePriorityAPI(data) {
    return axios({
        url:`${api.msgPre}/${data.id}/${data.priority}/cancelBroadcast`,
        method: 'GET',
    })
}

export function getAllClientsMenAPI() {
    return axios({
        url:`${api.msgPre}/getAllClientsMen`,
        method: 'GET',
    })
}
