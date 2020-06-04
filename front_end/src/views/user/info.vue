<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane tab="我的信息" key="1">
                <a-form :form="form" style="margin-top: 30px">
                    <a-form-item label="用户名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                            placeholder="请填写用户名"
                            v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                            v-if="modify"
                        />
                        <span v-else>{{ userInfo.userName }}</span>
                    </a-form-item>
                    <a-form-item label="邮箱" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.email }}</span>
                    </a-form-item>
                    <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                            placeholder="请填写手机号"
                            v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                            v-if="modify"
                        />
                        <span v-else>{{ userInfo.phoneNumber}}</span>
                    </a-form-item>
                    <a-form-item label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.credit }}</span>
                    </a-form-item>
                    <a-form-item label="密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="modify">
                        <a-input
                            placeholder="请输入新密码"
                            v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' }] }]"
                        />
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
            <a-tab-pane tab="我的订单" key="2">
                <a-table
                    :columns="columns"
                    :dataSource="userOrderList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <a-tag slot="orderState" color="blue" slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showContentModal(record.id)">查看</a-button>
                        <a-modal title="订单详情" :visible="contentVisible&&(currentIndex===record.id)"  @cancel="cancelContent" :footer="null" >
                            <a-descriptions title="订单详情" bordered :column="{ xxl: 4, xl: 3, lg: 3, md: 3, sm: 2, xs: 1 }" layout="vertical">
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
                        <a-divider type="vertical" v-if="record.orderState==='已执行'"></a-divider>
                        <a-button size="small" v-if="record.orderState==='已执行'" type="primary">评价</a-button>
                        <a-divider type="vertical" ></a-divider>
                        <a-button type="danger" size="small" @click="showCancelModal" v-if="record.orderState!=='已预订'" disabled=true>撤销</a-button>
                        <a-button type="danger" size="small" @click="showCancelModal" v-else>撤销</a-button>
                        <a-modal v-if="record.orderState == '已预订'" title="撤销订单" :visible="visible" cancelText="取消" okText="确定" @cancel="cancel" @ok="handleSubmit(record.id)">
                            <a-input placeholder="请输入撤销理由" maxLength={30} v-model="reason"></a-input>
                        </a-modal>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="会员信息" key="3">
                <div>
                    <a-button v-if="this.membership===0" @click="registerBtn">注册成为银会员</a-button>
                    <a-button v-if="this.membership===1">升级成为金会员</a-button>
                    <div v-if="this.membership!==0">
                        <div>会员状态：{{this.memInfo.membership}}</div>
                        <div>会员积分：{{this.memInfo.memberPoints}}</div>
                        <div>生日：{{this.memInfo.birthday}}</div>
                    </div>
                </div>
            </a-tab-pane>
            <RegisterModal></RegisterModal>
        </a-tabs>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import RegisterModal from "./registerModal"
const columns = [
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
        title: '状态',
        filters: [{ text: '已预订', value: '已预订' }, { text: '已撤销', value: '已撤销' }, { text: '已入住', value: '已入住' }],
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
export default {
    name: 'info',
    data(){
        return {
            currentIndex: '',
            reason: '',
            visible: false,
            contentVisible: false,
            modify: false,
            formLayout: 'horizontal',
            pagination: {},
            columns,
            data: [],
            form: this.$form.createForm(this, { name: 'coordinated' })
        }
    },
    components: {
        RegisterModal
    },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo',
            'userOrderList',
            'idOrder',
            'membership',
            'memInfo',
            'registerModalVisible',
        ])
    },
    async mounted() {
        await this.getUserInfo()
        await this.getUserOrders()
        await this.getMemInfo()
    },
    methods: {
        ...mapActions([
            'getUserInfo',
            'getUserOrders',
            'updateUserInfo',
            'cancelOrder',
            'getOrderById',
            'getMemInfo',
        ]),
        ...mapMutations([
            'set_registerModalVisible',
        ]),
        showContentModal(recordid){
            this.currentIndex=recordid
            console.log('你好啊啊啊啊啊啊啊')
            console.log(recordid)
            this.getOrderById(recordid)
            console.log('点击显示时向后端发送请求更新orderById对象')
            this.contentVisible=true
            console.log(this.contentVisible)
            console.log(this.currentIndex===recordid)
            console.log(this.currentIndex)
            console.log(this.contentVisible&&(this.currentIndex===recordid))
        },
        showCancelModal(){
            console.log('show cancelModal')
            this.visible=true
        },
        cancelContent(){
            this.contentVisible=false
        },
        cancel(){
            console.log('close cancelModal')
            this.visible=false
        },
        handleSubmit(orderid){
            console.log('submit')
            // console.log(this.reason)
            // console.log(orderid)
            const data={
                orderid: orderid,
                reason: this.reason
            }
            this.cancelOrder(data)
            this.visible=false
        },
        saveModify() {
            this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        userName: this.form.getFieldValue('userName'),
                        phoneNumber: this.form.getFieldValue('phoneNumber'),
                        password: this.form.getFieldValue('password')
                    }
                    this.updateUserInfo(data).then(()=>{
                        this.modify = false
                    })
                }
            });
        },
        modifyInfo() {
            setTimeout(() => {
                this.form.setFieldsValue({
                    'userName': this.userInfo.userName,
                    'phoneNumber': this.userInfo.phoneNumber,
                })
            }, 0)
            this.modify = true
        },
        cancelModify() {
            this.modify = false
        },
        confirmCancelOrder(orderId){
            this.cancelOrder(orderId)
        },
        cancelCancelOrder() {

        },
        registerBtn(){
            this.set_registerModalVisible(true)
            console.log(this.registerModalVisible)
            console.log(this.memInfo.membership)
            console.log(this.memInfo.memberPoints)
        }
    }
}
</script>
<style scoped lang="less">
    .info-wrapper {
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
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
