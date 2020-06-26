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

export function changePicAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data.hotelId}/changePic`,
        method: 'POST',
        data: data.pics,
    })
}

export function uploadPicAPI(data) {
    return axios({
        url: `${api.hotelPre}/upload`,
        method: 'POST',
        data: data,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

export function getPicsAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data}/getPics`,
        method: 'GET',
    })
}
