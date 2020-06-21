<template>
    <div class="msg-container">
        <a-card title="我的留言记录" :bordered="false">
            <a-list>
                <a-list-item :key="index" v-for="(item, index) in clientQuesListForC">
                    <a-list-item-meta>
                        <a-avatar slot="avatar" src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"/>
                        <div slot="title">
                            <span>{{ item.title }}</span>&nbsp;
                        </div>
                        <div slot="description">{{ item.sendTime }}</div>
                    </a-list-item-meta>
                    <a-tag color="purple" v-if="item.status=='0'">等待处理</a-tag>
                    <a-tag color="orange" v-else-if="item.status=='1'">处理中</a-tag>
                    <a-tag color="gray" v-if="item.status=='2'">已结束</a-tag>
                    <a-button type="link" @click="toChat(item.id, item.status)">详情</a-button>
                    <a-divider type="vertical" />
                    <a-popconfirm
                            title="您确定结束与客服的聊天吗？"
                            ok-text="确认"
                            cancel-text="取消"
                            @confirm="confirmDone(item.id)"
                            @cancel="cancel"
                    >
                        <a-button type="link" :disabled="item.status=='2'">结束</a-button>
                    </a-popconfirm>
                </a-list-item>
            </a-list>
        </a-card>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex';
    import { message } from 'ant-design-vue'
    export default {
        name: "Chat",
        data() {
            return {

            }
        },
        components: {

        },
        computed: {
            ...mapGetters([
                'clientQuesListForC',
                'userInfo'
            ])
        },
        methods: {
            ...mapMutations([
                'set_currentQuesId',
                'set_currentQuesStatus'
            ]),
            ...mapActions([
                'getClientQuesListForC',
                'changeMessageStatus'
            ]),
            confirmDone(id) {
                this.changeMessageStatus({
                    id: id,
                    status: '2'
                }).then(() => {
                    this.getClientQuesListForC();
                    message.success("感谢您的支持！")
                })
            },
            toChat(id, status) {
                this.set_currentQuesId(id);
                this.set_currentQuesStatus(status)
                this.$router.push({ name: 'chatMessage', params: { userType: this.userInfo.userType } });
            }
        },
        mounted() {
            this.getClientQuesListForC();
        }
    }
</script>

<style scoped>
    .msg-container{
        margin-top: 30px;
    }
</style>
