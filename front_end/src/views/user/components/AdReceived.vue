<template>
    <a-modal :visible="AdListVisible" :footer="null" @cancel="handleCancel">
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
    </a-modal>
</template>

<script>
    import {mapGetters} from 'vuex';

    export default {
        name: "AdReceived",
        ...mapGetters([
            'AdListVisible'
        ])
    }
</script>

<style scoped>

</style>
