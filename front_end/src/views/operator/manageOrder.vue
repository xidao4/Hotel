<template>
    <div>
        <a-table
                :data-source="showOrderList"
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
            <p slot="expandedRowRender" slot-scope="record" style="margin: 0">
                撤销理由：{{ record.cancelReason }}
            </p>
            <a-tag
                    slot="orderState"
                    slot-scope="text"
                    :color="text === '客户撤销' || text === '异常' ? 'volcano' : 'geekblue'"
            >
                {{text}}
            </a-tag>
            <a-icon
                    slot="filterIcon"
                    slot-scope="filtered"
                    type="search"
                    :style="{ color: filtered ? '#108ee9' : undefined }"
            />
            <span slot="action" slot-scope="record">
                <a-tag v-if="record.crid != null"
                       color="cyan">
                    信用值处理条目号：{{record.crid}}
                </a-tag>
                <a-button
                        type="primary"
                        size="small"
                        v-else-if="(record.orderState === '客户撤销' || record.orderState === '异常')"
                        @click="manageCredit(record)">信用变更</a-button>
            </span>
        </a-table>

        <ManageCredit></ManageCredit>
    </div>
</template>

<script>
    import { mapActions, mapGetters, mapMutations } from 'vuex';
    import ManageCredit from './components/manageCredit';
    import moment from 'moment'

    export default {
        name: "manageOrder",
        components: { ManageCredit },
        data() {
            return {
                searchText: '',
                searchInput: null,
                searchedColumn: '',
            }
        },
        computed: {
            ...mapGetters([
                'orderList',
                'showOrderList'
            ]),
            columns() {
                return [
                    {
                        title: '订单号',
                        dataIndex: 'id',
                        key: 'id'
                    },
                    {
                        title: '酒店名',
                        dataIndex: 'hotelName',
                        key: 'hotelName',
                        scopedSlots: {
                            filterDropdown: 'filterDropdown',
                            filterIcon: 'filterIcon',
                        },
                        onFilter: (value, record) => record.hotelName.indexOf(value) !== -1,
                        onFilterDropdownVisibleChange: visible => {
                            if (visible) {
                                setTimeout(() => {
                                    this.searchInput.focus();
                                }, 0);
                            }
                        },
                    },
                    {
                        title: '入住时间',
                        dataIndex: 'checkInDate',
                        scopedSlots: { customRender: 'checkInDate' },
                        sorter: (a, b) => moment(a.checkInDate).isBefore(moment(b.checkInDate)),
                        sortDirections: ['descend', 'ascend'],
                    },
                    {
                        title: '离店时间',
                        dataIndex: 'checkOutDate',
                        scopedSlots: { customRender: 'checkOutDate' },
                        sorter: (a, b) => moment(a.checkOutDate).isBefore(moment(b.checkOutDate)),
                        sortDirections: ['descend', 'ascend'],
                    },
                    {
                        title: '订单总价',
                        dataIndex: 'price',
                    },
                    {
                        title: '状态',
                        filters: [
                            { text: '已预订', value: '已预订' },
                            { text: '客户撤销', value: '客户撤销' },
                            { text: '已入住', value: '已入住' },
                            { text: '已执行', value: '已执行' },
                            { text: '异常', value: '异常' },
                        ],
                        onFilter: (value, record) => record.orderState.includes(value),
                        dataIndex: 'orderState',
                        scopedSlots: { customRender: 'orderState' }
                    },
                    {
                        title: '操作',
                        key: 'action',
                        scopedSlots: { customRender: 'action' },
                    },
                ];
            }
        },
        async mounted() {
            await this.getAllOrders();
            this.set_showOrderList(this.orderList)
        },
        watch:{
            async showOrderList(val){
                await this.getAllOrders();
                this.set_showOrderList(this.orderList)
            }
        },
        methods: {
            ...mapActions([
                'getAllOrders'
            ]),
            ...mapMutations([
                'set_manageCreditVisible',
                'set_currentUpdateInfo',
                'set_showOrderList'
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
            manageCredit(record) {
                this.set_manageCreditVisible(true);
                const userInfo = {
                    orderId: record.id,
                    userId: record.userId,
                };
                this.set_currentUpdateInfo(userInfo)
            },
            showOrderDetail() {

            }
        }
    }
</script>

<style scoped>
    .filter-box {
        margin: 20px auto;
    }
</style>
