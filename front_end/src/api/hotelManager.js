import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function addRoomAPI(data) {
    return axios({
        url: `${api.hotelPre}/roomInfo`,
        method: 'POST',
        data,
    })
}

export function updateRoomAPI(data) {
    return axios({
        url: `${api.hotelPre}/updateRoom/${data.hotelId}/${data.roomType}/${data.curNum}/${data.total}/${data.price}`,
        method: 'GET',
    })
}


