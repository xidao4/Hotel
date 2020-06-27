import {
    getCreditRecordsAPI,
    updateCreditAPI,
    getCreditByIdAPI,
    cancelUpdateAPI,
} from '../../api/credit';

import {
    getOrderAPI,
} from '../../api/order';


import {message} from 'ant-design-vue';
import { getUserInfoAPI } from "../../api/user";
import {getCurveDatasetAPI} from "../../api/admin";

const state = {
    // 组件可见性
    manageCreditVisible: false,
    // 订单管理相关
    currentOrderId: '',   // 当前展示的orderId
    currentOrderDetail: [],
    currentUpdateInfo: {},
    // 信用记录相关
    creditRecordsList: [],
    dateList: [],
    numList: [],
    showOrderList: []
};

const operator = {
    state,
    mutations: {
        set_showOrderList(state,data){
            state.showOrderList = [...data]
        },
        set_manageCreditVisible(state, data) {
            state.manageCreditVisible = data
        },
        set_currentOrderId(state, data) {
            state.currentOrderId = data
        },
        set_currentOrderDetail(state, data) {
            state.currentOrderDetail = [...data]
        },
        set_currentUpdateInfo(state, data) {
            state.currentUpdateInfo = {
                ...state.currentUpdateInfo,
                ...data
            }
        },
        clear_currentUpdateInfo(state) {
            state.currentUpdateInfo = {}
        },
        set_creditRecordsList(state, data) {
            state.creditRecordsList = [...data]
        },
        set_dateList: function (state, data) {
            state.dateList = [...data]
        },
        set_numList: function (state, data) {
            state.numList = [...data]
        },
    },
    actions: {
        getCurveDataset: async ({commit, state}) => {
            const res = await getCurveDatasetAPI()
            if(res){
                //console.log("module")
                //console.log(res)
                commit('set_dateList', res.dateList)
                commit('set_numList', res.numList)
                //console.log("module_fin")
            }else{
                console.log("error")
            }
        },
        getUserCreditById: async ({state, commit}, param) => {
            const res = await getCreditByIdAPI(param);
            if(res) {
                commit('set_currentUpdateInfo', res);
            }
        },
        getCreditRecords: async ({commit}) => {
            const res = await getCreditRecordsAPI();
            if(res) {
                commit('set_creditRecordsList', res)
            }
        },
        updateCredit: async ({commit}, param) => {
            const res = await updateCreditAPI(param);
            if(res) {
                commit('set_manageCreditVisible', false)
                commit('clear_currentUpdateInfo')
                message.success('更新成功')
            }
        },
        getOrderDetail: async ({commit}, param) => {
            const res = await getOrderAPI(param);
            if(res) {
                commit('set_currentOrderDetail', res)
            }
        },
        cancelUpdate: async ({dispatch}, param) => {
            const res = await cancelUpdateAPI(param);
            if(res) {
                message.success('撤销成功')
                dispatch('getCreditRecords')
            }
        },

    }
};

export default operator
