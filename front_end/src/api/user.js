import { axios } from '@/utils/request'

const api = {
    userPre: '/api/user'
}
export function loginAPI(data){
    return axios({
        url:`${api.userPre}/login`,
        method: 'POST',
        data
    })
}
export function registerAPI(data){
    return axios({
        url: `${api.userPre}/register`,
        method: 'POST',
        data
    })
}
export function getUserInfoAPI(id){
    return axios({
        url: `${api.userPre}/${id}/getUserInfo`,
        method: 'GET'
    })
}
export function updateUserInfoAPI(data) {
    return axios({
        url: `${api.userPre}/${data.id}/userInfo/update`,
        method: 'POST',
        data
    })
}
export function getMemInfoAPI(userId) {
    return axios({
        url:`${api.userPre}/${userId}/getMemInfo`,
        method :'GET'
    })

}
export function registerMemAPI(data){
    return axios({
        url: `${api.userPre}/registerMem`,
        method: 'POST',
        data
    })
}
export function increaseMemberPointsAPI(data){
    return axios({
        url:`${api.userPre}/increaseMemberPoints`,
        method:'POST',
        data
    })
}
export function decreaseMemberPointsAPI(data){
    return axios({
        url:`${api.userPre}/decreaseMemberPoints`,
        method:'POST',
        data
    })
}
export function uploadAPI(data) {
    return axios({
        url: `${api.userPre}/upload`,
        method: 'POST',
        data: data,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}
