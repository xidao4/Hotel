import {
    getOperatorListAPI,
    getClientListAPI,
    getManagerListAPI,
    addManagerAPI,
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
        addManagerModalVisible: false,
        addManagerParams: {
            email:'',
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
        set_addManagerModalVisible: function(state, data) {
            state.addManagerModalVisible = data
        },
        set_addManagerParams: function(state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
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
        addManager: async({ state, commit, dispatch }) => {
            const res = await addManagerAPI(state.addManagerParams)
            if(res) {
                commit('set_addManagerParams',{
                    email:'',
                    password:''
                })
                commit('set_addManagerModalVisible', false)
                message.success('添加成功')
                dispatch('getManagerList')
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
