import { message } from 'ant-design-vue'
import {
    getAllTagsAPI,
    addTagAPI,
    deleteTagAPI,
} from '@/api/tag'


const state = {
    tags:[]
};

const tag = {
    state,
    mutations: {
        set_tags(state, data) {
            state.tags = data
        }
    },
    actions: {
        getAllTags:async({commit},param)=>{
            const res=await getAllTagsAPI(param)
            if(res){
                commit('set_tags',res)
            }else{
                message.error('获取该酒店服务标签失败')
            }
        },
        addTag:async({dispatch},param)=>{
            const res=await addTagAPI(param)
            if(res){
                dispatch('getAllTags',param.hotelId)
            }else{
                message.error('增加该酒店服务标签失败')
            }
        },
        deleteTag:async({dispatch},removedTag)=>{
            const res=await deleteTagAPI(removedTag.id)
            if(res){
                console.log('removedTag.hotelId',removedTag.hotelId)
                await dispatch('getAllTags',removedTag.hotelId)
            }else{
                message.error('删除该酒店服务标签失败')
            }
        }

    }
};

export default tag
