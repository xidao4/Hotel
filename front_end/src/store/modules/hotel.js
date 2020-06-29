import { message } from 'ant-design-vue'
import store from '@/store'
import {
    getHotelsAPI,
    getHotelByIdAPI,
    getHotelByDateAPI,
    getHotelByIdLJYAPI,
    getAllHotelsLJYAPI,
    addHotelCommentAPI,
    getCommentByHotelIdAPI,
    updateReplyAPI,
    getHotelCardInfosAPI
} from '@/api/hotel'
import {
    reserveHotelAPI
} from '@/api/order'
import {
    orderMatchCouponsAPI,
    deleteCouponAPI
} from '@/api/coupon'
import {
    updateHotelInfoAPI
} from "../../api/hotel";


const hotel = {
    state: {
        hotelList: [

        ],
        showHotelList: [

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
        orderMatchCouponList: [],
        comment: [

        ],
        hasShownNoti:false,

        showFilter: false,
    },
    mutations: {
        set_userId: function(state, data) {
            state.userId = data
        },
        set_comment:function(state,data){
            state.comment=data
        },
        set_hotelList: function(state, data) {
            state.hotelList = data
        },
        set_showHotelList:function(state, data) {
            state.showHotelList = [...data]
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
        },
        set_hasShownNoti:function(state,data){
            state.hasShownNoti=data
        },
        set_showFilter:function(state,data){
            state.showFilter=data
        },
    },

    actions: {
        getHotelList: async({commit, state},data) => {
            const res = await getHotelsAPI({
                userId: data
            })
            if(res){
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getHotelListLJY:async({commit,state})=>{
            const res=await getAllHotelsLJYAPI()

            if(res){
                commit('set_hotelList',res)
                console.log('添加后，getAllHotelsLJY',state.hotelList)
                commit('set_hotelListLoading',false)
            }
        },
        getHotelById: async({commit, state}, data) => {
            const res = await getHotelByIdAPI({
                hotelId: state.currentHotelId,
                userId: data
            })
            console.log("module")
            console.log(state.currentHotelId)
            console.log(data)
            if(res){
                console.log('hotelInfo',res)
                commit('set_currentHotelInfo', res)
            }
        },
        getHotelCardInfos: async({rootState, commit}) => {
            const res = await getHotelCardInfosAPI({
                userId: rootState.user.userId
            })
            if(res) {
                commit('set_showHotelList', res);
            }
        },
        getHotelByIdLJY:async({commit,state})=>{
          const res=await getHotelByIdLJYAPI(state.currentHotelId)
          if(res)
              commit('set_currentHotelInfo',res)
        },
        getHotelByDate: async({commit, state}, data) => {
            const res = await getHotelByDateAPI(data)
            console.log(data)
            if (res) {
                commit('set_hotelListLoading', false)
            }
        },
        addOrder: async({ state, commit }, data) => {
            const res = await reserveHotelAPI(data)
            console.log('预定结果')
            console.log(res)
            if(res!='预订房间数量剩余不足'){
                message.success('预定成功')
                commit('set_orderModalVisible', false)
            }
            else{
                message.error('房间数量不足')
            }
        },
        getOrderMatchCoupons: async({ state, commit }, data) => {
            const res = await orderMatchCouponsAPI(data)
            if(res){
                commit('set_orderMatchCouponList', res)
            }
        },
        getHotelInfo:async({dispatch})=>{
            // console.log("in the method getHotelInfo")
            // console.log("state.userId",state.userId)//undefined
            // console.log("state.hotelId",state.hotelId)//undefined
            // console.log("state.currentHotelId",state.currentHotelId)//1
            dispatch('getHotelByIdLJY')
        },
        updateHotelInfo:async({state,dispatch},data)=>{
            const params = {
                id: state.currentHotelId,
                ...data,
            }
            const res=await updateHotelInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getHotelInfo')
            }
        },
        addHotelComment: async({ state, commit }, data) => {
            console.log('Vuex里调用addComment')
            console.log(data)
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
        deleteCoupon: async({ state, commit }, data) => {
            console.log('Vuex里调用deleteCoupon')
            console.log(data)
            const res = await deleteCouponAPI(data)
            console.log(res)
            if(res){
                console.log('删除优惠券成功')
                message.success('删除成功！')
            }
        },
    }
}

export default hotel
