import Vue from 'vue'
import router from '@/router'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import { message } from 'ant-design-vue'
import {
    loginAPI,
    registerAPI,
    getUserInfoAPI,
    updateUserInfoAPI,
    getMemInfoAPI,
    registerMemAPI,
    increaseMemberPointsAPI,
    decreaseMemberPointsAPI
} from '@/api/user'
import {
    getUserOrdersAPI,
    cancelOrderAPI,
    getOrderAPI
} from '@/api/order'
import{
    getHotelIdByManagerIdAPI
}from '@/api/hotel'

const getDefaultState = () => {
    return {
        userId: '',
        userInfo: {

        },
        userOrderList: [

        ],
        idOrder:{
            id: -1
        },
        memInfo: {
            memberPoints: '',
            birthday: ''
        },
        isMember:false,
        registerModalVisible:false,
        hotelId:'',
    }
}

const user = {
    state : getDefaultState(),

    mutations: {
        reset_state: function(state) {
            state.token = '',
            state.userId = '',
            state.userInfo = {

            },
            state.userOrderList = [],
            state.membership='',
            state.memInfo=[],
            state.hotelId=''
        },
        set_token: function(state, token){
            state.token = token
        },
        set_email: (state, data) => {
            state.email = data
        },
        set_userId: (state, data) => {
            state.userId = data
        },
        set_userInfo: (state, data) => {
            state.userInfo = {
                ...state.userInfo,
                ...data
            }
        },
        set_userOrderList: (state, data) => {
            state.userOrderList = data
        },
        set_idOrder: (state,data)=> {
            state.idOrder = data
        },
        set_memInfo:(state,data)=>{
            state.memInfo=data
        },
        set_registerModalVisible:(state,data)=>{
            state.registerModalVisible=data
        },
        set_isMember:(state,data)=>{
            state.isMember=data
        },
        set_hotelId:(state,data)=>{
            state.hotelId=data
        }
    },

    actions: {
        login: async ({ state,dispatch, commit }, userData) => {
            const res = await loginAPI(userData)
            if(res){
                setToken(res.id)
                commit('set_userId', res.id)

                if(res.userType==='HotelManager'){
                    const hotelId=await getHotelIdByManagerIdAPI(res.id)
                    commit('set_hotelId',hotelId)
                    commit('set_currentHotelId',hotelId)
                    console.log('state.currentHotelId',state.currentHotelId)//undefined
                    console.log("state.hotelId",state.hotelId)//1
                    //console.log("this.userId",this.userId)//uncaught typeError: cannot read property 'userId' of undefined
                    console.log("state.userId",state.userId)//1
                }

                if(res.userType==='Manager'){
                    router.push('/websiteAdmin')
                }else {
                    dispatch('getUserInfo')
                    router.push('/hotel/hotelList')
                }
            }
        },
        register: async({ commit }, data) => {
            console.log(data.userType);
            const res = await registerAPI(data)
            if(res){
                message.success('注册成功')
            }
        },
        getUserInfo({ state, commit }) {
            return new Promise((resolve, reject) => {
              getUserInfoAPI(state.userId).then(response => {
                const data = response
                if (!data) {
                  reject('登录已过期，请重新登录')
                }
                commit('set_userInfo', data)
                commit('set_userId', data.id)
                resolve(data)
              }).catch(error => {
                reject(error)
              })
            })
        },
        updateUserInfo: async({ state, dispatch }, data) => {
            const params = {
                id: state.userId,
                ...data,
            }
            const res = await updateUserInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        getUserOrders: async({ state, commit }) => {
            const data = {
                userId: Number(state.userId)
            }
            const res = await getUserOrdersAPI(data)
            if(res){
                commit('set_userOrderList', res)
                console.log(state.userOrderList)
            }
        },
        getOrderById: async({state,commit},data)=>{
            const res=await getOrderAPI(data)
            if(res){
                console.log('getOrderById')
                console.log(res)
                commit('set_idOrder',res)
                console.log('已取回目标id的记录')
            }
        },
        cancelOrder: async({ state, dispatch },data) => {
            const res = await cancelOrderAPI(data)
            console.log('user.js')
            console.log(data.reason)
            if(res) {
                dispatch('getUserOrders')
                message.success('撤销成功')
            }else{
                message.error('撤销失败')
            }
        },
        logout: async({ commit }) => {
            removeToken()
            resetRouter()
            commit('reset_state')
        },
          // remove token
        resetToken({ commit }) {
            return new Promise(resolve => {
                removeToken() // must remove  token  first
                commit('reset_state')
                resolve()
            })
        },
        getMemInfo:async ({state,commit})=>{
            const res=await getMemInfoAPI(state.userId)
            if(!res){
                commit('set_isMember',false)
            }else{
                commit('set_isMember',true)
                commit('set_memInfo',res)
            }
        },
        registerMem:async ({commit,dispatch},data)=>{
            const res = await registerMemAPI(data)
            if(res){
                message.success('注册成功')
            }
            dispatch('getMemInfo')
        },
        increaseMemberPoints:async({commit},data)=>{
            const res=await increaseMemberPointsAPI(data)
            if(res){
                message.success('增加会员积分成功')
            }
        },
        decreaseMemberPoints:async({commit},data)=>{
            const res=await decreaseMemberPointsAPI(data)
            if(res){
                message.success('扣除会员积分成功')
            }
        }
    }
}

export default user
