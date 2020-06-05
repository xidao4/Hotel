<template>
    <div class="manageUser-wrapper">
        <div id="pic2" style="width: 100%;height:400px;"></div>
        <a-tabs>
            <a-tab-pane tab="账户管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addManager">
                        <a-icon type="plus"/>
                        添加用户
                    </a-button>
                </div>
                <a-table
                        :columns="columns"
                        :dataSource="managerList"
                        bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="text, record">
                        <a-button type="danger" @click="order(record)">删除用户</a-button>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddManagerModal></AddManagerModal>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import AddManagerModal from './components/addManagerModal'
    import echarts from 'echarts'

    const columns = [
        {
            title: '用户邮箱',
            dataIndex: 'email',
        },
        {
            title: '用户名',
            dataIndex: 'userName',
        },
        {
            title: '用户密码',
            dataIndex: 'password',
        },
        {
            title: '用户手机号',
            dataIndex: 'phoneNumber',
        },
        {
            title: '信用值',
            dataIndex: 'credit',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    export default {
        name: 'manageHotel',
        data() {
            return {
                formLayout: 'horizontal',
                pagination: {},
                columns,
                data: [],
                form: this.$form.createForm(this, {name: 'manageUser'}),
            }
        },
        components: {
            AddManagerModal
        },
        computed: {
            ...mapGetters([
                'addManagerModalVisible',
                'managerList',
                'dateList',
                'numList'
            ])
        },
        async mounted() {
            await this.getCurveDataset()
            //console.log("loading...")
            //console.log(this.dateList)
            //console.log(this.numList)
            this.getManagerList()
            this.$nextTick(function () {
                let dom = document.getElementById('pic2')
                let myChart = echarts.init(dom)
                myChart.setOption({
                    title: {
                        text: '推广度曲线'
                    },
                    toolbox: {
                        show: true
                    },
                    legend: {
                        data: ['累计注册用户数']
                    },
                    xAxis: {
                        data: this.dateList
                    },
                    yAxis: {},
                    series: [{
                        name: '注册用户数',
                        type: 'line',
                        data: this.numList
                    }]
                })
            })
        },
        methods: {
            ...mapActions([
                'getManagerList',
                'getCurveDataset'
            ]),
            ...mapMutations([
                'set_addManagerModalVisible'
            ]),
            addManager() {
                this.set_addManagerModalVisible(true)
            }
        }
    }
</script>
<style scoped lang="less">
    .manageUser-wrapper {
        padding: 50px;

        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>