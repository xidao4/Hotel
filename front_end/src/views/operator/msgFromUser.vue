<template>
    <a-card class="msg-container" title="消息列表" :bordered="false">
        <a-row :gutter="24" type="flex" justify="center">
            <a-col :xl="18" :lg="24" :md="24" :sm="24" :xs="24">
                <a-list item-layout="horizontal"
                        :data-source="clientQuesList">
                    <a-list-item slot="renderItem" slot-scope="item">
                        <a-list-item-meta
                                :description="item.title"
                        >
                            <a-tag slot="title" color="#108ee9">{{ item.fromName }}</a-tag>
                            <a-avatar
                                    slot="avatar"
                                    src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                            />
                        </a-list-item-meta>
                        <a-tag color="purple" v-if="item.status == '0'">待处理</a-tag>
                        <a-tag color="green" v-else-if="item.status=='1'">处理中</a-tag>
                        <a-tag color="gray" v-if="item.status=='2'">已结束</a-tag>
                        <a-tag color="#929BB3">{{item.sendTime}}</a-tag>
                        <!--                <a-button type="link">联系方式</a-button>-->

                        <a-button type="link" @click="toChat(item.id, item.status)" v-if="item.status=='2'">记录</a-button>
                        <a-button type="link" @click="toChat(item.id, item.status)" v-else>处理</a-button>
                    </a-list-item>
                </a-list>
            </a-col>
        </a-row>
    </a-card>

</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex';

    export default {
        name: "msgFromUser",
        data() {
            return {

            }
        },

        methods: {
            ...mapActions([
                'getClientQuesList',
                'changeMessageStatus'
            ]),
            ...mapMutations([
                'set_currentQuesId',
                'set_currentQuesStatus'
            ]),
            toChat(id, status) {
                if(status == '0'){
                    status = '1'
                    this.changeMessageStatus({
                        id: id,
                        status: '1'
                    })
                }
                this.set_currentQuesId(id);
                this.set_currentQuesStatus(status)
                this.$router.push({ name: 'chatMessage', params: { userType: this.userInfo.userType } });
            },
        },
        computed: {
            ...mapGetters([
                'clientQuesList',
                'userInfo'
            ]),
        },
        mounted() {
            this.getClientQuesList();
        }
    }
</script>

<style scoped>
    .msg-container{
        margin-top: 30px;
    }
</style>
