<template>
    <a-modal
            :visible="orderModalVisible"
            title="预定详情"
            cancelText="取消"
            okText="下单"
            @cancel="cancelOrder"
            @ok="handleSubmit"
    >
        <a-form :form="form"  >
            <a-form-item v-bind="formItemLayout" label="房型信息" style="margin-bottom:2px">
                <span>{{ currentOrderRoom.roomType }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住人姓名" style="margin-bottom:2px">
                <a-input
                        v-decorator="[
                        'clientName',
                        { rules: [{required: true, message: '请填写入住人姓名', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="手机号" style="margin-bottom:2px">
                <a-input
                        v-decorator="[
                        'phoneNumber',
                        { rules: [{required: true, message: '请填写入住人联系手机', }] }
                    ]"
                />
            </a-form-item>

<!--            <a-form-item v-bind="formItemLayout" label="日期" style="margin-bottom:2px">-->
<!--                <a-range-picker-->
<!--                    format="YYYY-MM-DD"-->
<!--                    @change="changeDate"-->
<!--                    v-decorator="[-->
<!--                        'date',-->
<!--                        {-->
<!--                            rules: [{ required: true, message: '请选择入住时间' }]-->
<!--                        }-->
<!--                    ]"-->
<!--                        :placeholder="['入住日期','退房日期']"-->
<!--                />-->
<!--            </a-form-item >-->
            <a-form-item v-bind="formItemLayout" label="入住日期" style="margin-bottom: 2px">
                <a-date-picker
                    :disabled-date="disabledStartDate"
                    format="YYYY-MM-DD"
                    @change="changeDate"
                    v-decorator="[
                        'checkInDate',
                        {
                            rules: [{ required: true, message: '请选择入住日期' }]
                        }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="退房日期" style="margin-bottom: 2px">
                <a-date-picker
                    :disabled-date="disabledStartDate"
                    format="YYYY-MM-DD"
                    @change="changeDate"
                    v-decorator="[
                        'checkOutDate',
                        {
                            rules: [{ required: true, message: '请选择退房退房日期' }]
                        }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住人数" style="margin-bottom:2px">
                <a-select
                        v-decorator="[
                        'peopleNum',
                        { rules: [{ required: true, message: '请选择入住人数' }] },
                    ]"
                        placeholder="请选择入住人数"
                        @change="changePeopleNum"
                >
                    <a-select-option :value="1">
                        1
                    </a-select-option>
                    <a-select-option :value="2">
                        2
                    </a-select-option>
                    <a-select-option :value="3">
                        3
                    </a-select-option>
                    <a-select-option :value="4">
                        4
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="有无儿童" style="margin-bottom:2px">
                <a-radio-group
                        v-decorator="[
                        'haveChild',
                        { rules: [{required: true, message: '请选择有无儿童入住', }] }
                    ]"
                >
                    <a-radio :value="1">有</a-radio>
                    <a-radio :value="0">无</a-radio>
                </a-radio-group>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房间数" style="margin-bottom:2px">
                <a-select
                        v-decorator="[
                        'roomNum',
                        { rules: [{ required: true, message: '请选择房间数' }] },
                    ]"
                        placeholder="请选择房间数"
                        @change="changeRoomNum"
                >
                    <a-select-option :value="1">
                        1
                    </a-select-option>
                    <a-select-option :value="2">
                        2
                    </a-select-option>
                    <a-select-option :value="3">
                        3
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房间单价" style="margin-bottom:2px">
                <span style="margin-bottom:5px">{{ currentOrderRoom.price }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="总价" style="margin-bottom:2px">
                <span>￥{{ totalPrice }}</span>
            </a-form-item>

            <a-divider v-if="isMember" style="margin-top:2px;margin-bottom:2px;"></a-divider>
            <a-form-item v-bind="formItemLayout" label="当前会员积分：" v-if="isMember" style="margin-bottom:2px">
                <span>{{memInfo.memberPoints}}</span>
            </a-form-item>
            <a-checkbox v-model="usePoints" @change="onMemberPointsChange" v-if="isMember" style="margin-bottom:2px">
                使用积分抵扣（100积分抵扣1元现金）
            </a-checkbox>
            <a-form-item v-bind="formItemLayout" label="会员折扣价" v-if="isMember" style="margin-bottom:2px">
                <span>￥{{memPrice}}</span>
            </a-form-item>

            <a-divider style="margin-top:2px;margin-bottom:2px;"></a-divider>
            <h2 v-if="orderMatchCouponList.length>0">优惠</h2>
            <a-checkbox-group v-model="checkedList" @change="onchange" v-if="orderMatchCouponList.length>0">
                <a-table
                        :columns="columns"
                        :dataSource="orderMatchCouponList"
                        :showHeader="false"
                        bordered
                        v-if="orderMatchCouponList.length>0"
                >
                    <a-checkbox
                            slot="id"
                            slot-scope="record"
                            :value="record"
                    >
                    </a-checkbox>
                </a-table>
            </a-checkbox-group>
            <a-form-item v-if="orderMatchCouponList.length===0" style="margin-bottom:2px">
                <span>当前订单没有可用优惠券</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="结算后总价" style="margin-bottom:2px">
                <span>￥{{ finalPrice }}</span>
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AFormItem from "ant-design-vue/es/form/FormItem";
import {
    reserveHotelAPI
} from '@/api/order'
const moment = require('moment')
const columns = [
    {
        title: '勾选',
        dataIndex: 'id',
        scopedSlots: {customRender: 'id'}
    },
    {
        title: '优惠类型',
        dataIndex: 'couponName',
        scopedSlots: {customRender: 'couponName'}
    },
    {
        title: '优惠简介',
        dataIndex: 'description',

    },
    {
        title: '优惠金额',
        dataIndex: 'discountMoney',
    },
  ];
export default {
    name: 'orderModal',
    components: {AFormItem},
    data() {
        return {
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },//12 6
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 }, //24 16
                    sm: { span: 16 },
                },
            },
            columns,
            checkedList: [],
            totalPrice: '',
            memPrice:'',
            finalPrice: '',
            usePoints:false,
            newDate:false,
        }
    },
    computed: {
        ...mapGetters([
            'orderModalVisible',
            'currentOrderRoom',
            'currentHotelId',
            'currentHotelInfo',
            'userId',
            'orderMatchCouponList',
            'isMember',
            'memInfo'
        ]),
    },
    mounted(){
        this.getMemInfo()
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'orderModal' });
    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible',
            'set_orderMatchCouponList'
        ]),
        ...mapActions([
            'addOrder',
            'getOrderMatchCoupons',
            'getMemInfo',
            'increaseMemberPoints',
            'decreaseMemberPoints'
        ]),
        cancelOrder() {
            this.set_orderModalVisible(false)
        },
        confirmOrder() {

        },
        disabledStartDate(startValue) {
            const endValue = this.endValue;
            if(startValue && endValue) {
                return startValue.valueOf() > endValue.valueOf() || moment(startValue).format("YYYY-MM-DD") < moment().format("YYYY-MM-DD") || moment(startValue).diff(moment(), 'day') > 30;
            } else if(startValue) {
                return moment(startValue).format("YYYY-MM-DD") < moment().format("YYYY-MM-DD") || moment(startValue).diff(moment(), 'day') > 30;
            }
            return false;
        },
        disabledEndDate(endValue) {
            const startValue = this.startValue;
            if(startValue && endValue) {
                return startValue.valueOf() >= endValue.valueOf() || moment(endValue).format("YYYY-MM-DD") < moment().format("YYYY-MM-DD") || moment(endValue).diff(moment(), 'day') > 30;
            } else if(endValue) {
                return moment(endValue).format("YYYY-MM-DD") < moment().format("YYYY-MM-DD") || moment(endValue).diff(moment(), 'day') > 30;
            }
            return false;
        },
        changeDate() {
            if (this.totalPrice != '') {
                this.totalPrice = this.form.getFieldValue('roomNum') * moment(this.form.getFieldValue('checkOutDate')).diff(moment(this.form.getFieldValue('checkInDate')), 'day') * Number(this.currentOrderRoom.price)

                if(this.newDate)
                    this.newDate=false
                else
                    this.newDate=true
            }
        },
        changePeopleNum(v) {
        },
        changeRoomNum(v) {
            this.totalPrice = Number(v) * Number(this.currentOrderRoom.price) * moment(this.form.getFieldValue('checkOutDate')).diff(moment(this.form.getFieldValue('checkInDate')), 'day')
        },
        onchange() {
            console.log('v-model=\'checkedList\'',this.checkedList)//为所有选中的优惠券
            //console.log('onchange(msg)',msg)//也为所有选中的优惠券
            if(this.isMember)
                this.finalPrice=this.memPrice
            else
                this.finalPrice=this.totalPrice
            this.orderMatchCouponList.filter(item => this.checkedList.indexOf(item.id)!=-1).forEach(item => this.finalPrice= (this.finalPrice-item.discountMoney).toFixed(2))

            // if(this.checkedList.length>0){
            //     this.orderMatchCouponList.filter(item => this.checkedList.indexOf(item.id)!=-1).forEach(item => this.finalPrice= this.finalPrice-item.discountMoney)
            // }else{
            //
            // }
        },
        onMemberPointsChange(){
            console.log("usePoints",this.usePoints)
        },
        handleSubmit(e) {
            e.preventDefault();
            console.log("finalPrice",this.finalPrice)
            this.form.validateFieldsAndScroll(async (err, values) => {
                if (!err) {


                    const data = {
                        hotelId: this.currentHotelId,
                        hotelName: this.currentHotelInfo.name,
                        userId: Number(this.userId),
                        residentName: this.form.getFieldValue('clientName'),
                        phoneNumber:this.form.getFieldValue('phoneNumber'),
                        checkInDate: moment(this.form.getFieldValue('checkInDate')).format('YYYY-MM-DD'),
                        checkOutDate: moment(this.form.getFieldValue('checkOutDate')).format('YYYY-MM-DD'),
                        roomType: this.currentOrderRoom.roomType == '大床房' ? 'BigBed' : this.currentOrderRoom.roomType == '双床房' ? 'DoubleBed' : 'Family',
                        roomNum: this.form.getFieldValue('roomNum'),

                        peopleNum: this.form.getFieldValue('peopleNum'),
                        haveChild: this.form.getFieldValue('haveChild'),
                        createDate: moment().format('YYYY-MM-DD'),
                        //price: this.checkedList.length > 0 ? this.finalPrice: this.totalPrice
                        price:this.finalPrice,
                        whetherComment: 0
                    }
                    console.log('orderInfo',data)

                    const res=await reserveHotelAPI(data)
                    if(res){
                        message.success('预定成功')

                        console.log('usePoints',this.usePoints)
                        if(this.usePoints){
                            const data={
                                userId:this.userId,
                                memberPoints:parseInt(this.memInfo.memberPoints/100)*100   //1137//100*100=11*100=1100
                            }
                            console.log('decreaseMemberPointsInfo',data)
                            await this.decreaseMemberPoints(data)
                            await this.getMemInfo()
                        }

                        this.set_orderModalVisible(false)
                    }

                }
            })
        },
    },
    watch:{
        totalPrice(val) {
            //得到最新的会员价（如果是会员的话）
            if(this.isMember) {
                // this.usePoints=false

                this.memPrice = (0.9 * this.totalPrice).toFixed(2)
                console.log("totalPrice",this.totalPrice)
                console.log("memPrice=0.9*totalPrice=",this.memPrice)

                //下三行为测试
                if(this.usePoints){
                    this.memPrice-=parseInt(this.memInfo.memberPoints/100)
                }


                this.finalPrice=this.memPrice;
            } else{
                this.finalPrice=this.totalPrice
            }

            //得到最新的匹配优惠券
            let data = {
                userId: this.userId,
                hotelId: this.currentHotelId,
                orderPrice: this.isMember?this.memPrice:this.totalPrice,//如果是会员，用会员优惠价来匹配优惠券
                roomNum: this.form.getFieldValue('roomNum'),
                checkIn: moment(this.form.getFieldValue('checkInDate')).format('YYYY-MM-DD'),
                checkOut: moment(this.form.getFieldValue('checkOutDate')).format('YYYY-MM-DD'),
                createDate: moment().format('YYYY-MM-DD')
            }
            this.getOrderMatchCoupons(data)

            this.checkedList=[]
        },
        usePoints(val){
            let subAmount=parseInt(this.memInfo.memberPoints/100)
            if(!val){//取消勾选，不用积分抵扣
                this.memPrice+=subAmount
            }else{//勾选，使用积分抵扣
                this.memPrice-=subAmount
            }
            this.finalPrice=this.memPrice

            //得到最新的匹配优惠券
            let data = {
                userId: this.userId,
                hotelId: this.currentHotelId,
                orderPrice: this.isMember?this.memPrice:this.totalPrice,//如果是会员，用会员优惠价来匹配优惠券
                roomNum: this.form.getFieldValue('roomNum'),
                checkIn: moment(this.form.getFieldValue('checkInDate')).format('YYYY-MM-DD'),
                checkOut: moment(this.form.getFieldValue('checkOutDate')).format('YYYY-MM-DD'),
                createDate: moment().format('YYYY-MM-DD')
            }
            this.getOrderMatchCoupons(data)

            this.checkedList=[]
        },
        orderModalVisible(val){
            this.form.resetFields()
            this.usePoints=false
            this.checkedList=[]
            console.log('checkedList',this.checkedList)
            this.set_orderMatchCouponList([])
            console.log('orderMatchCouponList',this.orderMatchCouponList)
            this.totalPrice=0
        },
        newDate(val){
            this.set_orderMatchCouponList([])
            console.log('orderMatchCouponList',this.orderMatchCouponList)
            //得到最新的匹配优惠券
            let data = {
                userId: this.userId,
                hotelId: this.currentHotelId,
                orderPrice: this.isMember?this.memPrice:this.totalPrice,//如果是会员，用会员优惠价来匹配优惠券
                roomNum: this.form.getFieldValue('roomNum'),
                checkIn: moment(this.form.getFieldValue('checkInDate')).format('YYYY-MM-DD'),
                checkOut: moment(this.form.getFieldValue('checkOutDate')).format('YYYY-MM-DD'),
                createDate: moment().format('YYYY-MM-DD')
            }
            this.getOrderMatchCoupons(data)
            console.log('orderMatchCouponList',this.orderMatchCouponList)
            this.checkedList=[]

        }
    }
}
</script>
