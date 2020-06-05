import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function getHotelsAPI() {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'get',
    })
}
export function getHotelByIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'GET',
    })
}

export function addHotelCommentAPI(param) {
    return axios({
        url: `${api.hotelPre}/addComment`,
        method: 'POST',
        data: param
    })
}

export function getCommentByHotelIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param}/getCommentByHotelId`,
        method: 'GET'
    })
}

export function updateReplyAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.commentId}/${param.reply}/updateReply`,
        method: 'GET'
    })
}