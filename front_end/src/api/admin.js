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
export function getOperatorListAPI(){
    return axios({
        url: `${api.adminPre}/getAllOperators`,
        method:'POST'
    })
}
export function addOperatorAPI(data) {
    return axios({
        url: `${api.adminPre}/addOperator`,
        method: 'POST',
        data
    })
}
export function addHotelAPI(data) {
    return axios({
        url: `${api.adminPre}/addHotel`,
        method: 'POST',
        data
    })
}
export function deleteUserAPI(data) {
    return axios({
        url: `${api.adminPre}/${data}/deleteUser`,
        method: 'POST',
    })
}
