// 在Vuex里面向后端发起请求
import {
    addRoomAPI,
    addHotelAPI,
    updateRoomAPI,
    changePicAPI,
    uploadPicAPI,
    getPicsAPI,
} from '@/api/hotelManager'
import {
    getAllOrdersAPI,
    changeStatusAPI,
    getOrderByHotelIdAPI
} from '@/api/order'
import {
    hotelAllCouponsAPI,
    hotelTargetMoneyAPI,
    hotelTimeAPI,
    hotelTargetRoomNumAPI
} from '@/api/coupon'
// 提示组件
import { message } from 'ant-design-vue'
import {hotelBirthdayAPI, hotelFestivalAPI} from "../../api/coupon";

const hotelManager = {
    state: {
        orderList: [],
        addHotelParams: {
            name: '',
            address: '',
            bizRegion:'XiDan',
            hotelStar:'',
            rate: 0,
            description:'',
            phoneNum:'',
            managerId:'',
        },
        addHotelModalVisible: false,
        addRoomParams: {
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
        },
        addRoomModalVisible: false,
        couponVisible: false,
        addCouponVisible: false,
        activeHotelId: 0,
        couponList: [],
        bigV: '',
        doubleV: '',
        familyV: '',
        pic: '',
        fileList: [],
    },
    // actions里面主要处理异步事件，mutation则反之
    mutations: {
        set_bigV:function(state){
            state.bigV=true
        },
        set_doubleV:function(state){
            state.doubleV=true
        },
        set_familyV:function(state){
            state.familyV=true
        },
        set_orderList: function(state, data) {
            state.orderList = data
        },
        // es6中的一种新的赋值方式，...代表着将对象拆分成一个个键值对，然后'{}'内的所有键值对重新组成一个新的对象
        // 在这里，就是将data里的键值对加到addHotelParams里面
        set_addRoomModalVisible: function(state, data) {
            state.addRoomModalVisible = data
        },
        set_addRoomParams: function(state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            }
        },
        set_couponVisible: function(state, data) {
            state.couponVisible = data
        },
        set_activeHotelId: function(state, data) {
            state.activeHotelId = data
        },
        set_couponList: function(state, data) {
            state.couponList = data
        },
        set_addCouponVisible: function(state, data) {
            state.addCouponVisible =data
        },
        set_pic: function (state, data) {
            state.pic = data
        },
        set_fileList: function (state,data) {
            state.fileList = data
        }
    },
    actions: {
        getPicUrl:async ({commit,state},data)=>{
            const res = await uploadPicAPI(data)
            if(res){
                /*console.log("module")
                console.log(res)*/
                commit('set_pic',res)
            }else{
                console.log("error!")
            }
        },
        changePic:async ({rootState},data)=>{
            await changePicAPI(data)
        },
        getPics:async ({commit,state},data)=>{
            const res = await getPicsAPI(data)
            if(res){
                console.log("module",res)
                commit('set_fileList',res)
            }else{
                console.log("error!")
            }
        },
        getAllOrders: async({ state, commit }) => {
            const res = await getAllOrdersAPI()
            // res非空时进行
            if(res){
                commit('set_orderList', res)
            }
        },
        getOrderByHotelId: async({ state, commit },data) => {
            const res = await getOrderByHotelIdAPI(data)
            // res非空时进行
            if(res){
                commit('set_orderList', res)
            }
        },
        // addHotel: async({ state, dispatch, commit }) => {
        //     const res = await addHotelAPI(state.addHotelParams)
        //     if(res){
        //         dispatch('getHotelList')
        //         commit('set_addHotelParams', {
        //             name: '',
        //             address: '',
        //             bizRegion:'XiDan',
        //             hotelStar:'',
        //             rate: 0,
        //             description:'',
        //             phoneNum:'',
        //             managerId:'',
        //         })
        //         commit('set_addHotelModalVisible', false)
        //         message.success('添加成功')
        //     }else{
        //         message.error('添加失败')
        //     }
        // },
        addRoom: async({ state, dispatch, commit }) => {
            console.log('调用API之前')
            const res = await addRoomAPI(state.addRoomParams)
            console.log('调用API之后')
            console.log(res)
            if(res===null){
                commit('set_addRoomModalVisible', false)
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                })
                message.success('添加房间成功')
            }else{
                message.error('添加失败')
            }
        },
        updateRoom: async({ state, commit },data) => {
            const res = await updateRoomAPI(data)
            if(res) {
                commit('set_addRoomModalVisible', false)
                message.success('更新酒店房间信息成功')
            }
            else{
                message.error('更新失败')
            }
        },
        getHotelCoupon: async({ state, commit }) => {
            const res = await hotelAllCouponsAPI(state.activeHotelId)
            if(res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                commit('set_couponList',res)
            }
        },
        addHotelTargetMoneyCoupon: async({ commit, dispatch }, data) => {
            const res = await hotelTargetMoneyAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible',false)
                commit('set_couponVisible',true)
                message.success('添加策略成功')
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        addHotelTimeCoupon: async({ commit, dispatch }, data) => {
            const res = await hotelTimeAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible',false)
                commit('set_couponVisible',true)
                message.success('添加策略成功')
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        addHotelTargetRoomNumCoupon: async({ commit, dispatch }, data) => {
            const res = await hotelTargetRoomNumAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible',false)
                commit('set_couponVisible',true)
                message.success('添加策略成功')
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        addHotelBirthdayCoupon: async({ commit, dispatch }, data) => {
            const res = await hotelBirthdayAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible',false)
                commit('set_couponVisible',true)
                message.success('添加策略成功')
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        addHotelFestivalCoupon: async({ commit, dispatch }, data) => {
            const res = await hotelFestivalAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible',false)
                commit('set_couponVisible',true)
                message.success('添加策略成功')
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        addHotelVIPSpecialCoupon: async({ commit, dispatch }, data) => {
            const res = await hotelVIPSpecialAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible',false)
                commit('set_couponVisible',true)
                message.success('添加策略成功')
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        changeStatus: async({commit},data) =>{
            const res=await changeStatusAPI(data)
            if(res){
                message.success('更改状态成功')
            }
            else {
                message.error('更改失败')
            }
        }
    }
}
export default hotelManager
