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
<!--              以下为有用代码-->
              <a-input-search placeholder="输入搜索关键词" enter-button @search="onSearchClient" />
              <a-button @click="showAllClient">显示全部</a-button>
              <div style="width: 100%; text-align: right; margin:20px 0">
                <a-button type="primary" @click="myAdd"><a-icon type="plus" />添加</a-button>
              </div>
                <a-table
                    :columns="colHotel"
                    :dataSource="displayHMList"
                    bordered
                >
                    <span slot="action" slot-scope="text, record">
                        <a-button>更改</a-button>
                        <a-button type="danger" @click="order(record)">删除</a-button>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="用户" key="2">
              <a-input-search placeholder="输入搜索关键词" enter-button @search="onSearchClient" />
              <a-button @click="showAllClient">显示全部</a-button>
                <a-table
                  :columns="columns"
                  :dataSource="displayClientList"
                  bordered
                 >
                  <span slot="action" slot-scope="text, record">
                        <a-button @click="modifyClient(record.id)">更改信息</a-button>
                        <a-button type="danger" @click="order(record.id)">删除用户</a-button>
                  </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="网站运营人员" key="3">
              <a-input-search placeholder="输入搜索关键词" enter-button @search="onSearchOO" />
              <a-button @click="showAll">显示全部</a-button>
              <div style="width: 100%; text-align: right; margin:20px 0">
                <a-button type="primary" @click="addOperator"><a-icon type="plus" />添加网站运营人员</a-button>
              </div>
              <a-table
                :columns="colOperator"
                :dataSource="displayOperatorList"
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
        <ModifyClientModal></ModifyClientModal>
        <ModifyHMModal></ModifyHMModal>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddOperatorModal from './components/addOperatorModal'
import ModifyOOModal from "./components/ModifyOOModal";
import ModifyClientModal from "./components/ModifyClientModal"
const colHotel=[
    {
        title:'酒店编号',
        dataIndex:'hotelId',
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
        title:'工作人员邮箱',
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
        ModifyClientModal,
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
            'displayOperatorList',
            'OOIdx',
            'isSearching',
            'displayClientList',
            'tmpClientId',
            'tmpClientInfo',
            'modifyClientModalVisible',
            'displayHMList',
            'HMList'
        ]),
    },
    watch:{},
    mounted() {
        console.log("before mounting manageUser.vue"),
        this.getHMList(),
        this.getClientList(),
        this.getOperatorList()
        //myInit()
    },
    methods: {
        ...mapActions([
            'getHotelAndManagerList',
            'getOperatorList',
            'searchOO',
            'getManagerList',
            'getClientList',
            'getHotelList',
            'deleteUser',
            'getTmpUserInfo',
            'getTmpClientInfo',
            'searchClient',
            'getHMList',
        ]),
        ...mapMutations([
            'set_addOperatorModalVisible',
            'set_modifyOOModalVisible',
            'set_OOIdx',
            'set_modifyOOModalVisible',
            'set_displayOperatorList',
            'set_hotelAndManagerList',
            'set_displayClientList',
            'set_tmpClientId',
            'set_tmpClientInfo',
            'set_modifyClientModalVisible',
            'set_HMList',
            'set_displayHMList'
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
        onSearchOO(value){
            this.searchOO(value);
        },
        showAll(){
            this.isSearching=false
            this.getOperatorList()
        },
        async modifyClient(userId){
            this.set_tmpClientId(userId)
            console.log('tmpClientId',this.tmpClientId)
            await this.getTmpClientInfo()
            console.log('after await getTmpClientInfo')
            this.set_modifyClientModalVisible(true)
            console.log(this.modifyClientModalVisible)
        },
        onSearchClient(value){
            this.searchClient(value)
        },
        showAllClient(){
            console.log('before showAllClient')
            this.getClientList()
            console.log('after showAllClient')
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
