<template>
    <div class="manageUser-wrapper">
        <a-tabs>
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
          <a-tab-pane tab="酒店" key="4">
            <div style="width: 100%; text-align: right; margin:20px 0">
              <a-button type="primary" @click="addH"><a-icon type="plus" />添加酒店</a-button>
            </div>
            <a-table
              :columns="colH"
              :dataSource="hotelList"
              bordered
              >
              <span slot="action" slot-scope="text, record">
                  <a-button @click="addM(record.id)">添加工作人员</a-button>
                  <a-popconfirm
                          title="删除酒店将同时删除工作人员。确定想删除吗？"
                          @confirm="deleteHM(record.id)"
                          okText="确定"
                          cancelText="取消"
                  >
                    <a-button type="danger">删除酒店及工作人员</a-button>
                  </a-popconfirm>
                </span>
            </a-table>
          </a-tab-pane>
          <a-tab-pane tab="酒店工作人员" key="5">
            <a-table
              :columns="colM"
              :dataSource="managerList"
              bordered
              ></a-table>
          </a-tab-pane>
        </a-tabs>
        <div>
          <AddMModal></AddMModal>
          <AddOperatorModal></AddOperatorModal>
          <ModifyOOModal></ModifyOOModal>
          <ModifyClientModal></ModifyClientModal>
          <AddHotelModal></AddHotelModal>
        </div>


    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddOperatorModal from './components/addOperatorModal'
import ModifyOOModal from "./components/ModifyOOModal"
import ModifyClientModal from "./components/ModifyClientModal"
import AddHotelModal from "./components/addHotelModal"
import AddMModal from "./components/addMModal"
import{
    getManagerIdByHotelIdAPI
}from '@/api/hotel'
import{
    deleteHMAPI
}from '@/api/admin'
import { message } from 'ant-design-vue'
const colH=[
    {
        title:'编号',
        dataIndex:'id',
    },{
        title:'名称',
        dataIndex:'name',
    },{
        title:'商圈',
        dataIndex:'bizRegion',
    },{
        title:'地址',
        dataIndex:'address',
    },{
      title:"工作人员编号",
        dataIndex:'managerId'
    },
    {
        title:'操作',
        key:'action',
        scopedSlots:{customRender:'action'}
    }
]
const colM=[
    // {
    //     title:'编号',
    //     dataIndex:'managerId',
    // },
    {
        title:'账号',
        dataIndex:'email',
    },{
        title:'用户名',
        dataIndex:'userName',
    },{
        title:'密码',
        dataIndex:'password'
    }
]
const colHotel=[
    {
        title:'id',
        dataIndex:'hotelId',
    },
    {
        title:'名称',
        dataIndex:'name',
    },
    {
        title:'地址',
        dataIndex:'address',
    },
    {
        title:'商圈',
        dataIndex:'bizRegion'
    },
    {
        title:'星级',
        dataIndex:'hotelStar'
    },
    {
        title:'电话号码',
        dataIndex:'phoneNum'
    },
    {
        title:'评分',
        dataIndex:'rate'
    },
    // {
    //     title:'描述',
    //     dataIndex:'hotelDescription'
    // },
    {
        title:'编号',
        dataIndex:'managerId',
    },
    {
        title:'工作人员账号',
        dataIndex:'email',
    },
    {
        title:'用户名',
        dataIndex:'userName',
    },
    {
        title:'密码',
        dataIndex:'password'
    },
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
            colHotel, //useless
            colH, //hotel
            colM, //manager
            columns,   //client
            colOperator,
            data: [],
            form: this.$form.createForm(this, { name: 'manageUser' }),
        }
    },
    components: {
        AddHotelModal,
        ModifyClientModal,
        ModifyOOModal,
        AddOperatorModal,
        AddMModal,
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
            'HMList',
            'addHMModalVisible',
            'modifyHMModalVisible',
            'addHotelModalVisible',
            'tmpHotelId',
            'addMModalVisible'

        ]),
    },
    watch:{},
    mounted() {
        this.getClientList(),
        this.getOperatorList(),
        this.getManagerList(),
        this.getHotelList()
    },
    methods: {
        ...mapActions([
            'getOperatorList',
            'searchOO',
            'getManagerList',
            'getClientList',
            'getHotelList',
            'deleteUser',
            'getTmpUserInfo',
            'getTmpClientInfo',
            'searchClient',
        ]),
        ...mapMutations([
            'set_addOperatorModalVisible',
            'set_modifyOOModalVisible',
            'set_OOIdx',
            'set_displayOperatorList',
            'set_displayClientList',
            'set_tmpClientId',
            'set_tmpClientInfo',
            'set_modifyClientModalVisible',
            'set_displayHMList',
            'set_addHotelModalVisible',
            'set_tmpHotelId',
            'set_addMModalVisible',
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
        },
        myAdd(){
            this.set_addHMModalVisible(true)
        },
        addH(){
          this.set_addHotelModalVisible(true)
        },
        async addM(hotelId){
            const res=await getManagerIdByHotelIdAPI(hotelId)
            if(res){
                message.error("该酒店已经有工作人员账号")
            }else{
                this.set_tmpHotelId(hotelId)
                console.log(this.tmpHotelId)
                this.set_addMModalVisible(true)
                console.log(this.addMModalVisible)
            }
        },
        async deleteHM(hotelId){
            const res=await deleteHMAPI(hotelId)
            this.getManagerList(),
            this.getHotelList()
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


<!--入驻酒店-->
<!--增-->
<!--（操作：增加工作人员、删酒店的同时删除工作账号）-->
<!--工作人员-->
