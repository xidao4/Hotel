<template>
    <div class="info-wrapper">
        <a-row type="flex" >
            <a-col  :span="24" :order="1">
<!--                -->
                <a-tabs :tab-position="tabPosition" :size="size" >
                    <a-tab-pane key="1">
                        <span slot="tab">
                            <a-icon type="user" />
                            我的信息
                        </span>
                        <div id="pic" style="width: 500px;height:200px;"></div>
                        <div style="font-size: large;font-weight: bold">个人信息</div>
                        <div class="my_card">
                            <a-form :form="form" style="margin-top: 30px" >
                                <a-form-item  :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" style="margin-bottom:2px;margin-left: 160px">
                                    <a-avatar v-if="!modify" :src="avatar_url" :size="100"></a-avatar>
                                    <EditAvatar v-if="modify" :avatar_url="avatar_url"
                                                @editAvatar="editAvatar"></EditAvatar>
                                </a-form-item>
                                <a-form-item  :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" style="margin-bottom:8px;margin-left: 173px;font-size: 18px" >
                                    <a-input
                                            placeholder="请填写用户名"
                                            v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                                            v-if="modify"
                                    />
                                    <span v-else>{{ userInfo.userName }}</span>
                                </a-form-item>
                                <a-form-item label="邮箱" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 0 }" style="margin-bottom:2px;margin-left: 130px">
                                    <span>{{ userInfo.email }}</span>
                                </a-form-item>
                                <a-form-item label="手机号" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 0 }" style="margin-bottom:2px;margin-left: 130px">
                                    <a-input
                                            placeholder="请填写手机号"
                                            v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                                            v-if="modify"
                                    />
                                    <span v-else>{{ userInfo.phoneNumber}}</span>
                                </a-form-item>
                                <a-form-item label="信用值" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 0 }" style="margin-bottom:2px;margin-left: 130px">
                                    <span>{{ userInfo.credit }}</span>
                                </a-form-item>
                                <a-form-item label="密码" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 0 }"
                                             v-if="modify" style="margin-bottom:2px;margin-left: 130px">
                                    <a-input
                                            v-model="newPwd"
                                            placeholder="请输入新密码"
                                            type="password"
                                            v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' }] }]"
                                    />
                                </a-form-item>
                                <a-form-item label="会员积分" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 0 }" v-if="isMember" style="margin-bottom:2px;margin-left: 130px">
                                    <span>{{this.memInfo.memberPoints}}</span>
                                </a-form-item>
                                <a-form-item label="生日" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 0 }" v-if="isMember" style="margin-bottom:2px;margin-left: 130px">
                                    <span>{{this.memInfo.birthday}}</span>
                                </a-form-item>
                                <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-if="!isMember" style="margin-bottom:2px;margin-left: 130px">
                                    <span v-if="isRegistering">请选择您的生日（不可更改）</span>
                                    <a-date-picker @change="onChange"  v-if="isRegistering"/>
                                    <a-button type="primary" v-else @click="registerBtn" >注册成为会员</a-button>
                                </a-form-item>
                                <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify" style="margin-bottom:2px;margin-left: 80px">
                                    <a-button type="primary" @click="saveModify">
                                        保存
                                    </a-button>
                                    <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                                        取消
                                    </a-button>
                                </a-form-item>
                                <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else style="margin-bottom:2px;margin-left: 120px">
                                    <a-button type="primary" @click="modifyInfo">
                                        修改信息
                                    </a-button>
                                </a-form-item>
                            </a-form>
                        </div>
                    </a-tab-pane>
                    <a-tab-pane  key="2">
                        <span slot="tab">
                            <a-icon type="wallet" />
                            我的订单
                        </span>
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
                                <div class="my-button">
                                    <a-button type="primary" style="margin-top: 2px;" size="small" @click="showContentModal(record.id)">查看</a-button>
                                </div>
                                <a-modal title="订单详情" :visible="contentVisible&&(currentIndex===record.id)"  @cancel="cancelContent" :footer="null" >
                                    <a-descriptions title="订单详情" bordered :column="{ xxl: 4, xl: 3, lg: 3, md: 3, sm: 2, xs: 1 }" layout="vertical">
                                        <a-descriptions-item label="订单号">{{idOrder.id}}</a-descriptions-item>
                                        <a-descriptions-item label="酒店名称">{{idOrder.hotelName}}</a-descriptions-item>
                                        <a-descriptions-item label="入住时间">{{idOrder.checkInDate}}</a-descriptions-item>
                                        <a-descriptions-item label="退房时间">{{idOrder.checkOutDate}}</a-descriptions-item>
                                        <a-descriptions-item label="房间类型">{{idOrder.roomType}}</a-descriptions-item>
                                        <a-descriptions-item label="房间数量">{{idOrder.roomNum}}</a-descriptions-item>
                                        <a-descriptions-item label="入住人数">{{idOrder.peopleNum}}</a-descriptions-item>
                                        <a-descriptions-item label="是否携带孩童" v-if="idOrder.haveChild===false">否</a-descriptions-item>
                                        <a-descriptions-item label="是否携带孩童" v-else>是</a-descriptions-item>
                                        <a-descriptions-item label="价格">{{idOrder.price}}</a-descriptions-item>
                                        <a-descriptions-item label="客户名称">{{idOrder.residentName}}</a-descriptions-item>
                                        <a-descriptions-item label="手机号">{{idOrder.phoneNumber}}</a-descriptions-item>
                                        <a-descriptions-item label="订单状态">{{idOrder.orderState}}</a-descriptions-item>
                                        <a-descriptions-item label="撤销理由" v-if="idOrder.orderState=='客户撤销'">{{idOrder.cancelReason}}</a-descriptions-item>
                                    </a-descriptions>
                                </a-modal>

                                <div class="my-button">
                                    <a-button size="small" style="margin-top: 1px;" v-if="record.orderState==='已执行' && record.whetherComment===0" type="primary" @click="showCommentModal(record.id)">评价</a-button>
                                    <a-button size="small" style="margin-top: 1px;" v-else type="primary" disabled=true @click="showCommentModal(record.id)">评价</a-button>
                                </div>
                                <a-modal title="评价" :visible="commentVisible&&(commentIndex===record.id)" cancelText="取消" okText="确定" @cancel="commentCancel" @ok="commentSubmit(record)">
                                    <a-form :form="commentForm" v-bind="formItemLayout">
                                        <a-form-item label="评价星级" v-bind="formItemLayout">
                                            <a-rate v-model="commentValue">
                                                <a-icon slot="character" type="heart" />
                                            </a-rate>
                                        </a-form-item>
                                        <a-form-item label="评价内容：" v-bind="formItemLayout">
                                            <a-textarea placeholder="请填写评价内容" v-model="commentContent" />
                                        </a-form-item>
                                    </a-form>
                                </a-modal>

                                <div class="my-button" style="margin-top: 1px;">
                                    <a-button type="danger" style="margin-top: 1px;" size="small" @click="showCancelModal(record.id)" v-if="record.orderState!=='已预订'" disabled=true>撤销</a-button>
                                    <a-button type="danger" style="margin-top: 1px;" size="small" @click="showCancelModal(record.id)" v-else>撤销</a-button>
                                </div>
                                <a-modal v-if="record.orderState == '已预订'" title="撤销订单" :visible="visible&&(vv===record.id)" cancelText="取消" okText="确定" @cancel="cancel" @ok="handleSubmit(record)">
                                    <a-input placeholder="请输入撤销理由" maxLength={30} v-model="reason"></a-input>
                                </a-modal>
                            </span>
                        </a-table>
                    </a-tab-pane>
                    <RegisterModal></RegisterModal>
                </a-tabs>
            </a-col>
        </a-row>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import RegisterModal from "./registerModal"
import AFormItem from "ant-design-vue/es/form/FormItem";
import echarts from 'echarts'
import EditAvatar from "../../components/EditAvatar"
const columns = [
    {
        title: '编号',
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
        title: '入住',
        dataIndex: 'checkInDate',
        scopedSlots: { customRender: 'checkInDate' }
    },
    {
        title: '离店',
        dataIndex: 'checkOutDate',
        scopedSlots: { customRender: 'checkOutDate' }
    },
    {
        title: '人数',
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
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
            },
            commentIndex: '',
            commentValue: 5,
            commentContent: '',
            currentIndex: '',
            reason: '',
            visible: false,
            contentVisible: false,
            commentVisible: false,
            modify: false,
            formLayout: 'horizontal',
            pagination: {},
            columns,
            data: [],
            form: this.$form.createForm(this, { name: 'coordinated' }),
            isRegistering:false,
            avatar_url: '',
            date_curve: [],
            credit_curve: [],
            // form: this.$form.createForm(this, { name: 'coordinated' }),
            commentForm: this.$form.createForm(this,{name: 'commentForm'}),
            changedPwd:false,
            newPwd:'',
            tabPosition: 'left',
            size: 'small',
            vv: ''
        }
    },
    inject:['reload'],
    components: {
        AFormItem,
        RegisterModal,
        EditAvatar
    },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo',
            'userOrderList',
            'idOrder',
            'memInfo',
            'registerModalVisible',
            'isMember',
            'dateRecord',
            'creditRecord',
            'comment',

        ])
    },
    async mounted() {
        await this.getUserInfo()
        await this.getUserOrders()
        await this.getMemInfo()
        await this.getUserInfo()
        await this.getUserOrders()
        this.avatar_url = "https://supernatural.oss-cn-beijing.aliyuncs.com/" + this.userInfo.avatar_url
        console.log(this.avatar_url)
        //console.log("loading...")
        await this.getUserCredit()
        this.$nextTick(function () {
            let dom = document.getElementById('pic')
            let myChart = echarts.init(dom)
            myChart.setOption({
                title: {
                    text: '信用变化'
                },
                toolbox: {
                    show: true
                },
                legend: {
                    data: ['信用']
                },
                xAxis: {
                    data: this.dateRecord
                },
                yAxis: {},
                series: [{
                    name: '信用',
                    type: 'line',
                    data: this.creditRecord
                }]
            })
        })
        await this.getCommentByHotelId(1)
    },
    watch:{
      newPwd:function () {
          this.changedPwd=true
      }
    },
    methods: {
        ...mapActions([
            'getUserInfo',
            'getUserOrders',
            'updateUserInfo',
            'cancelOrder',
            'getOrderById',
            'getMemInfo',
            'registerMem',
            'getUserCredit',
            'addHotelComment',
        ]),
        ...mapMutations([
            'set_registerModalVisible',
            'getCommentByHotelId',
            'set_isLw'
        ]),

        editAvatar(url) {
            this.avatar_url = url
        },
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
        showCommentModal(recordid){
            this.commentVisible=true
            this.commentIndex=recordid
        },
        commentCancel(){
            this.commentVisible=false
        },
        showCancelModal(orderId){
            console.log('show cancelModal')
            this.vv=orderId
            this.visible=true
        },
        cancelContent(){
            this.contentVisible=false
        },
        cancel(){
            console.log('close cancelModal')
            this.visible=false
        },
        handleSubmit(order){
            console.log('submit')
            // console.log(this.reason)
            // console.log(orderid)
            const data={
                orderid: order.id,
                reason: this.reason,
                hotelId: order.hotelId,
                roomType: order.roomType,
                startTime: order.checkInDate,
                endTime: order.checkOutDate,
                reserveNum: order.roomNum,
            }
            this.cancelOrder(data)
            this.visible=false
            this.reason=''
        },
        async commentSubmit(record){
            console.log(record)
            //record是订单信息
            console.log(record.id)
            console.log(this.commentValue)
            console.log(this.commentContent)
            console.log(this.userInfo)
            //userInfo是用户信息
            console.log(record.hotelName)
            const comment={
                userId: this.userId,
                hotelId: record.hotelId,
                orderId: record.id,
                commentValue: this.commentValue,
                commentContent: this.commentContent,
                avatar: this.userInfo.avatar_url,
                userName: this.userInfo.userName,
                reply: ''
            }
            console.log(comment)
            await this.addHotelComment(comment)
            console.log('Vuex里的comment')
            console.log(this.comment)
            this.commentValue=5
            this.commentContent=''
            this.commentVisible=false
            this.getUserOrders()
        },
        async saveModify() {
            await this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        userName: this.form.getFieldValue('userName'),
                        phoneNumber: this.form.getFieldValue('phoneNumber'),
                        password: this.changedPwd?this.form.getFieldValue('password'):''
                    }
                    this.updateUserInfo(data).then(()=>{
                        this.modify = false
                        console.log('重新取userInfo')
                        this.getUserInfo()
                        this.getMemInfo()
                        console.log('更新结束')
                        this.set_isLw()
                        console.log('强制刷新')
                        this.set_isLw()
                        console.log('刷新解决')
                    })
                }
            });
        },
        modifyInfo() {
            setTimeout(() => {
                this.form.setFieldsValue({
                    'userName': this.userInfo.userName,
                    'phoneNumber': this.userInfo.phoneNumber,
                    'password':this.userInfo.password
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
            this.isRegistering=true
           // this.set_registerModalVisible(true)
        },
        async onChange(date,dateString){
            const data = {
                userId: this.userId,
                birthday: dateString
            }
            await this.registerMem(data)
            this.isRegistering=false
        }
    }
}
</script>
<style scoped lang="less">
    .info-wrapper {
        padding: 60px;

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
        padding: 20px 100px 144px;
        .ant-tabs-bar {
            padding-left: 30px;
        }
    }
    .my_card{
        width: 500px;
        /*margin-left: 250px;*/
        //border: 1px solid blue;
    }
    .my-button{
        margin-bottom:1px;
    }
</style>

