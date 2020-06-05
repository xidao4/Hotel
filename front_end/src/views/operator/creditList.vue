<template>
    <a-table
            :data-source="showRecordsList"
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
                    :placeholder="`查找 ${column.title}`"
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
        <a-tag
                slot="status"
                slot-scope="text"
                :color="text === '无效'? 'volcano' : 'geekblue'"
        >
            {{(text === "0") ? "无效" : "有效"}}
        </a-tag>
        <a-icon
                slot="filterIcon"
                slot-scope="filtered"
                type="search"
                :style="{ color: filtered ? '#108ee9' : undefined }"
        />
    </a-table>
</template>

<script>
    import { mapActions, mapGetters, mapMutations } from 'vuex';
    import moment from 'moment'

    export default {
        name: "creditList",
        data() {
            return {
                showRecordsList: [],
                searchText: '',
                searchInput: null,
                searchedColumn: '',
            }
        },
        methods: {
            ...mapActions([
                'getCreditRecords'
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

        },
        computed: {
            ...mapGetters([
                'creditRecordsList'
            ]),
            columns() {
                return [
                    {
                        title: '条目序号',
                        dataIndex: 'rid',
                        key: 'id',
                        align: 'center',
                        width: '100',
                    },
                    {
                        title: '用户名',
                        dataIndex: 'username',
                        key: 'username',
                        align: 'center',
                        width: '100',
                        scopedSlots: {
                            filterDropdown: 'filterDropdown',
                            filterIcon: 'filterIcon',
                        },
                        onFilter: (value, record) => record.username.indexOf(value) !== -1,
                        onFilterDropdownVisibleChange: visible => {
                            if (visible) {
                                setTimeout(() => {
                                    this.searchInput.focus();
                                }, 0);
                            }
                        },
                    },
                    {
                        title: '变更后信用值',
                        dataIndex: 'credit',
                        align: 'center',
                        width: '100',
                    },
                    {
                        title: '记录创建时间',
                        dataIndex: 'createTime',
                        align: 'center',
                        width: '200',
                        scopedSlots: {customRender: 'createTime'},
                        sorter: (a, b) => moment(a.createTime).isBefore(moment(b.createTime)),
                        sortDirections: ['descend', 'ascend'],
                    },
                    {
                        title: '最后一次更新时间',
                        dataIndex: 'updateTime',
                        align: 'center',
                        width: '200',
                        scopedSlots: {customRender: 'updateTime'},
                        sorter: (a, b) => moment(a.updateTime, 'second').isBefore(moment(b.updateTime, 'second')),
                        sortDirections: ['descend', 'ascend'],
                    },
                    {
                        title: '记录状态',
                        align: 'center',
                        width: '100',
                        filters: [
                            {text: '有效', value: '1'},
                            {text: '无效', value: '0'},
                        ],
                        onFilter: (value, record) => record.status.includes(value),
                        dataIndex: 'status',
                        scopedSlots: {customRender: 'status'}
                    },
                    {
                        title: '说明',
                        dataIndex: 'desc',
                        align: 'center',
                        width: '300',
                        key: 'desc',
                        scopedSlots: {customRender: 'desc'},
                    },
                ];
            }
        },
        async mounted() {
            await this.getCreditRecords();
            this.showRecordsList = this.creditRecordsList;
        }
    }
</script>

<style scoped>

</style>
