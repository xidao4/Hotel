<template>
    <a-modal
        :visible="addCouponVisible"
        title="添加优惠策略"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
        <!--        a-model中的cancelText对应的是@cancel事件，okText对应的是@ok事件-->
        <!-- 这里是添加策略模态框区域，请编写表单 -->
        <!--        使用v-bind="formItemLayout"来对对应的组件实现排版-->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="优惠券类型" v-bind="formItemLayout">
                <!--通过ant-design-vue去获取表单的数据是使用v-decorator的方式去给每个项去注册，这样才能通过组件去拉取表单的数据，同时对必填项做校验，必填项前面会有星号-->
                <!--问题一：不是很明白为什么要绑定一个changeType事件？对应的方法在非满减特惠的时候会提示"请实现"，不明白这么设计的意图是什么-->
                <a-select v-decorator="['type',{rules: [{required:true,message: '请选择类型'}]}]" @change="changeType">
                    <!--                    v-decorator第一个字段是绑定的名称，第二个字段说明了在未填写时提交表单的提示-->
                    <!--官方不推荐v-model来做数据绑定：我们不推荐在 Form 中使用双向绑定，同一份数据可能在多处使用，如果使用双向绑定，那么数据的修改会同时同步到各个组件，但这并不是我们想要的， 你应该在表单提交成功或失败或确认时同步数据，使用非双向绑定的表单，你会拥有最大限度的控制数据修改/同步的权限。-->
                    <a-select-option value="1">生日特惠</a-select-option>
                    <a-select-option value="2">多间特惠</a-select-option>
                    <a-select-option value="3">满减特惠</a-select-option>
                    <a-select-option value="4">限时特惠</a-select-option>
                    <a-select-option value="5">节日特惠</a-select-option>
                    <!--<a-select-option value="6">会员特惠</a-select-option>-->
                </a-select>
            </a-form-item>
            <a-form-item label="券名" v-bind="formItemLayout">
                <a-input placeholder="请填写优惠券名" v-decorator="['name',{rules: [{required:true,message:'请输入券名'}]}]" />
            </a-form-item>

            <!--            生日优惠所需表单部分-->
            <a-form-item label="优惠金额" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='1'">
                <a-input placeholder="请填写优惠金额" v-decorator="['discountMoney',{rules:[{required:true,message:'请填写优惠金额'}]}]"/>
            </a-form-item>

            <!--            节日优惠所需表单部分-->
            <a-form-item label="优惠金额" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='5'">
                <a-input placeholder="请填写优惠金额" v-decorator="['discountMoney',{rules:[{required:true,message:'请填写优惠金额'}]}]"/>
            </a-form-item>

            <!--&lt;!&ndash;            会员优惠所需表单部分&ndash;&gt;
            <a-form-item label="银会员折扣" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='6'">
                <a-input placeholder="请填写银会员折扣" v-decorator="['discount-silver',{rules:[{required:true,message:'请填写折扣'}]}]"/>
            </a-form-item>
            <a-form-item label="金会员折扣" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='6'">
                <a-input placeholder="请填写金会员折扣" v-decorator="['discount-gold',{rules:[{required:true,message:'请填写折扣'}]}]"/>
            </a-form-item>-->

            <!--            targetMoney满减优惠所需表单部分-->
            <a-form-item label="达标金额" v-if="this.form.getFieldValue('type')==='3'">
                <a-input placeholder="请填写达标金额" v-decorator="['targetMoney',{rules:[{required:true,message:'请填写达标金额'}]}]"/>
            </a-form-item>
            <a-form-item label="优惠金额" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='3'">
                <a-input placeholder="请填写优惠金额" v-decorator="['discountMoney',{rules:[{required:true,message:'请填写优惠金额'}]}]"/>
            </a-form-item>

            <!--            time限时优惠所需表单部分-->
            <a-form-item label="优惠金额" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='4'">
                <a-input placeholder="请填写优惠金额" v-decorator="['discountMoney',{rules:[{required:true,message:'请填写优惠金额'}]}]"/>
            </a-form-item>

            <a-form-item label="起始-失效时间" v-if="this.form.getFieldValue('type')==='4'">
                            <ARangePicker
                            :format="format"
                            showTime
                            onChange={onChange}
                            v-decorator="['time',{rules: [{required:true,message: '请选择时间'}]}]"
                            />
            </a-form-item>


            <!--            targetRoomNum多间优惠所需表单部分-->
<!--                        <a-form-item label="达标房间数" v-if="this.form.getFieldValue('type')==='2'">-->
<!--                            <a-input placeholder="请填写达标房间数" v-decorator="['targetRoomNum',{rules:[{required:true,message:'请填写达标房间数'}]}]"/>-->
<!--                        </a-form-item>-->
                        <a-form-item label="优惠金额" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='2'">
                            <a-input placeholder="请填写三间以上优惠金额" v-decorator="['discountMoney',{rules:[{required:true,message:'请填写具体优惠金额'}]}]"/>
                        </a-form-item>


            <a-form-item label="优惠简介" v-bind="formItemLayout">
                <a-input type="textarea" :rows="4" placeholder="请填写优惠简介"
                         v-decorator="['description',{rules: [{required:true,message:'请填写优惠简介'}]}]" />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import DatePicker from "ant-design-vue/lib/date-picker";

    const { RangePicker } = DatePicker
    export default {
        name: 'addCouponModal',
        components:{
        },
        data() {
            const format="YYYY-MM-DD HH:mm:ss"
            return {
                // label 标签布局，同 <Col> 组件，设置 span offset 值，如 {span: 3, offset: 12} 或 sm: {span: 3, offset: 12}
                // wrapperCol，需要为输入控件设置布局样式时，使用该属性，用法同 labelCol
                // xs、sm是响应尺寸
                // sm：一般用于小屏设备
                // xs：超小型设备
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
                form: this.$form.createForm(this,{name: 'addCouponModal'}),
                format

            }
        },
        computed: {
            //activeHotelId指明当前是对哪个酒店进行添加优惠策略
            ...mapGetters([
                'activeHotelId',
                'addCouponVisible',
            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            // this.form = this.$form.createForm(this, { name: 'addCouponModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addCouponVisible'
            ]),
            ...mapActions([
                // addHotelTargetMoneyCoupon：添加酒店策略接口
                'addHotelTargetMoneyCoupon',
                'addHotelTimeCoupon',
                'addHotelTargetRoomNumCoupon',
                'addHotelBirthdayCoupon',
                'addHotelFestivalCoupon',
                'addHotelVIPSpecialCoupon'
            ]),
            cancel() {
                this.set_addCouponVisible(false)
            },
            changeType(v){
                if( v == '3') {
                }
                else if (v=='4') {
                } else{
                    // this.$message.warning('请实现')
                }
            },
            handleSubmit(e) {
                console.log('添加策略')
                //moment类型的时间
                // console.log(this.form.getFieldValue('time')[0].format())

                // 如果调用这个preventDefault()方法，默认事件行为将不再触发。
                //问题二：为什么需要阻止默认行为？
                //猜测：可能ant-design的@ok方法会有一个默认的回调函数来关闭当前界面，但在该项目中，界面的关闭都是程序员自己来改变visible属性来实现的，所以需要阻止默认行为
                e.preventDefault();
                //validateFieldsAndScroll：ant-design组件自带方法，校验并获取一组输入域的值与 Error，若 fieldNames 参数为空，则校验全部组件
                //校验完后，如果校验不通过的菜单域不在可见范围内，则自动滚动进可见范围
                // Function([fieldNames: string[]], [options: object], callback: Function(errors, values))
                //'[]'表示可选参数
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        if(this.form.getFieldValue('type')==='3') {
                            const data = {
                                // 这里添加接口参数
                                name: this.form.getFieldValue('name'),
                                description: this.form.getFieldValue('description'),
                                type: Number(this.form.getFieldValue('type')),
                                targetMoney: Number(this.form.getFieldValue('targetMoney')),
                                discountMoney: Number(this.form.getFieldValue('discountMoney')),
                                hotelId: Number(this.activeHotelId),
                                status: 1,
                            }
                            // 向store中提交数据
                            // 这个方法里面自带关掉界面的实现
                            this.addHotelTargetMoneyCoupon(data)
                            this.form.resetFields()

                        }
                        else if(this.form.getFieldValue('type')==='2'){
                            const data = {
                                name: this.form.getFieldValue('name'),
                                description: this.form.getFieldValue('description'),
                                type: Number(this.form.getFieldValue('type')),
                                discountMoney: Number(this.form.getFieldValue('discountMoney')),
                                hotelId: Number(this.activeHotelId),
                                status: 1
                            }
                            this.addHotelTargetRoomNumCoupon(data)
                            this.form.resetFields()

                        }
                        else if(this.form.getFieldValue('type')==='4'){
                            const data = {
                                name: this.form.getFieldValue('name'),
                                description: this.form.getFieldValue('description'),
                                type: Number(this.form.getFieldValue('type')),
                                startTime: this.form.getFieldValue('time')[0],
                                endTime: this.form.getFieldValue('time')[1],
                                discountMoney: Number(this.form.getFieldValue('discountMoney')),
                                hotelId: Number(this.activeHotelId),
                                status: 1
                            }
                            this.addHotelTimeCoupon(data)
                            this.form.resetFields()
                        }
                        else if(this.form.getFieldValue('type')==='5'){
                            const data = {
                                name: this.form.getFieldValue('name'),
                                description: this.form.getFieldValue('description'),
                                type: Number(this.form.getFieldValue('type')),
                                discountMoney: Number(this.form.getFieldValue('discountMoney')),
                                hotelId: Number(this.activeHotelId),
                                status: 1
                            }
                            this.addHotelFestivalCoupon(data)
                            this.form.resetFields()

                        }
                        else if(this.form.getFieldValue('type')==='1'){
                            const data = {
                                name: this.form.getFieldValue('name'),
                                description: this.form.getFieldValue('description'),
                                type: Number(this.form.getFieldValue('type')),
                                discountMoney: Number(this.form.getFieldValue('discountMoney')),
                                hotelId: Number(this.activeHotelId),
                                status: 1
                            }
                            this.addHotelBirthdayCoupon(data)
                            this.form.resetFields()

                        }
                        /*else if(this.form.getFieldValue('type')==='6'){
                            const data = {
                                name: this.form.getFieldValue('name'),
                                description: this.form.getFieldValue('description'),
                                type: Number(this.form.getFieldValue('type')),
                                discountSilver: Number(this.form.getFieldValue('discount-silver')),
                                discountGold: Number(this.form.getFieldValue('discount-gold')),
                                hotelId: Number(this.activeHotelId),
                                status: 1
                            }
                            this.addHotelVIPSpecialCoupon(data)
                            this.form.resetFields()

                        }*/
                    }
                });
            },
        }
    }
</script>
