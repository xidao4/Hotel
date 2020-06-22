<template>
    <div>
        <a-table
                :data-source="broadcastList"
                :columns="columns"
                bordered
                rowKey="id"
                style="margin-top: 30px">
            <a-tag
                    slot="status"
                    slot-scope="text"
                    :color="text === '0'? 'volcano' : 'geekblue'"
            >
                {{(text === "0") ? "无效" : "有效"}}
            </a-tag>
            <span slot="action" slot-scope="record">
                <a-button
                        type="danger"
                        size="small">{{(record.status===0)?'设为有效':'设为无效'}}</a-button>
            </span>
        </a-table>
    </div>
</template>

<script>
    import { mapActions, mapGetters, mapMutations } from 'vuex';
    export default {
        name: "AdRecords",
        data() {
            return {

            }
        },
        computed: {
            ...mapGetters([
                'broadcastList'
            ]),
            columns() {
                return [
                    {
                        title: '编号',
                        dataIndex: 'id',
                        key: 'id',
                        align: 'center',
                    },
                    {
                        title: '发送时间',
                        dataIndex: 'sendTime',
                        key: 'sendTime',
                        align: 'center',
                    },
                    {
                        title: '主题',
                        dataIndex: 'title',
                        key: 'title',
                    },
                    {
                        title: '状态',
                        dataIndex: 'status',
                        key: 'status',
                        align: 'center',
                        scopedSlots: {customRender: 'status'}
                    },
                    {
                        title: '操作',
                        key: 'action',
                        scopedSlots: { customRender: 'action' },
                    },
                ]
            }
        },
        methods: {
            ...mapActions([
                'getBroadcastList'
            ]),

        },
        mounted() {
            this.getBroadcastList()
        }
    }
</script>

<style scoped>

</style>

