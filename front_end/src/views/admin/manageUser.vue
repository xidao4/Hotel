<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="入驻酒店及其工作人员" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                  <a-button type="primary" @click="addHotel"><a-icon type="plus" />添加酒店及其工作人员</a-button>
                </div>
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addManager"><a-icon type="plus" />添加酒店工作人员</a-button>
                </div>
                <a-table
                    :columns="colHotel"
                    :dataSource="hotelList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="text, record">
                        <a-button>更改酒店及其工作人员信息</a-button>
                        <a-button type="danger" @click="order(record)">删除酒店及其工作人员</a-button>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="用户" key="2">
                <a-table
                  :columns="columns"
                  :dataSource="clientList"
                  bordered
                 >
                  <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                  <span slot="action" slot-scope="text, record">
                        <a-button>更改信息</a-button>
                        <a-button type="danger" @click="order(record.id)">删除用户</a-button>
                  </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="网站运营人员" key="3">
              <div style="width: 100%; text-align: right; margin:20px 0">
                <a-button type="primary" @click="addOperator"><a-icon type="plus" />添加网站运营人员</a-button>
              </div>
              <a-table
                :columns="colOperator"
                :dataSource="operatorList"
                bordered
                >
                <span slot="action" >
                  <a-button>更改信息</a-button>
                </span>
              </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddManagerModal></AddManagerModal>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddManagerModal from './components/addManagerModal'
const colHotel=[
    {
        title:'酒店编号',
        dataIndex:'id',
    },{
        title:'酒店名称',
        dataIndex:'name',
    },
    {
        title:'酒店工作人员账号编号',
        dataIndex:'managerId',
    },
    {
        title:'酒店工作人员账号',
        dataIndex:'email',
    },{
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    }
]
const columns = [
    {
        title:'用户编号',
        dataIndex:'id',
    }, {
        title: '用户邮箱',
        dataIndex: 'email',
    }, {
        title: '用户名',
        dataIndex: 'userName',
    }, {
        title: '用户密码',
        dataIndex: 'password',
    }, {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    }, {
        title: '信用值',
        dataIndex: 'credit',
    }, {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
const colOperator=[
    {
        title:'用户编号',
        dataIndex:'id',
    },{
        title:'邮箱',
        dataIndex:'email'
    },{
        title:'用户名',
        dataIndex:'userName',
    },{
        title:'密码',
        dataIndex:'password'
    },{
        title:'操作',
        key:'action',
        scopedSlots:{customRender:'action'}
    }
]
export default {
    name: 'manageHotel',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns,
            colHotel,
            colOperator,
            data: [],
            form: this.$form.createForm(this, { name: 'manageUser' }),
        }
    },
    components: {
        AddManagerModal
    },
    computed: {
        ...mapGetters([
            'addManagerModalVisible',
            'managerList',
            'clientList',
            'hotelList',
            'operatorList'
        ]),
    },
    mounted() {
      this.getManagerList(),
      this.getClientList(),
      this.getHotelList(),
      this.getOperatorList()
    },
    methods: {
        ...mapActions([
            'getOperatorList',
            'getManagerList',
            'getClientList',
            'getHotelList',
            'deleteUser'
        ]),
        ...mapMutations([
            'set_addManagerModalVisible'
        ]),
        addManager(){
            this.set_addManagerModalVisible(true)
        },
        order(userId){
            this.deleteUser(userId);
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
