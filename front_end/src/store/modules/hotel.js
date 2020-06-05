import { message } from 'ant-design-vue'
import store from '@/store'
import {
    getHotelsAPI,
    getHotelByIdAPI,
    addHotelCommentAPI,
    getCommentByHotelIdAPI,
    updateReplyAPI
} from '@/api/hotel'
import {
    reserveHotelAPI
} from '@/api/order'
import {
    orderMatchCouponsAPI,
} from '@/api/coupon'

const hotel = {
    state: {
        hotelList: [
            
        ],
        hotelListParams: {
            pageNo: 0,
            pageSize: 12
        },
        hotelListLoading: true,
        currentHotelId: '',
        currentHotelInfo: {

        },
        orderModalVisible: false,
        currentOrderRoom: {

        },
        orderMatchCouponList: [

        ],
        comment: [

        ]
    },
    mutations: {
        set_comment:function(state,data){
            state.comment=data
        },
        set_hotelList: function(state, data) {
            state.hotelList = data
        },
        set_hotelListParams: function(state, data) {
            state.hotelListParams = {
                ...state.hotelListParams,
                ...data,
            }
        },
        set_hotelListLoading: function(state, data) {
            state.hotelListLoading = data
        },
        set_currentHotelId: function(state, data) {
            state.currentHotelId = data
        },
        set_currentHotelInfo: function(state, data) {
            state.currentHotelInfo = {
                ...state.currentHotelInfo,
                ...data,
            }
        },
        set_orderModalVisible: function(state, data) {
            state.orderModalVisible = data
        },
        set_currentOrderRoom: function(state, data) {
            state.currentOrderRoom = {
                ...state.currentOrderRoom,
                ...data,
            }
        },
        set_orderMatchCouponList: function(state, data) {
            state.orderMatchCouponList = data
        }
    },

    actions: {
        getHotelList: async({commit, state}) => {
            const res = await getHotelsAPI()
            if(res){
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getHotelById: async({commit, state}) => {
            const res = await getHotelByIdAPI({
                hotelId: state.currentHotelId
            })
            if(res){
                commit('set_currentHotelInfo', res)
            }
        },
        addOrder: async({ state, commit }, data) => {
            const res = await reserveHotelAPI(data)
            console.log(res)
            if(res){
                message.success('预定成功')
                commit('set_orderModalVisible', false)
            }
        },
        getOrderMatchCoupons: async({ state, commit }, data) => {
            const res = await orderMatchCouponsAPI(data)
            if(res){
                commit('set_orderMatchCouponList', res)
            }
        },
        addHotelComment: async({ state, commit }, data) => {
            console.log('Vuex里调用addComment')
            const res = await addHotelCommentAPI(data)
            if(res){
                message.success('感谢你的评价～')
            }
            else {
                message.error('评价失败，一会儿再试试吧')
            }
        },
        getCommentByHotelId: async({ state, commit }, data) => {
            console.log('Vuex里调用getCommentByHotelId')
            console.log(data)
            const res = await getCommentByHotelIdAPI(data)
            console.log(res)
            if(res){
                console.log('改变Vuex里的comment')
                console.log(res)
                commit('set_comment', res)
            }
        },
        updateReply: async({ state, commit }, data) => {
            console.log('Vuex里调用updateReply')
            console.log(data)
            const res = await updateReplyAPI(data)
            console.log(res)
            if(res){
                console.log('updateReply成功')
                message.success('回复成功！')
                // const r=await this.getCommentByHotelId(this.currentHotelId)
            }
        },

    }
}

export default hotel