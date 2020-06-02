import { getCreditRecordsAPI } from '../../api/credit'

const state = {
    manageCreditVisible: false,
    currentOrderId: '',   // 当前展示的orderId
    currentUserId: '',
    currentUserInfo: '',
    creditRecordsList: [
        // private Integer rid;
        // private Integer userId;
        // private String username;
        // private Double credit;
        // private String createTime;
        // private String updateTime;
        // private String status;
        // private String desc;
    ]
};

const operator = {
    state,
    mutations: {
        set_manageCreditVisible(state, data) {
            state.manageCreditVisible = data
        },
        set_creditRecordsList(state, data) {
            state.creditRecordsList = [...data]
        }
    },
    actions: {
        getUserCreditById: async ({state}) => {
            const res = await (state.currentUserId)

        },
        getCreditRecords: async ({rootState, commit}) => {
            const res = await getCreditRecordsAPI({
                userId: rootState.userId
            });
            if(res) {
                commit('set_creditRecordsList', res)
            }
        }
    }
};

export default operator
