import { axios } from '@/utils/request'

const api = {
    creditPre: '/api/credit'
}

export function updateCreditAPI(param) {
    return axios({
        url:`${api.creditPre}/${param.method}`,
        method: 'POST',
        data: param.data
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

export function cancelUpdateAPI(param){
    return axios({
        url:`${api.creditPre}/${param}/cancelUpdate`,
        method: 'POST',
    })
}
