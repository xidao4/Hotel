import { axios } from '@/utils/request'
const api = {
    adminPre: '/api/admin'
}
export function getClientListAPI(){
    return axios({
        url: `${api.adminPre}/getAllClients`,
        method:'POST'
    })
}
export function getManagerListAPI(){
    return axios({
        url: `${api.adminPre}/getAllManagers`,
        method: 'POST'
    })
}
export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addManager`,
        method: 'POST',
        data
    })
}
export function addHotelAPI(data) {
    return axios({
        url: `${api.adminPre}/addHotel`,
        method: 'POST',
        data,
    })
}
