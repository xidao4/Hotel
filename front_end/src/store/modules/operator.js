import {
    getCreditRecordsAPI,
    updateCreditAPI,
    getCreditByIdAPI,
} from '../../api/credit'
import { getUserInfoAPI } from "../../api/user";
import {getCurveDatasetAPI} from "../../api/admin";

const state = {
    manageCreditVisible: false,
    currentOrderId: '',   // 当前展示的orderId
    currentUpdateInfo: {},
    creditRecordsList: [],
    dateList: [],
    numList: [],
};

const operator = {
    state,
    mutations: {
        set_manageCreditVisible(state, data) {
            state.manageCreditVisible = data
        },
        set_currentOrderId(state, data) {
            state.currentOrderId = data
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
        getCreditRecords: async ({rootState, commit}) => {
            const res = await getCreditRecordsAPI({
                userId: rootState.userId
            });
            if(res) {
                commit('set_creditRecordsList', res)
            }
        },
        updateCredit: async ({commit}, param) => {
            const res = await updateCreditAPI(param);
            if(res) {
                commit('set_manageCreditVisible', false)
                commit('clear_currentUpdateInfo')
            }
        },
    }
};

export default operator
