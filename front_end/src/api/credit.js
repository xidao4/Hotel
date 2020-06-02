import { axios } from '@/utils/request'

const api = {
    creditPre: '/api/credit'
}

export function decreDefaultAPI(data){
    return axios({
        url:`${api.creditPre}/decreDefault`,
        method: 'POST',
        data
    })
}

export function increDefaultAPI(data){
    return axios({
        url:`${api.creditPre}/increDefault`,
        method: 'POST',
        data
    })
}

export function decreManualAPI(data){
    return axios({
        url:`${api.creditPre}/decreManual`,
        method: 'POST',
        data
    })
}

export function getCreditRecordsAPI(data){
    return axios({
        url:`${api.creditPre}/getCreditRecords`,
        method: 'GET',
    })
}

export function getUserCreditAPI(data){
    return axios({
        url:`${api.creditPre}/${data.userId}/getUserCreditRecords`,
        method: 'GET',
    })
}

export function getCreditByIdAPI(data){
    return axios({
        url:`${api.creditPre}/${data.userId}/getCreditById`,
        method: 'GET',
    })
}
