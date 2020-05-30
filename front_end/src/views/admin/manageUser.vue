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
              <div >
                <a-button @click="btnClick">test</a-button>
              </div>
              <div style="width: 100%; text-align: right; margin:20px 0">
                <a-button type="primary" @click="addOperator"><a-icon type="plus" />添加网站运营人员</a-button>
              </div>
              <a-table
                :columns="colOperator"
                :dataSource="operatorList"
                bordered
                >
                <span slot="action" slot-scope="text, record">
                  <a-button @click="modifyOO(record.id)">更改信息</a-button>
                </span>
              </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddOperatorModal></AddOperatorModal>
        <ModifyOOModal></ModifyOOModal>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddOperatorModal from './components/addOperatorModal'
import ModifyOOModal from "./components/ModifyOOModal";
const colHotel=[
    {
        title:'酒店编号',
        dataIndex:'id',
    },
    {
        title:'名称',
        dataIndex:'name',
    },
    // {
    //     title:'地址',
    //     dataIndex:'address',
    // },
    // {
    //     title:'商圈',
    //     dataIndex:'bizRegion'
    // },
    // {
    //     title:'星级',
    //     dataIndex:'hotelStar'
    // },
    // {
    //     title:'电话号码',
    //     dataIndex:'phoneNum'
    // },
    // {
    //     title:'评分',
    //     dataIndex:'rate'
    // },
    // {
    //     title:'描述',
    //     dataIndex:'hotelDescription'
    // },
    {
        title:'酒店工作人员账号编号',
        dataIndex:'managerId',
    },
    {
        title:'工作人员账号',
        dataIndex:'email',
    },
    // {
    //     title:'工作人员用户名',
    //     dataIndex:'userName',
    // },
    // {
    //     title:'工作人员密码',
    //     dataIndex:'password'
    // },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' }
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
        ModifyOOModal,
        AddOperatorModal
    },
    computed: {
        ...mapGetters([
            'addOperatorModalVisible',
            'modifyOOModalVisible',
            'managerList',
            'clientList',
            'hotelList',
            'operatorList',
            'OOIdx'
        ]),
    },
    watch:{

    },
    mounted() {
        console.log("before mounting manageUser.vue"),
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
            'deleteUser',
            'getTmpUserInfo',

        ]),
        ...mapMutations([
            'set_addOperatorModalVisible',
            'set_modifyOOModalVisible',
            'set_OOIdx',
            'set_modifyOOModalVisible',
        ]),
        addOperator(){
            this.set_addOperatorModalVisible(true)
        },
        order(userId){
            this.deleteUser(userId);
        },
        modifyOO(userId){

            this.set_OOIdx(userId)
            console.log("before getTmpUserInfo","test",this.OOIdx),
            this.getTmpUserInfo(),
            console.log("after getTmpUserInfo")
            //this.set_modifyOOModalVisible(true)
        },
        btnClick(){
            console.log("0530 click the test button",this.operatorList)
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
