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
export function getHotelByDateAPI(param) {
    return axios({
        url: `${api.hotelPre}/inDateRange`,
        method: 'POST',
        data: param
    })
}
