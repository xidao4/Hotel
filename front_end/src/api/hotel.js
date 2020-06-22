import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
//获取某用户预定过的全部酒店
export function getHotelsAPI(param) {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'POST',
        data: {
            userId: param.userId
        }
    })
}
//获取全部酒店
export function getAllHotelsLJYAPI(){
    return axios({
        url: `${api.hotelPre}/allHotels`,
        method: 'get',
    })
}
//获取某用户预订过的某个酒店的信息
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
//根据Hotelid获取酒店信息
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
//通过酒店id得到该酒店工作人员的id
export function getManagerIdByHotelIdAPI(hotelId) {
    return axios({
        url:`${api.hotelPre}/${hotelId}/getManagerId`,
        method:'POST',
    })
}
//通过酒店工作人员的id获得他所工作酒店的id
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
