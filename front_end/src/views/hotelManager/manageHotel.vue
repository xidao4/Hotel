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
                        <a-button type="primary" size="small">订单详情</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                            title="确定想删除该订单吗？"
                            @confirm="deleteOrder(record)"
                            okText="确定"
                            cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除订单</a-button>
                        </a-popconfirm>
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
            modify: false,
            formLayout: 'horizontal',
            pagination: {},
            columns1,
            columns2,
            form: this.$form.createForm(this, { name: 'manageHotel' }),
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
            'hotelInfo',
            'userId',
            'hotelId',
            'currentHotelId',//used
            'currentHotelInfo',//used
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
            'getHotelInfo',
            'updateHotelInfo'
        ]),
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
