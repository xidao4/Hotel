<template>
    <div class="msg-container">
        <a-card :bordered="false">
            <a-list>
                <a-list-item :key="index" v-for="(item, index) in adSentList">
                    <a-list-item-meta
                            :description="item.title"
                    >
                        <a-tag slot="title" color="#108ee9">{{ item.toName }}</a-tag>
                        <a-avatar
                                slot="avatar"
                                src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                        />
                    </a-list-item-meta>
                    <a-tag color="gray" v-if="item.status=='0'">未读</a-tag>
                    <a-tag color="green" v-else-if="item.status=='1'">已读</a-tag>
                    <a-tag color="#929BB3">{{item.sendTime}}</a-tag>
                    <a-button type="link" @click="toDetail(item.content)">详情</a-button>
                </a-list-item>
            </a-list>
        </a-card>


        <a-modal :visible="detailVisible" :footer="null" @cancel="handleCancel">
            <div>{{detailContent}}</div>
        </a-modal>
    </div>

</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex';
    import { message } from 'ant-design-vue'
    export default {
        name: "Chat",
        data() {
            return {
                detailVisible: false,
                detailContent: ''
            }
        },
        components: {

        },
        computed: {
            ...mapGetters([
                'adSentList'
            ])
        },
        methods: {
            ...mapMutations([

            ]),
            ...mapActions([
                'get_adSentList',
            ]),
            toDetail(content) {
                this.detailVisible = true;
                this.detailContent = content
            },
            handleCancel() {
                this.detailVisible = false;
            }
        },
        mounted() {
            this.get_adSentList();
        }
    }
</script>

<style scoped>
    .msg-container{
        margin-top: 30px;
    }
</style>
