import {
    getOperatorListAPI,
    getClientListAPI,
    getManagerListAPI,
    addOperatorAPI,
    deleteUserAPI
} from '@/api/admin'
import{
    getHotelsAPI
}from '@/api/hotel'
import { message } from 'ant-design-vue'

const admin = {
    state: {
        operatorList:[],
        hotelList:[],
        clientList:[],
        managerList: [],
        addOperatorModalVisible: false,
        addOperatorParams: {
            email:'',
            userName:'',
            password:''
        },
        addhotelAndManagerList:{
            id:'',
            name:'',
            manager_id:'',
            email:''
        }
    },
    mutations: {
        set_operatorList:function(state,data){
            state.operatorList=data
        },
        set_hotelAndManagerList: function(state,hotelList,hotelManagerList){

        },
        set_hotelList: function(state,data){
            state.hotelList=data
        },
        set_clientList:function(state,data){
            state.clientList=data
        },
        set_managerList: function(state, data) {
            state.managerList = data
        },
        set_addOperatorModalVisible: function(state, data) {
            state.addOperatorModalVisible = data
        },
        set_addOperatorParams: function(state, data) {
            state.addOperatorParams = {
                ...state.addOperatorParams,
                ...data,
            }
        }
    },
    actions: {
        getOperatorList:async({commit})=>{
            const res=await getOperatorListAPI()
            if(res){commit('set_operatorList',res)}
        },
        getHotelList: async({commit})=>{
            const res=await getHotelsAPI()
            if(res){
                commit('set_hotelList',res)
            }
        },
        getClientList: async({commit}) => {
            const res=await getClientListAPI()
            if(res){
                commit('set_clientList',res)
            }
        },
        getManagerList: async({ commit }) => {
            const res = await getManagerListAPI()
            if(res){
                commit('set_managerList', res)
            }
        },
        addOperator: async({ state, commit, dispatch }) => {
            const res = await addOperatorAPI(state.addOperatorParams)
            if(res) {
                commit('set_addOperatorParams',{
                    email:'',
                    userName:'',
                    password:''
                })
                commit('set_addOperatorModalVisible', false)
                message.success('添加成功')
                dispatch('getOperatorList')
            }else{
                message.error('添加失败')
            }
        },
        deleteUser:async({dispatch},userId)=>{
            const res=await deleteUserAPI(userId)
            if(res){
                dispatch('getClientList')
            }else{
                message.error('删除失败')
            }
        }
    }
}
export default admin
