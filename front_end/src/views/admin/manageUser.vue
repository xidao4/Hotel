<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="用户" key="2">
<!--              <a-input-search placeholder="输入搜索关键词" enter-button @search="onSearchClient" />-->
<!--              <a-button @click="showAllClient">显示全部</a-button>-->
                <a-table
                  :columns="columns"
                  :dataSource="displayClientList"
                  bordered
                 >
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
                  <a-icon
                          slot="filterIcon"
                          slot-scope="filtered"
                          type="search"
                          :style="{ color: filtered ? '#108ee9' : undefined }"
                  />
                  <span slot="action" slot-scope="text, record">
                        <a-button @click="modifyClient(record.id)">更改信息</a-button>
                        <a-popconfirm
                          title="确定删除吗？"
                          ok-text="确定"
                          cancel-text="取消"
                          @confirm="order(record.id)"
                          >
                          <a-button type="danger">删除用户</a-button>
                        </a-popconfirm>
<!--                        <a-button type="danger" @click="order(record.id)">删除用户</a-button>-->
                  </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="网站运营人员" key="3">
<!--              <a-input-search placeholder="输入搜索关键词" enter-button @search="onSearchOO" />-->
<!--              <a-button @click="showAll">显示全部</a-button>-->
              <div style="width: 100%; text-align: right; margin:20px 0">
                <a-button type="primary" @click="addOperator"><a-icon type="plus" />添加网站运营人员</a-button>
              </div>
              <a-table
                :columns="colOperator"
                :dataSource="displayOperatorList"
                bordered
                >
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
                <a-icon
                        slot="filterIcon"
                        slot-scope="filtered"
                        type="search"
                        :style="{ color: filtered ? '#108ee9' : undefined }"
                />
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
              :dataSource="displayHotelList"
              bordered
              >
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
              <a-icon
                      slot="filterIcon"
                      slot-scope="filtered"
                      type="search"
                      :style="{ color: filtered ? '#108ee9' : undefined }"
              />
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
              >
              <span slot="action" slot-scope="text, record">
                  <a-button @click="modifyHM(record.id)">修改信息</a-button>
                </span>
            </a-table>
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
        scopedSlots:{
            filterDropdown:'filterDropdown',
            filterIcon:'filterIcon',
        },
        onFilter:(value,record)=>record.name.indexOf(value)!==-1,
        onFilterDropdownVisibleChange:visible=>{
            if(visible){
                setTimeout(()=>{
                    this.searchInput.focus();
                },0);
            }
        }
    },{
        title:'商圈',
        dataIndex:'bizRegion',
    },{
        title:'地址',
        dataIndex:'address',
        scopedSlots:{
            filterDropdown:'filterDropdown',
            filterIcon:'filterIcon',
        },
        onFilter:(value,record)=>record.address!=='' && record.address.indexOf(value)!==-1,
        onFilterDropdownVisibleChange:visible=>{
            if(visible){
                setTimeout(()=>{
                    this.searchInput.focus();
                },0);
            }
        }
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
    {
        title:'编号',
        dataIndex:'id',
    },
    {
        title:'账号',
        dataIndex:'email',
    },{
        title:'用户名',
        dataIndex:'userName',
    },{
        title:'创建时间',
        dataIndex:'createDate',
    },{
        title:'操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    }
    // {
    //     title:'密码',
    //     dataIndex:'password'
    // }
]
const columns = [
    {
        title:'用户编号',
        dataIndex:'id',
    }, {
        title: '用户邮箱',
        dataIndex: 'email',
        scopedSlots:{
            filterDropdown:'filterDropdown',
            filterIcon:'filterIcon',
        },
        onFilter:(value,record)=>record.email.indexOf(value)!==-1,
        onFilterDropdownVisibleChange:visible=>{
             if(visible){
                 setTimeout(()=>{
                     this.searchInput.focus();
                 },0);
             }
        }
    }, {
        title: '用户名',
        dataIndex: 'userName',
        scopedSlots:{
            filterDropdown:'filterDropdown',
            filterIcon:'filterIcon',
        },
        onFilter:(value,record)=>record.userName.indexOf(value)!==-1,
        onFilterDropdownVisibleChange:visible=>{
            if(visible){
                setTimeout(()=>{
                    this.searchInput.focus();
                },0);
            }
        }
    },
    // {
    //     title: '用户密码',
    //     dataIndex: 'password',
    // },
    {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    },
    {
        title:'创建时间',
        dataIndex:'createDate',
    },
    {
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
        dataIndex:'email',
        scopedSlots:{
            filterDropdown:'filterDropdown',
            filterIcon:'filterIcon',
        },
        onFilter:(value,record)=>record.email.indexOf(value)!==-1,
        onFilterDropdownVisibleChange:visible=>{
            if(visible){
                setTimeout(()=>{
                    this.searchInput.focus();
                },0);
            }
        }
    },{
        title:'用户名',
        dataIndex:'userName',
        scopedSlots:{
            filterDropdown:'filterDropdown',
            filterIcon:'filterIcon',
        },
        onFilter:(value,record)=>record.userName.indexOf(value)!==-1,
        onFilterDropdownVisibleChange:visible=>{
            if(visible){
                setTimeout(()=>{
                    this.searchInput.focus();
                },0);
            }
        }
    },
    // {
    //     title:'密码',
    //     dataIndex:'password'
    // },
    {
        title:'创建时间',
        dataIndex:'createDate',
    },
    {
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
            colH, //hotel
            colM, //manager
            columns,   //client
            colOperator,
            data: [],
            form: this.$form.createForm(this, { name: 'manageUser' }),
            displayHotelList:[],
            searchText: '',
            searchInput: null,
            searchedColumn: '',
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
    async mounted() {
        await this.getClientList(),
        await this.getOperatorList(),
        await this.getManagerList(),
        await this.getHotelListLJY(),
        this.displayHotelList=[...this.hotelList]
    },
    methods: {
        ...mapActions([
            'getOperatorList',
            'searchOO',
            'getManagerList',
            'getClientList',
            'getHotelList',
            'getHotelListLJY',
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
        modifyHM(userId){
            this.set_OOIdx(userId)
            this.getTmpUserInfo()
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
            //console.log('tmpClientId',this.tmpClientId)
            await this.getTmpClientInfo()
            //console.log('after await getTmpClientInfo')
            this.set_modifyClientModalVisible(true)
            //console.log(this.modifyClientModalVisible)
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
        },
        handleSearch(selectedKeys, confirm, dataIndex) {
            confirm();
            this.searchText = selectedKeys[0];
            this.searchedColumn = dataIndex;
        },
        handleReset(clearFilters) {
            clearFilters();
            this.searchText = '';
        },
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
