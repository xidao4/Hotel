import { axios } from '@/utils/request'

const api = {
    tagPre: '/api/tags'
}

export function getAllTagsAPI(param) {
    return axios({
        url:`${api.tagPre}/${param}/list`,
        method: 'GET',
    })
}

export function addTagAPI(param) {
    return axios({
        url:`${api.tagPre}/${param.tagName}/${param.hotelId}/save`,
        method: 'POST',
        param
    })
}

export function deleteTagAPI(param) {
    return axios({
        url:`${api.tagPre}/${param.tagName}/${param.hotelId}/list`,
        method: 'POST',
    })
}
