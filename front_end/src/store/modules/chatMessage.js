import {
    changeMessageStatusAPI,
    getChatByIdAPI,
    getQuesByIdAPI,
    getQuesForClientByIdAPI,
    sendMessageAPI,
    getBroadcastListAPI,
    sendBroadcastAPI,
    updatePriorityAPI,
    changeBroadcastStatusAPI
} from '../../api/chatMessage';

import {getClientListAPI} from '../../api/admin';

import {message} from "ant-design-vue";

const state = {
    //组件可见性
    LeaveMsgModalVisible: false,
    //client
    clientQuesList: [],
    clientQuesListForC: [],
    //op
    currentQuesId: null,
    quesRecords: [],          //对于某问题的聊天记录
    currentQuesStatus: '',
    // 推送相关
    msgClientList: [],
    currentGroupType: null,
    currentGroupTos: [],
    broadcastList: []
}

const chatMessage = {
    state,
    mutations: {
        set_LeaveMsgModalVisible(state, data) {
            state.LeaveMsgModalVisible = data
        },
        set_clientQuesList(state, data) {
            state.clientQuesList = [...data]
        },
        set_currentQuesId(state, data) {
            state.currentQuesId = data
        },
        set_quesRecords(state, data) {
            state.quesRecords = [...data]
        },
        set_clientQuesListForC(state, data){
            state.clientQuesListForC = [...data]
        },
        set_currentQuesStatus(state, data) {
            state.currentQuesStatus = data
        },
        set_msgClientList(state, data) {
            state.msgClientList = [...data]
        },
        set_currentGroupType(state, data) {
            state.currentGroupType = data
        },
        set_currentGroupTos(state, data) {
            state.currentGroupTos = [...data]
        },
        set_broadcastList(state, data) {
            state.broadcastList = [...data]
        },
    },
    actions: {
        getClientQuesList: async ({commit, rootState}) => {
            const res = await getQuesByIdAPI({
                toId: rootState.user.userId
            });
            if(res) {
                commit('set_clientQuesList', res)
            }
        },

        leaveMessage: async ({rootState}, param) => {
            const res = await sendMessageAPI({
                title: param.title,
                content: param.content,
                from: rootState.user.userId,
                tos: [param.operatorId],
                type: 1,
                retMsgId: -1
            });
            if(res) {
                message.success('发送成功')
            }
        },

        getQuesDetail: async ({state, commit}) => {
            const res = await getChatByIdAPI({
                id: state.currentQuesId
            });
            if(res) {
                commit('set_quesRecords', res)
            }
        },

        chat: async ({rootState, commit}, param) => {
            const res = await sendMessageAPI({
                title: '',
                content: param.content,
                from: rootState.user.userId,
                tos: [param.clientId],
                type: 1,
                retMsgId: param.retMsgId
            });
            if(res) {
                message.success('发送成功')
            }
        },

        getClientQuesListForC: async ({rootState, commit}) => {
            const res = await getQuesForClientByIdAPI({
                fromId: rootState.user.userId
            });
            if(res) {
                commit('set_clientQuesListForC', res);
            }
        },

        changeMessageStatus: async ({rootState, commit}, param) => {
            const res = await changeMessageStatusAPI(param);
            if(res) {

            }
        },
        getMsgClientList: async ({commit}) => {
            const res = await getClientListAPI();
            if(res) {
                commit('set_msgClientList', res);
            }
        },
        sendBroadcast: async ({state, rootState}, param) => {
            const res = await sendBroadcastAPI({
                createdId: rootState.user.userId,
                title: param.title,
                content: param.content,
                priority: param.priority
            });
            if(res){
                return true;
            } else {
                message.error(res);
                return false
            }
        },
        getBroadcastList: async ({commit}) => {
            const res = await getBroadcastListAPI();
            if (res) {
                commit('set_broadcastList', res)
            }
        },
        changeBroadcastStatus: async ({state}, param) => {
            const res = await changeBroadcastStatusAPI({
                id: param.id,
                status: param.status
            })
        },
    }
}

export default chatMessage
