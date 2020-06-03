import {
    getCreditRecordsAPI,
    updateCreditAPI,
    getCreditByIdAPI,
} from '../../api/credit'
import { getUserInfoAPI } from "../../api/user";

const state = {
    manageCreditVisible: false,
    currentOrderId: '',   // 当前展示的orderId
    currentUpdateInfo: {},
    creditRecordsList: [],
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
        }
    },
    actions: {
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
