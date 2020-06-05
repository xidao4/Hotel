import { axios } from '@/utils/request'
const api = {
    orderPre: '/api/order'
}
export function reserveHotelAPI(data) {
    return axios({
        url: `${api.orderPre}/addOrder`,
        method: 'POST',
        data,
    })
}
export function getAllOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getAllOrders`,
        method: 'GET',
    })
}
export function getUserOrdersAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.userId}/getUserOrders`,
        method: 'GET',
    })
}
export function getOrderAPI(orderid) {
    return axios({
        url: `${api.orderPre}/${orderid}/getOrder`,
        method: 'GET'
    })
}
export function cancelOrderAPI(data) {
    console.log('api/order.js')
    console.log(data.orderid)
    console.log(data.reason)
    return axios({
        url: `${api.orderPre}/${data.orderid}/${data.reason}/annulOrder`,
        method: 'GET',
    })
}

export function changeStatusAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.orderid}/${data.status}/changeStatus`,
        method: 'GET'
    })
}