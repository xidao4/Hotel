<template>
    <div class="manageHotel-wrapper">

        <a-tabs>
            <a-tab-pane tab="酒店管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addHotel"><a-icon type="plus" />添加酒店</a-button>
                </div>
                 <a-table
                    :columns="columns1"
                    :dataSource="hotelList"
                    bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="addRoom(record)">录入房间</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="info" size="small" @click="showCoupon(record)">优惠策略</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                            title="确定想删除该酒店吗？"
                            @confirm="deleteHotel(record)"
                            okText="确定"
                            cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除酒店</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="订单管理" key="2">
                <a-table
                    :columns="columns2"
                    :dataSource="orderList"
                    bordered
                >
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showContentModal(record.id)">订单详情</a-button>
                        <a-modal title="订单详情" :visible="contentVisible&&(currentIndex===record.id)"  @cancel="cancelContent" :footer="null">
                            <a-descriptions title="User Info" bordered :column="{ xxl: 4, xl: 3, lg: 3, md: 3, sm: 2, xs: 1 }" layout="vertical">
                                <a-descriptions-item label="订单号">{{idOrder.id}}</a-descriptions-item>
                                <a-descriptions-item label="酒店名称">{{idOrder.hotelName}}</a-descriptions-item>
                                <a-descriptions-item label="入住时间">{{idOrder.checkInDate}}</a-descriptions-item>
                                <a-descriptions-item label="退房时间">{{idOrder.checkOutDate}}</a-descriptions-item>
                                <a-descriptions-item label="房间类型">{{idOrder.roomType}}</a-descriptions-item>
                                <a-descriptions-item label="房间数量">{{idOrder.roomNum}}</a-descriptions-item>
                                <a-descriptions-item label="入住人数">{{idOrder.peopleNum}}</a-descriptions-item>
                                <a-descriptions-item label="是否携带孩童">{{idOrder.haveChild}}</a-descriptions-item>
                                <a-descriptions-item label="价格">{{idOrder.price}}</a-descriptions-item>
                                <a-descriptions-item label="客户名称">{{idOrder.clientName}}</a-descriptions-item>
                                <a-descriptions-item label="手机号">{{idOrder.phoneNumber}}</a-descriptions-item>
                                <a-descriptions-item label="订单状态">{{idOrder.orderState}}</a-descriptions-item>
                                <a-descriptions-item label="撤销理由" v-if="idOrder.orderState=='客户撤销'">{{idOrder.cancelReason}}</a-descriptions-item>
                            </a-descriptions>
                        </a-modal>
                        <a-divider type="vertical"></a-divider>
<!--                        <a-popconfirm-->
<!--                            title="确定想删除该订单吗？"-->
<!--                            @confirm="deleteOrder(record)"-->
<!--                            okText="确定"-->
<!--                            cancelText="取消"-->
<!--                        >-->
<!--                            <a-button type="danger" size="small">删除订单</a-button>-->
                        <a-popover title="订单状态管理" trigger="click" :ref="`popover-${record.id}`">
                            <a slot="content" @click="change(record.id,'已入住')"><a-tag>已入住</a-tag></a>
                            <a slot="content" @click="change(record.id,'已执行')"><a-tag>已执行</a-tag></a>
                            <a slot="content" @click="change(record.id,'酒店撤销')"><a-tag>撤销</a-tag></a>
                            <a slot="content" @click="change(record.id,'异常')"><a-tag>异常</a-tag></a>
                            <a-button type="primary" size="small" @click="manage">订单管理</a-button>
                        </a-popover>
<!--                        </a-popconfirm>-->
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="基本信息维护" key="3">
                <a-form :form="form" style="margin-top: 30px">
                    <a-form-item label="名称" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                    <span>{{ currentHotelInfo.name }}</span>
                    </a-form-item>
                    <a-form-item label="地址" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                                placeholder="请填写地址"
                                v-decorator="['address', { rules: [{ required: true, message: '请输入地址' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ currentHotelInfo.address }}</span>
                    </a-form-item>
                    <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                                placeholder="请填写手机号"
                                v-decorator="['phoneNum', { rules: [{ required: true, message: '请输入手机号' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ currentHotelInfo.phoneNum}}</span>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                        <a-button type="primary" @click="saveModify">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                        <a-button type="primary" @click="modifyInfo">
                            修改信息
                        </a-button>
                    </a-form-item>


                    <template v-for="tag in tags">
                      <a-tag :key="tag.tagName" closable @close="()=>handleClose(tag)">
                        {{tag.tagName}}

                      </a-tag>
                    </template>
                    <a-input
                      v-if="inputVisible"
                      ref="input"
                      type="txt"
                      size="small"
                      :style="{ width: '78px' }"
                      :value="inputValue"
                      @change="handleInputChange"
                      @blur="handleInputConfirm"
                      @keyup.enter="handleInputConfirm"
                    />
                  <a-tag v-else style="background: #fff; borderStyle: dashed;" @click="showInput">
                    <a-icon type="plus" /> 新的酒店服务标签
                  </a-tag>


                </a-form>
            </a-tab-pane>
        </a-tabs>
        <AddHotelModal></AddHotelModal>
        <AddRoomModal></AddRoomModal>
        <Coupon></Coupon>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddHotelModal from './components/addHotelModal'
import AddRoomModal from './components/addRoomModal'
import Coupon from './components/coupon'
const moment = require('moment')
const columns1 = [
    {
        title: '酒店名',
        dataIndex: 'name',
    },
    {
        title: '商圈',
        dataIndex: 'bizRegion',
    },
    {
        title: '地址',
        dataIndex: 'address',
    },
    {
        title: '酒店星级',
        dataIndex: 'hotelStar'
    },
    {
        title: '评分',
        dataIndex: 'rate',
    },
    {
        title: '简介',
        dataIndex: 'description',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
const columns2 = [
    {
        title: '订单号',
        dataIndex: 'id',
    },
    {
        title: '酒店名',
        dataIndex: 'hotelName',
    },
    {
        title: '房型',
        dataIndex: 'roomType',
        scopedSlots: { customRender: 'roomType' }
    },
    {
        title: '入住时间',
        dataIndex: 'checkInDate',
        scopedSlots: { customRender: 'checkInDate' }
    },
    {
        title: '离店时间',
        dataIndex: 'checkOutDate',
        scopedSlots: { customRender: 'checkOutDate' }
    },
    {
        title: '入住人数',
        dataIndex: 'peopleNum',
    },
    {
        title: '房价',
        dataIndex: 'price',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
export default {
    name: 'manageHotel',
    data(){
        return {
            currentIndex: '',
            visible: false,
            contentVisible: false,
            modify: false,
            formLayout: 'horizontal',
            pagination: {},
            columns1,
            columns2,
            form: this.$form.createForm(this, { name: 'manageHotel' }),
            inputVisible: false,
            inputValue: '',
        }
    },
    components: {
        AddHotelModal,
        AddRoomModal,
        Coupon,
    },
    computed: {
        ...mapGetters([
            'orderList',
            'hotelList',
            'addHotelModalVisible',
            'addRoomModalVisible',
            'activeHotelId',
            'couponVisible',
            'idOrder',
            'hotelInfo',
            'userId',
            'hotelId',
            'currentHotelId',//used
            'currentHotelInfo',//used
            'tags',
        ]),
    },
    async mounted() {
        console.log("this.userId",this.userId)//1
        console.log("this.currentHotelId",this.currentHotelId)//1
        console.log("this.hotelId",this.hotelId)//1
        await this.getHotelList()
        await this.getAllOrders()
        //console.log("0602::2",state.hotelId) 'state' is not defined  no-undef
        await this.getHotelInfo()
        await this.getAllTags(this.currentHotelId)
    },
    methods: {
        ...mapMutations([
            'set_addHotelModalVisible',
            'set_addRoomModalVisible',
            'set_couponVisible',
            'set_activeHotelId',
            'set_hotelId',
        ]),
        ...mapActions([
            'getHotelList',
            'getAllOrders',
            'getHotelCoupon',
            'getOrderById',
            'changeStatus',
            'getHotelInfo',
            'updateHotelInfo',
            'getAllTags',
            'deleteTag',
            'addTag',
        ]),
        manage(index,item){
            for (const key in this.$refs) {
                if (key.indexOf('popover-') !== -1) {
                    this.$refs[key].doClose();
                }
            }
        },
        change(orderid,status){
            const data={
                orderid:orderid,
                status:status
            }
            this.changeStatus(data)
        },
        addHotel() {
            this.set_addHotelModalVisible(true)
        },
        addRoom(record) {
            this.set_activeHotelId(record.id)
            this.set_addRoomModalVisible(true)
        },
        showCoupon(record) {
            this.set_activeHotelId(record.id)
            this.set_couponVisible(true)
            this.getHotelCoupon()
        },
        deleteHotel(){

        },
        deleteOrder(){

        },
        cancelContent(){
            this.contentVisible=false
        },
        showContentModal(recordid){
            this.contentVisible=true
            this.currentIndex=recordid
            console.log('你好啊啊啊啊啊啊啊')
            console.log(recordid)
            this.getOrderById(recordid)
            console.log('点击显示时向后端发送请求更新orderById对象')
        },
        modifyInfo() {
            setTimeout(() => {
                this.form.setFieldsValue({
                    'phoneNum': this.currentHotelInfo.phoneNum,
                    'address':this.currentHotelInfo.address,
                })
            }, 0)
            this.modify = true
        },
        cancelModify() {
            this.modify = false
        },
        saveModify(){
            console.log(this.form.getFieldValue('phoneNum'))
            this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        phoneNum: this.form.getFieldValue('phoneNum'),
                        address: this.form.getFieldValue('address')
                    }
                    this.updateHotelInfo(data).then(()=>{
                        this.modify = false
                    })
                }
            });
        },
        showInput() {
            this.inputVisible = true;
            this.$nextTick(function() {
                this.$refs.input.focus();
            });
        },
        handleClose(removedTag){
            console.log('removedTag',removedTag)
            console.log('removedTag.id',removedTag.id)
            console.log('removedTag.hotelId',removedTag.hotelId)
            this.deleteTag(removedTag)
        },
        handleInputChange(e) {
            this.inputValue = e.target.value;
        },
        async handleInputConfirm(){
            const inputValue=this.inputValue;
            //console.log("currentHotelId",this.currentHotelId)
            if(inputValue==='')
                this.inputVisible=false
            else{
                const param={
                    hotelId:this.currentHotelId,
                    tagName:inputValue
                }
                await this.addTag(param)
                this.inputValue=''
                this.inputVisible=false
            }

        }
    }
}
</script>
<style scoped lang="less">
    .manageHotel-wrapper {
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
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>
