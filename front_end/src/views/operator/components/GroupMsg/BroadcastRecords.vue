<template>
    <div>
        <a-table
                :data-source="broadcastList"
                :columns="columns"
                bordered
                rowKey="id"
                style="margin-top: 30px">
            <div
                    slot="filterDropdown"
                    slot-scope="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
                    style="padding: 8px"
            >
                <a-input
                        v-ant-ref="c => (searchInput = c)"
                        :placeholder="`筛选 ${column.status}`"
                        :value="selectedKeys[0]"
                        style="width: 188px; margin-bottom: 8px; display: block;"
                        @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                        @pressEnter="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                />
                <a-button
                        type="primary"
                        icon="search"
                        size="small"
                        style="width: 90px; margin-right: 8px"
                        @click="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                >
                    查找
                </a-button>
                <a-button size="small" style="width: 90px" @click="() => handleReset(clearFilters)">
                    重置
                </a-button>
            </div>
            <a-icon
                    slot="filterIcon"
                    slot-scope="filtered"
                    type="search"
                    :style="{ color: filtered ? '#108ee9' : undefined }"
            />
            <a-tag
                    slot="status"
                    slot-scope="text"
                    :color="text === '0'? 'green' : '#4AD958'"
            >
                {{(text == "0") ? "无效" : "有效"}}
            </a-tag>
            <a-tag
                    slot="priority"
                    slot-scope="text"
                    :color="text === 0? 'cyan' : '#2db7f5'"
            >
                {{(text == '0') ? '低' : '高'}}
            </a-tag>
            <span slot="action" slot-scope="record">
                <a-popconfirm title="确定置为无效？" @confirm="changeStatus(record.id, record.status)" v-if="record.status=='1'">
                    <a-icon slot="icon" type="question-circle-o" style="color: red" />
                    <a-button
                            type="danger"
                            size="small">设为无效</a-button>
                </a-popconfirm>
                <a-popconfirm title="确定恢复有效？" @confirm="changeStatus(record.id, record.status)" v-if="record.status=='0'">
                    <a-icon slot="icon" type="question-circle-o" style="color: dodgerblue" />
                    <a-button
                            type="default"

                            size="small">设为有效</a-button>
                </a-popconfirm>
            </span>
        </a-table>
    </div>
</template>

<script>
    import { mapActions, mapGetters, mapMutations } from 'vuex';
    import { message } from 'ant-design-vue';
    export default {
        name: "GroupRecords",
        data() {
            return {
                searchText: '',
                searchInput: null,
                searchedColumn: '',
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
                        title: '创建人',
                        dataIndex: 'createdName',
                        key: 'createdName',
                        align: 'center',
                    },
                    {
                        title: '创建时间',
                        dataIndex: 'createdTime',
                        key: 'createdTime',
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
                        scopedSlots: {customRender: 'status'},
                        filters: [
                            {text: '有效', value: '1'},
                            {text: '无效', value: '0'},
                        ],
                        onFilter: (value, record) => record.status === value,
                    },
                    {
                        title: '优先级',
                        dataIndex: 'priority',
                        key: 'priority',
                        align: 'center',
                        scopedSlots: {customRender: 'priority'},
                        filters: [
                            {text: '高', value: 1},
                            {text: '低', value: 0},
                        ],
                        onFilter: (value, record) => record.priority === value,
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
                'getBroadcastList',
                'changeBroadcastStatus'
            ]),
            handleSearch(selectedKeys, confirm, dataIndex) {
                confirm();
                this.searchText = selectedKeys[0];
                this.searchedColumn = dataIndex;
            },
            handleReset(clearFilters) {
                clearFilters();
                this.searchText = '';
            },
            async changeStatus(id, status) {
                let s = (status==='0')?'1':'0';
                let tip = (status==='0')?"已在首页增加该公告":"已从首页删除该公告";
                await this.changeBroadcastStatus({
                    id: id,
                    status: s
                }).then(() => {
                    message.success(tip)
                })

                this.getBroadcastList()
            }

        },
        mounted() {
            this.getBroadcastList()
        },
        watch:{
            broadcastList(val){

            }
        }
    }
</script>

<style scoped>

</style>
