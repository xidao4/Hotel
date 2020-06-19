import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function getHotelsAPI(param) {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'POST',
        data: {
            userId: param.userId
        }
    })
}
export function getAllHotelsLJYAPI(){
    return axios({
        url: `${api.hotelPre}/allHotels`,
        method: 'get',
    })
}
export function getHotelByIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'POST',
        data: {
            hotelId: param.hotelId,
            userId: param.userId
        }
    })
}
export function getHotelByIdLJYAPI(currentHotelId){
    return axios({
        url:`${api.hotelPre}/${currentHotelId}/currentHotelInfo`,
        method:'GET',
    })
}
export function getHotelByDateAPI(param) {
    return axios({
        url: `${api.hotelPre}/inDateRange`,
        method: 'POST',
        data: param
    })
}
export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data
    })
}
export function getManagerIdByHotelIdAPI(hotelId) {
    return axios({
        url:`${api.hotelPre}/${hotelId}/getManagerId`,
        method:'POST',
    })
}
export function getHotelIdByManagerIdAPI(managerId) {
    return axios({
        url:`${api.hotelPre}/${managerId}/getHotelId`,
        method:'POST'
    })
}
export function updateHotelInfoAPI(data){
    return axios({
        url:`${api.hotelPre}/${data.id}/hotelInfo/update`,
        method:'POST',
        data
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