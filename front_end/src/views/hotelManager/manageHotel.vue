<template>
    <div class="manageHotel-wrapper">
        <a-tabs type="card">
            <a-tab-pane  key="2">
                <span slot="tab">
        <a-icon type="file-done"/>
        订单管理
      </span>
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
                        <a-popover title="订单状态管理" trigger="click" :ref="`popover-${record.id}`">
                            <a slot="content" @click="change(record,'已入住')" v-if="record.orderState==='已预订'"><a-tag color="green">已入住</a-tag></a>
                            <a slot="content" @click="change(record,'已入住')" v-else disabled="true" ><a-tag color="red">已入住</a-tag></a>
                            <a slot="content" @click="change(record,'已执行')" v-if="record.orderState==='已预订' || record.orderState==='已入住'"><a-tag color="green">已执行</a-tag></a>
                            <a slot="content" @click="change(record,'已执行')" v-else disabled="true"><a-tag color="red">已执行</a-tag></a>
                            <a slot="content" @click="change(record,'酒店撤销')" v-if="record.orderState==='已预订'"><a-tag color="green">撤销</a-tag></a>
                            <a slot="content" @click="change(record,'酒店撤销')" v-else disabled="true"><a-tag color="red">撤销</a-tag></a>
                            <a slot="content" @click="change(record,'异常')" v-if="record.orderState==='已预订'"><a-tag color="green">异常</a-tag></a>
                            <a slot="content" @click="change(record,'异常')" v-else disabled="true"><a-tag color="red">异常</a-tag></a>
                            <a-button type="primary" size="small" @click="manage">订单管理</a-button>
                        </a-popover>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane  key="3">
                 <span slot="tab">
        <a-icon type="solution"/>
        基本信息
      </span>

              <a-col :span="12">
                <div style="margin-top: 40px">

                  <a-row>
                    <div class="clearfix" style="margin:auto">
                      <a-upload
                              :customRequest="upload"
                              list-type="picture-card"
                              :file-list="fileList"
                              @preview="handlePreview"
                              @change="handleChange"
                      >
                        <div v-if="fileList.length < 8">
                          <a-icon type="plus" />
                          <div class="ant-upload-text">上传</div>
                        </div>
                      </a-upload>
                      <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
                        <img alt="example" style="width: 100%" :src="previewImage" />
                      </a-modal>
                    </div>
                  </a-row>

                  <a-row>
                      <div style="margin-top:15px;">
                        <a-button type="primary" size="default" @click="addRoom()" style="margin-left: 20px;margin-right: 25px;">录入房间</a-button>
                        <a-button type="info" size="default" @click="showCoupon()" style="margin-left: 25px;margin-right: 20px">优惠策略</a-button>
                      </div>
                  </a-row>

                  <a-row>

                      <a-card  title="酒店服务设施标签" style="margin-top:25px;margin-right: 50px">
                        <template v-for="tag in tags">
                          <a-tag :key="tag.tagName" closable @close="()=>handleClose(tag)" style="margin-top:10px">
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
                        <a-tag v-else style="background: #1DA57A; borderStyle: dashed;" @click="showInput">
                          <a-icon type="plus" /> 新的酒店服务标签
                        </a-tag>
                      </a-card>

                  </a-row>

                </div>
                </a-col>
              <a-col :span="12">
                <a-card title="酒店基本信息" style="width:450px;margin-top:40px">
                  <a-form :form="form" style="margin-top: 30px;margin-left:20px;margin-bottom: 30px">
                    <a-form-item label="名称" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" style="margin-bottom:3px">
                      <span>{{ currentHotelInfo.name }}</span>
                    </a-form-item>
                    <a-form-item label="星级" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" style="margin-bottom:3px">
                      <a-rate style="font-size: 15px" :value="hotelStar" disabled allowHalf/>
                    </a-form-item>
                    <a-form-item label="好评率" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" style="margin-bottom:3px">
                      <a-progress :stroke-color="{
                                    '0%': '#108ee9',
                                    '100%': '#87d068',
                                  }" :percent="currentHotelInfo.rate*20" status="active" />
                    </a-form-item>
                    <a-form-item label="地址" :label-col="{ span: 3 }" :wrapper-col="{ span: 18, offset: 1  }" style="margin-bottom:3px">
                      <a-input
                              placeholder="请填写地址"
                              v-decorator="['address', { rules: [{ required: true, message: '请输入地址' }] }]"
                              v-if="modify"
                      />
                      <span v-else>{{ currentHotelInfo.address }}</span>
                    </a-form-item>
                    <a-form-item label="手机" :label-col="{ span: 3 }" :wrapper-col="{ span: 18, offset: 1 }" style="margin-bottom:3px">
                      <a-input
                              placeholder="请填写手机号"
                              v-decorator="['phoneNum', { rules: [{ required: true, message: '请输入手机号' }] }]"
                              v-if="modify"
                      />
                      <span v-else>{{ currentHotelInfo.phoneNum}}</span>
                    </a-form-item>
                    <a-form-item label="描述" :label-col="{ span: 3 }" :wrapper-col="{ span: 18, offset: 1 }" style="margin-bottom:3px">
                      <a-input type="textarea" :rows="4" placeholder="请填写酒店简介"
                               v-decorator="['description',{rules: [{required:true,message:'请填写优惠简介'}]}]"
                               v-if="modify"/>
                      <span v-else>{{currentHotelInfo.description}}</span>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify" style="margin-bottom:3px">
                      <a-button type="primary" @click="saveModify">
                        保存
                      </a-button>
                      <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                        取消
                      </a-button>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else style="margin-bottom:3px">
                      <a-button type="primary" @click="modifyInfo">
                        修改信息
                      </a-button>
                    </a-form-item>
                  </a-form>
                </a-card>

              </a-col>

            </a-tab-pane>
            <a-tab-pane  key="4">
                <span slot="tab">
        <a-icon type="profile"/>
        酒店评价
      </span>
                <a-list
                    class="comment-list"
                    :header="`${comment.length} 评价`"
                    item-layout="horizontal"
                    :data-source="comment"
                >
                    <a-list-item slot="renderItem" slot-scope="item">
                        <a-comment :author="item.userName" :avatar="'https://supernatural.oss-cn-beijing.aliyuncs.com/'+item.avatar">
                            <template slot="actions" v-if="userInfo.userType==='HotelManager'&&(item.reply==='')">
                                <span @click="showReply(item)">{{ actions }}</span>
                                <a-modal :visible="commentvisible&&(item.id==index)" title="回复评价" cancelText="取消" okText="确定" @cancel="cancel" @ok="handleSubmit(item)">
                                    <a-textarea placeholder="请输入回复" v-model="reply"></a-textarea>
                                </a-modal>
                            </template>
                            <p slot="content">
                                <a-rate :default-value="item.commentValue" disabled ><a-icon slot="character" type="heart" /></a-rate><br/><br/>
                                {{ item.commentContent }}
                            </p>
                            <a-comment author="酒店掌柜" avatar="https://ydl8023.oss-cn-beijing.aliyuncs.com/avatar19.png" v-if="item.reply!==''">
                                <p slot="content">
                                    {{ item.reply }}
                                </p>
                            </a-comment>
                        </a-comment>
                    </a-list-item>
                </a-list>
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
import AFormItem from "ant-design-vue/es/form/FormItem";
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
/*function getBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
}*/
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
            actions: 'reply to',
            commentvisible: false,
            reply: '',
            index: '',
            previewVisible: false,
            previewImage: '',
            /*file: {
                uid: 1,
                name: 'image.png',
                status: 'done',
                url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
            },
            maxL: 0,*/
        }
    },
    components: {
        AFormItem,
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
            'bigV',
            'doubleV',
            'familyV',
            'comment',
            'userInfo',
            'pic',
            `fileList`,
        ]),
        hotelStar() {
            if(this.currentHotelInfo.hotelStar === 'One') {
                return 1;
            } else if(this.currentHotelInfo.hotelStar === 'Two') {
                return 2;
            } else if(this.currentHotelInfo.hotelStar === 'Three') {
                return 3;
            } else if(this.currentHotelInfo.hotelStar === 'Four') {
                return 4;
            } else {
                return 5;
            }
        },
    },
    async mounted() {
        // console.log("this.userId",this.userId)//1
        console.log("currentHotelId",this.currentHotelId)//1
        // console.log("this.hotelId",this.hotelId)//1
        await this.getHotelInfo()
        console.log('currentHotelInfo',this.currentHotelInfo)
        //await this.getHotelListLJY()
        await this.getOrderByHotelId(this.currentHotelId)
        //console.log("0602::2",state.hotelId) 'state' is not defined  no-undef
        await this.getAllTags(this.currentHotelId)
        await this.getCommentByHotelId(this.currentHotelId)
        await this.getPics(this.currentHotelId)
        console.log('userInfo',this.userInfo)
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
            'getHotelListLJY',
            'getAllOrders',
            'getHotelCoupon',
            'getOrderById',
            'changeStatus',
            'getHotelInfo',
            'updateHotelInfo',
            'getAllTags',
            'deleteTag',
            'addTag',
            'updateReply',
            'getCommentByHotelId',
            'getPicUrl',
            'changePic',
            'getPics',
            'getOrderByHotelId'
        ]),
        async upload(info){
            console.log("list_before",this.fileList)
            //console.log("len",this.fileList.length)
            const formData = new FormData()
            formData.append('img',info.file)
            //console.log("info",info)
            await this.getPicUrl(formData)
            //console.log("pic",this.pic)
            //console.log(this.fileList.length)
            let file = {
                uid: 0,
                name: 'image.png',
                status: 'done',
                url: "https://supernatural.oss-cn-beijing.aliyuncs.com/" + this.pic,
            }
            if(this.fileList.length!=0){
                file.uid = this.fileList[this.fileList.length-1].uid+1
            }
            //console.log("before",this.fileList)
            this.fileList.push(file)
            await this.changePic({
                pics: this.fileList,
                hotelId: this.currentHotelId
            })
            //console.log("list1",this.fileList[0])
            //console.log("list",this.fileList)
            console.log("list_after",this.fileList)
            //this.fileList.pop(this.fileList.length-1)
            //console.log("file",file)
            //console.log("after",this.fileList.length)
        },
        handleCancel() {
            this.previewVisible = false;
        },
        async handlePreview(file) {
            this.previewImage = file.url || file.preview;
            this.previewVisible = true;
        },
        async handleChange({ fileList }) {
            this.fileList = fileList
            /*if(this.fileList.length>this.maxL){
                this.fileList.pop(-1)
                this.maxL = this.fileList.length
            }*/
            /*for(let i=fileList.length()-1;i>=0;i--){
                if(fileList[i].status=="removed"){
                    //this.fileList.pop(i)
                    console.log(i)
                }
            }*/
            await this.changePic({
                pics: this.fileList,
                hotelId: this.currentHotelId
            })
            await this.getPics(this.currentHotelId)
            console.log("change",this.fileList)
        },
        manage(index,item){
            for (const key in this.$refs) {
                if (key.indexOf('popover-') !== -1) {
                    this.$refs[key].doClose();
                }
            }
        },
        async change(order,status){
            console.log(order)
            const data={
                orderid:order.id,
                status:status,
                hotelId:order.hotelId,
                roomType:order.roomType,
                startTime: order.checkInDate,
                endTime: order.checkOutDate,
                reserveNum: order.roomNum,
            }
            await this.changeStatus(data)
            await this.getOrderByHotelId(this.currentHotelId)
        },
        addRoom() {
            console.log(this.bigV)
            console.log(this.doubleV)
            console.log('familyV:')

            console.log(this.familyV)
            this.set_activeHotelId(this.currentHotelId)
            this.set_addRoomModalVisible(true)
        },
        showCoupon() {
            this.set_activeHotelId(this.currentHotelId)
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
                    'description':this.currentHotelInfo.description
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
                        address: this.form.getFieldValue('address'),
                        description:this.form.getFieldValue('description'),

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

        },
        showReply(item){
            console.log('item:')
            console.log(item)
            this.commentvisible=true
            this.index=item.id
            console.log()
        },
        cancel(){
            this.commentvisible=false
            this.reply=''
        },
        handleSubmit(item){
            console.log('提交中的item')
            console.log(item)
            const param={
                commentId: item.id,
                reply: this.reply
            }
            this.updateReply(param).then(val=>{
                this.getCommentByHotelId(this.currentHotelId)
            })
            console.log('重新获取comment')
            console.log(this.comment)
            this.commentvisible=false
            this.reply=''
        },

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
    .ant-upload-select-picture-card i {
        font-size: 32px;
        color: #999;
    }

    .ant-upload-select-picture-card .ant-upload-text {
        margin-top: 8px;
        color: #666;
    }
</style>
