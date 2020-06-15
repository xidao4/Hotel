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
export function getHotelByIdAPI(currentHotelId) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'POST',
        data: {
            hotelId: param.hotelId,
            userId: param.userId
        }
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
