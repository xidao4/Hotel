import {
    getManagerListAPI,
    addManagerAPI,
} from '@/api/admin'
import { message } from 'ant-design-vue'
import {getCurveDatasetAPI} from "../../api/admin";

const admin = {
    state: {
        managerList: [

        ],
        dateList: [],
        numList: [],
        addManagerModalVisible: false,
        addManagerParams: {
            email:'',
            password:''
        }
    },
    mutations: {
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
        },
        set_dateList: function (state, data) {
            state.dateList = data
        },
        set_numList: function (state, data) {
            state.numList = data
        }
    },
    actions: {
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
        }
    }
}
export default admin