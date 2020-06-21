import {
    getQuesByIdAPI,
    getQuesForClientByIdAPI,
    getChatByIdAPI,
    getPrivateAdAPI,
    getAdSentByIdAPI,
    getGroupMsgAPI,
    sendMessageAPI,
    changeMessageStatusAPI
} from '../../api/chat';

import {
    getClientListAPI
} from '../../api/admin';

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
    currentGroupTos: []
}

const chat = {
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
        }
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
                type: 2,
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
                type: 2,
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
        sendGroupMsg: async ({state, rootState}, param) => {
            const res = await sendMessageAPI({
                title: param.title,
                content: param.content,
                from: rootState.user.userId,
                tos: state.currentGroupTos,
                type: state.currentGroupType,
                retMsgId: -1
            });
        }
    }
}

export default chat
