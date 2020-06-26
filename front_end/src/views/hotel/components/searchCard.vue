<template>
    <a-drawer
            title="筛选您心仪的酒店"
            placement="left"
            :closable="false"
            :visible="visible"
            :width="600"
            @close="onClose"
    >
        <a-card :bordered="false">

            <a-row>
                <a-col :span="4">
                    <a-icon type="crown" theme="twoTone" two-tone-color="#18a581" class="icon-box" />
                    <span class="desc-box">排序</span>
                </a-col>
                <a-col :span="20">
                    <a-radio-group v-model="type" @change="onChange">
                        <a-radio value="rate">
                            好评优先
                        </a-radio>
                        <a-radio value="history">
                            预定过优先
                        </a-radio>
                    </a-radio-group>
                </a-col>
            </a-row>

            <a-row class="margin-top">
                <a-col :span="4">
                    <a-icon type="star" theme="twoTone" two-tone-color="#18a581" class="icon-box" />
                    <span class="desc-box">星级</span>
                </a-col>
                <a-col :span="4" style="font-size: 14px;" >
                    <a-checkbox :indeterminate="indeterminate" :checked="checkAll" @change="onCheckAllChange">
                        全选
                    </a-checkbox>
                </a-col>
            </a-row>

            <a-row style="margin-top: 10px;">
                <a-col :span="4">

                </a-col>
                <a-col :span="16" style="font-size: 14px;">
                    <a-checkbox-group v-model="checkedList" :options="plainOptions" @change="onChange" />
                </a-col>
            </a-row>

            <a-row class="margin-top">
                <a-col :span="4">
                    <a-icon type="environment" theme="twoTone" two-tone-color="#18a581" class="icon-box" />
                    <span class="desc-box">商圈</span>
                </a-col>
                <a-col :span="20">
                    <a-select mode="multiple" placeholder="请选择商圈" @change="onChangeBizRegion"
                              style="width: 250px;" size="small">
                        <a-select-option v-for="item in ['全部','XiDan']" :key="item">{{item}}</a-select-option>
                    </a-select>
                </a-col>

            </a-row>

            <a-row class="margin-top">
                <a-col :span="4">
                    <a-icon type="calendar" theme="twoTone" two-tone-color="#18a581" class="icon-box" />
                    <span class="desc-box">时间</span>
                </a-col>
                <a-col :span="20">
                    <a-date-picker
                            v-model="startValue"
                            :disabled-date="disabledStartDate"
                            format="YYYY-MM-DD"
                            placeholder="入住时间"
                            @openChange="handleStartOpenChange"
                            size="small">
                    </a-date-picker>
                    <span style="text-align: center; margin-left: 2px; margin-right: 2px;">
                            -
                        </span>
                    <a-date-picker
                            v-model="endValue"
                            :disabled-date="disabledEndDate"
                            format="YYYY-MM-DD"
                            placeholder="退房时间"
                            :open="endOpen"
                            @openChange="handleEndOpenChange"
                            size="small">
                    </a-date-picker>
                </a-col>
            </a-row>

            <a-row class="margin-top">
                <a-col :span="4">
                    <a-icon type="account-book" theme="twoTone" two-tone-color="#18a581" class="icon-box" />
                    <span class="desc-box">价格</span>
                </a-col>
                <a-col :span="2"><span>{{priceRange[0]}}</span></a-col>
                <a-col :span="14" style="display: inline; font-size: 5px;">
                    <a-slider range :max="3000" :step="50" v-model="priceRange" style="margin-right: 15px"/>
                </a-col>
                <a-col :span="2"><span>{{priceRange[1]}}</span></a-col>
            </a-row>

            <a-row class="margin-top" style="text-align: center;">
                <a-col :span="16" :offset="4">
                    <a-button type="primary" shape="round" size="small">筛选</a-button>
                    <a-button type="primary" shape="round" size="small" style="margin-left: 10px">重置</a-button>
                    <a-button type="primary" shape="round" size="small" style="margin-left: 10px" @click="onClose">取消</a-button>
                </a-col>
            </a-row>
        </a-card>
    </a-drawer>
</template>

<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex';
    import moment from 'moment';
    import ARow from "ant-design-vue/es/grid/Row";
    import ACol from "ant-design-vue/es/grid/Col";

    const plainOptions = ['一星', '二星', '三星', '四星', '五星'];
    const defaultCheckedList = ['一星', '二星', '三星', '四星', '五星'];

    export default {
        name: "searchCard",
        components: {ACol, ARow},
        data() {
            return {
                tmpHotelList: [],
                hotelListByBizRegion: [],
                hotelListByStar: [],
                hotelListByDate: [],

                visible: true,

                type: 'rate',

                checkedList: defaultCheckedList,
                indeterminate: true,
                checkAll: false,
                plainOptions,

                startValue: null,
                endValue: null,
                endOpen: false,

                priceRange: [0, 3000],
            }
        },
        async mounted() {
            await this.getHotelList(this.userId)
            this.tmpHotelList = this.hotelList
            this.hotelListByBizRegion = this.hotelList
            this.hotelListByStar = this.hotelList
            this.hotelListByDate = this.hotelList
        },
        computed: {
            ...mapGetters([
                'userId',
                'hotelList',
                'hotelListLoading',
            ])
        },
        methods: {
            ...mapMutations([
                'set_hotelListParams',
                'set_hotelListLoading',
            ]),
            ...mapActions([
                'getHotelList',
                'getHotelByDate'
            ]),

            onClose() {
                this.visible = false;
            },

            // 星级
            onChange(checkedList) {
                this.indeterminate = !!checkedList.length && checkedList.length < plainOptions.length;
                this.checkAll = checkedList.length === plainOptions.length;
            },
            onCheckAllChange(e) {
                Object.assign(this, {
                    checkedList: e.target.checked ? plainOptions : [],
                    indeterminate: false,
                    checkAll: e.target.checked,
                });
            },

            // 商圈
            onChangeBizRegion(value) {
                this.hotelListByBizRegion = []
                let all = false
                if (value.length == 0) {
                    this.hotelListByBizRegion = this.hotelList
                } else {
                    for (var i = 0; i < value.length; i++) {
                        if (value[i] == "全部") {
                            all = true
                            break
                        }
                    }
                    if (all) {
                        this.hotelListByBizRegion = this.hotelList
                    } else {
                        for (var i = 0; i < this.hotelList.length; i++) {
                            for (var j = 0; j < value.length; j++) {
                                if (this.hotelList[i].bizRegion == value[j]) {
                                    this.hotelListByBizRegion.push(this.hotelList[i])
                                    console.log(this.hotelList[i])
                                    break
                                }
                            }
                        }
                    }
                }
                this.onChangeTmpHotelList()
            },

            onChangeStar(value) {
                this.hotelListByStar = []
                let all = false
                if (value.length == 0) {
                    this.hotelListByStar = this.hotelList
                } else {
                    for (var i = 0; i < value.length; i++) {
                        if (value[i] == "全部") {
                            all = true
                            break
                        }
                    }
                    if (all) {
                        this.hotelListByStar = this.hotelList
                    } else {
                        for (let i = 0; i < this.hotelList.length; i++) {
                            for (let j = 0; j < value.length; j++) {
                                if (this.hotelList[i].hotelStar == value[j]) {
                                    this.hotelListByStar.push(this.hotelList[i])
                                    break
                                }
                            }
                        }
                    }
                }
                this.onChangeTmpHotelList()
            },
            async onChangeDate(value) {
                let checkInDate = moment(value[0]._d).format('YYYY-MM-DD')
                let checkOutDate = moment(value[1]._d).format('YYYY-MM-DD')
                await this.getHotelByDate({
                    userId: Number(this.userId),
                    checkInDate: checkInDate,
                    checkOutDate: checkOutDate
                })
                this.onChangeTmpHotelList()
            },
            onChangeTmpHotelList() {
                this.tmpHotelList = []
                for (var i = 0; i < this.hotelListByBizRegion.length; i++) {
                    let flag = false
                    for (var j = 0; j < this.hotelListByStar.length; j++) {
                        if (this.hotelListByBizRegion[i].id == this.hotelListByStar[i].id) {
                            flag = true
                            break
                        }
                    }
                    if (flag) {
                        flag = false
                        for (var j = 0; j < this.hotelListByDate.length; j++) {
                            if (this.hotelListByBizRegion[i].id == this.hotelListByDate[i].id) {
                                flag = true
                                break
                            }
                        }
                        if (flag) {
                            this.tmpHotelList.push(this.hotelListByBizRegion[i])
                        }
                    }
                }
            },
            pageChange(page, pageSize) {
                const data = {
                    pageNo: page - 1
                }
                this.set_hotelListParams(data)
                this.set_hotelListLoading(true)
                this.getHotelList(this.userId)
            },
            jumpToDetails(id) {
                this.$router.push({name: 'hotelDetail', params: {hotelId: id}})
            },
            openNotification(placement) {
                this.$notification.open({
                    message: `网站会员尊享优惠`,
                    description: this.msg,
                    duration: 0,
                    // icon: <a-icon type="smile" style="color: #108ee9" />,
                    placement
                });
            },
            itemRender(current, type, originalElement) {
                if (type === 'prev') {
                    return
                <
                    a > 上一页 < /a>;
                } else if (type === 'next') {
                    return
                <
                    a > 下一页 < /a>;
                }
                return originalElement;
            },
            disabledStartDate(startValue) {
                const endValue = this.endValue;
                if (startValue && endValue) {
                    return startValue.valueOf() > endValue.valueOf() || moment(startValue).format("YYYY-MM-DD") < moment().format("YYYY-MM-DD") || moment(startValue).diff(moment(), 'day') > 30;
                } else if (startValue) {
                    return moment(startValue).format("YYYY-MM-DD") < moment().format("YYYY-MM-DD") || moment(startValue).diff(moment(), 'day') > 30;
                }
                return false;
            },
            disabledEndDate(endValue) {
                const startValue = this.startValue;
                if (startValue && endValue) {
                    return startValue.valueOf() >= endValue.valueOf() || moment(endValue).format("YYYY-MM-DD") < moment().format("YYYY-MM-DD") || moment(endValue).diff(moment(), 'day') > 30;
                } else if (endValue) {
                    return moment(endValue).format("YYYY-MM-DD") < moment().format("YYYY-MM-DD") || moment(endValue).diff(moment(), 'day') > 30;
                }
                return false;
            },
            handleStartOpenChange(open) {
                if (!open) {
                    this.endOpen = true;
                }
            },
            handleEndOpenChange(open) {
                this.endOpen = open;
            },
        }
    }
</script>

<style scoped>
    .desc-box {
        margin-left: 5px;
        font-size: 16px;
        font-weight: bold;
    }
    .icon-box {
        font-size: 20px;
    }
    .form-item {
        font-size: 10px;
    }
    .code-box-demo .ant-slider {
        margin-bottom: 16px;
    }
    .margin-top {
        margin-top: 45px;
    }
</style>
