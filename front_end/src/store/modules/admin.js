import {
    getOperatorListAPI,
    getClientListAPI,
    getManagerListAPI,
    addOperatorAPI,
    deleteUserAPI,
    updateTmpUserInfoAPI
} from '@/api/admin'
import{
    getUserInfoAPI
} from '@/api/user'
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
        tmpUserInfo:[],
        addOperatorModalVisible: false,
        addOperatorParams: {
            email:'',
            userName:'',
            password:''
        },
        modifyOOModalVisible:false,
        addHotelAndManagerList:{
            id:'',
            name:'',
            manager_id:'',
            email:''
        },
        OOIdx:''
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
        },
        set_modifyOOModalVisible: function(state,data){
            state.modifyOOModalVisible=data
        },
        set_OOIdx:function(state,data){
            state.OOIdx=data
        },
        set_tmpUserInfo: function(state,data){
            state.tmpUserInfo = {
                ...state.tmpUserInfo,
                ...data
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
        },
        getTmpUserInfo:async({ state, commit })=> {
            console.log('in getTmpUserInfo >> state.OOIdx',state.OOIdx)
            const res = await getUserInfoAPI(state.OOIdx)
            if(res){
                commit('set_tmpUserInfo', res)
                console.log('in getTmpUserInfo >> state.tmpUserInfo',state.tmpUserInfo)
            }
            commit('set_modifyOOModalVisible',true)
        },
        updateTmpUserInfo:async({state,dispatch},data)=>{
            const params = {
                id: state.OOIdx,
                ...data,
            }
            const res = await updateTmpUserInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getTmpUserInfo')
                dispatch('getOperatorList')
                console.log("0530,in updateTmpUserInfo,admin.js",this.operatorList)
            }
        }
    }
}
export default admin
