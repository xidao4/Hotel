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

import {message} from "ant-design-vue";

const state = {
    //组件可见性
    MsgAffixVisible: false,
    LeaveMsgModalVisible: false,
    ChatVisible: false,
    ChatBoxVisible: false,
    //client
    clientQuesList: [],
    clientQuesListForC: [],
    //op
    currentQuesId: null,
    quesRecords: [],          //对于某问题的聊天记录
    currentQuesStatus: '',
}

const chat = {
    state,
    mutations: {
        set_MsgAffixVisible(state, data) {
            state.MsgAffixVisible = data
        },
        set_LeaveMsgModalVisible(state, data) {
            state.LeaveMsgModalVisible = data
        },
        set_ChatVisible(state, data) {
            state.ChatVisible = data
        },
        set_ChatBoxVisible(state, data) {
            state.ChatBoxVisible = data
        },
        set_clientQuesList(state, data) {
            state.clientQuesList = [...data]
        },
        set_msgGroupList(state, data) {
            state.msgGroupList = [...data]
        },
        set_sendMsgList(state, data) {
            state.sendMsgList = [...data]
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
    }
}

export default chat
