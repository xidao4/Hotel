import {
    getOperatorListAPI,
    getClientListAPI,
    getManagerListAPI,
    addOperatorAPI,
    deleteUserAPI,
    updateTmpUserInfoAPI,
    addManagerAPI
} from '@/api/admin'
import{
    getUserInfoAPI
} from '@/api/user'
import{
    getHotelsAPI,
    addHotelAPI
}from '@/api/hotel'
import { message } from 'ant-design-vue'

const admin = {
    state: {
        addManagerModalVisible: false,
        addManagerParams: [],
        operatorList:[],
        displayOperatorList:[],
        adminHotelList:[],
        clientList:[],
        managerList: [],
        tmpUserInfo:[],
        addOperatorModalVisible: false,
        addOperatorParams: {
            email:'',
            userName:'',
            password:'',
        },
        modifyOOModalVisible:false,
        OOIdx:'',
        isSearching:false,
        displayClientList:'',
        tmpClientId:'',
        tmpClientInfo:[],
        modifyClientModalVisible:false,
        HMList:[],
        displayHMList:[],
        addHMModalVisible:false,
        modifyHMModalVisible:false,
        addHotelModalVisible:false,
        addMModalVisible:false,
        tmpHotelId:'',
    },
    mutations: {
        set_operatorList:function(state,data){
            state.operatorList=data
        },
        set_displayOperatorList:function(state,data){
            state.displayOperatorList=data
        },
        set_adminHotelList: function(state,data){
            state.adminHotelList=data
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
        },
        set_isSearching: function (state,data) {
            state.isSearching=data
        },
        set_displayClientList:function(state,data){
            state.displayClientList=data
        },
        set_tmpClientId:function(state,data){
            state.tmpClientId =data
        },
        set_tmpClientInfo: function(state,data){
            state.tmpClientInfo={
                ...state.tmpClientInfo,
                ...data
            }
        },
        set_modifyClientModalVisible:function (state,data) {
            state.modifyClientModalVisible=data
        },
        set_HMList:function (state,data) {
            state.HMList=data
        },
        set_displayHMList:function (state,data) {
            state.displayHMList=data
        },
        set_addHMModalVisible:function (state,data){
            state.addHMModalVisible=data
        },
        set_modifyHMModalVisible:function (state,data) {
            state.modifyHMModal=data
        },
        set_addHotelModalVisible:function (state,data) {
            state.addHotelModalVisible=data
        },
        set_addMModalVisible:function (state,data) {
            state.addMModalVisible=data
        },
        set_tmpHotelId:function (state,data) {
            state.tmpHotelId=data
        },
    },
    actions: {
        searchOO:async({commit},data)=>{
            const res=await searchOOAPI(data)
            if(res){
                commit('set_displayOperatorList',res)
            }
            commit('set_isSearching',true)
        },
        getOperatorList:async({commit,state})=>{
            const res=await getOperatorListAPI()
            if(res) {
                commit('set_operatorList', res)
                commit('set_displayOperatorList', res)
                console.log('operatorList',state.operatorList)
            }
        },
        getClientList: async({commit}) => {
            const res=await getClientListAPI()
            if(res){
                commit('set_clientList',res)
                commit('set_displayClientList',res)
            }
        },
        getManagerList: async({ commit }) => {
            const res = await getManagerListAPI()
            if(res){
                commit('set_managerList', res)
            }
        },
        addOperator: async({ state, commit, dispatch }) => {
            console.log('adOperatorParams 表单提交的数据',state.addOperatorParams)//right
            const res = await addOperatorAPI(state.addOperatorParams)
            if(res) {
                commit('set_addOperatorParams',{
                    email:'',
                    userName:'',
                    password:'',
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
            //console.log('in getTmpUserInfo >> state.OOIdx',state.OOIdx)
            const res = await getUserInfoAPI(state.OOIdx)
            if(res){

                commit('set_tmpUserInfo', res)
                //console.log('in getTmpUserInfo >> state.tmpUserInfo',state.tmpUserInfo)
                console.log('getTmpUserInfo tmpUserInfo',res)
            }
            commit('set_modifyOOModalVisible',true)
        },
        updateTmpUserInfo:async({state,commit,dispatch},data)=>{
            const params = {
                id: state.OOIdx,
                ...data,
            }
            const res = await updateTmpUserInfoAPI(params)
            if(res){
                message.success('修改成功')
                //dispatch('getTmpUserInfo')
                dispatch('getOperatorList')
                dispatch('getManagerList')
                //console.log("0530,in updateTmpUserInfo,admin.js",this.operatorList)

            }
        },
        getTmpClientInfo:async({state,commit})=>{
            const res=await getUserInfoAPI(state.tmpClientId)
            if(res){
                commit('set_tmpClientInfo',res)
            }
        },
        updateTmpClientInfo:async({state,commit,dispatch},data)=>{
            const params = {
                id: state.tmpClientId,
                ...data,
            }
            const res = await updateTmpUserInfoAPI(params)
            if(res){
                message.success("修改成功")
                dispatch('getTmpClientInfo')
                dispatch('getClientList')
            }
        },
        searchClient:async({commit},data)=>{
            const res=await searchClientAPI(data)
            if(res){
                commit('set_displayClientList',res)
            }
            commit('set_isSearching',true)
        },
        addHotel:async({commit,dispatch,state},data)=>{
            const res = await addHotelAPI(data)  //success数据库显示已经加入
            if(res) {
                commit('set_addHotelModalVisible', false)
                message.success('添加成功')
                dispatch('getHotelListLJY')
                console.log('hotel.hotelList 获取全部酒店列表后',state.hotelList)
            }else{
                message.error('添加失败')
            }
        },
        addManager:async({commit,dispatch},data)=>{
            const res = await addManagerAPI(data)
            if(res) {
                commit('set_addMModalVisible', false)
                message.success('添加成功')
                dispatch('getManagerList')
                dispatch('getHotelListLJY')
            }else{
                message.error('添加失败')
            }
        },
    }
}
export default admin
