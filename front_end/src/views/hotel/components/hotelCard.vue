<template>
    <div>
        <a-card hoverable class="hotelCard">
            <a-row type="flex" align="center">
                <a-col :span="6">
                    <div class="img-box">
                        <img :src="previewUrl" alt="hotelImg" v-if="previewUrl !==''">
                        <img src="@/assets/cover.jpeg" v-else>
                    </div>
                    <div
                            style="margin-top: 2px; display: inline-flex;"
                            v-for="(item, index) in image_urls"
                            :key="index">
                        <a-avatar
                                shape="square"
                                :src="item"
                                :style="(index > 0)?'margin-left: 2px;':''"
                                :class="(selectedImg === index)?'selectedImg':''"
                                @click.stop="preview(index)"/>
                    </div>
                </a-col>
                <a-col :span="10" :offset="2">
                    <a-row type="flex">
                        <span class="name-box">{{detail.name}}</span>
                        <span style="color: gold; margin-left: 10px;margin-top: 7px" v-if="detail.hasOrderedBefore">
                            预定过的酒店
                        </span>
                    </a-row>
                    <a-row type="flex" align="bottom" style="margin-top: 7px">
                        <div v-for="(item, index) in hotelTags" :key="index">
                            <a-divider type="vertical" v-if="index > 0"></a-divider>
                            <span class="desc-box">{{item}}</span>
                        </div>
                        <span style="margin-left: 4px">...更多</span>
                    </a-row>
                    <a-row type="flex" align="bottom" style="margin-top: 7px">
                        <span class="assess-num-box" style="margin-right: 10px;">{{detail.commentNum}}条评价</span>

                        <span class="desc-box">好评率</span>
                        <span style="width: 105px; margin-left: 6px">
                            <a-progress :stroke-color="{
                                '0%': '#108ee9',
                                '100%': '#87d068',
                              }" :percent="detail.rate*20" status="active" /></span>
                    </a-row>
                    <a-row type="flex" align="bottom" style="margin-top: 7px">
                        <span class="text-box">星级</span>
                        <div class="symbol-box">
                            <a-rate :value="hotelStar" disabled allowHalf/>
                        </div>
                    </a-row>
                    <a-row type="flex" align="bottom" style="margin-top: 7px">
                        <a-icon type="environment" theme="twoTone" two-tone-color="#18a581" style="font-size: 18px" />
                        <span>{{detail.address}}</span>
                    </a-row>
                </a-col>
                <a-col :span="6">
                    <a-row style="margin-top: 20px;">
                        <span style="color: #18a581">
                        ¥
                    </span>
                        <span class="price-box">
                        {{detail.minPrice}}
                    </span>
                        <span>
                        起 >
                    </span>
                    </a-row>
                    <a-row type="flex" align="bottom" style="margin-top: 5px">
                        <div v-for="(item, index) in detail.couponNames" :key="index" style="line-height: 25px">
                            <a-tag color="#18a581" v-if="index < 2">
                                {{item}}
                            </a-tag>
                        </div>
                    </a-row>
                    <a-row type="flex" align="bottom" style="margin-top: 10px">
                        <span style="font-weight: bold">查看更多优惠 >>></span>
                    </a-row>
                </a-col>
            </a-row>
        </a-card>
    </div>
</template>

<script>
    import ARow from "ant-design-vue/es/grid/Row";
    import ACol from "ant-design-vue/es/grid/Col";
    export default {
        name: "test",
        components: {ACol, ARow},
        props: ['detail'],
        data() {
            return {
                previewUrl: '',
                selectedImg: 0,
            }
        },
        computed: {
            hotelStar() {
                if(this.detail.hotelStar === 'One') {
                    return 1;
                } else if(this.detail.hotelStar === 'Two') {
                    return 2;
                } else if(this.detail.hotelStar === 'Three') {
                    return 3;
                } else if(this.detail.hotelStar === 'Four') {
                    return 4;
                } else {
                    return 5;
                }
            },
            hotelTags() {
                let arr = [];
                let cnt = 0;
                let tags = this.detail.tags;
                for(let i = 0; i < tags.length; i++) {
                    if(cnt > 8) break;
                    arr.push(tags[i].tagName);
                    cnt += tags[i].tagName.length
                }
                return arr;
            },
            image_url() {
                if(this.detail.pic_1 != '') {
                    return 'https://supernatural.oss-cn-beijing.aliyuncs.com/' + this.detail.pic_1;
                } else {
                    return ''
                }
            },
            image_urls() {
                let arr = [];
                if(this.detail.pic_1 != '') {
                    arr.push('https://supernatural.oss-cn-beijing.aliyuncs.com/' + this.detail.pic_1)
                } else {
                    arr.push('')
                }
                if(this.detail.pic_2 != '') {
                    arr.push('https://supernatural.oss-cn-beijing.aliyuncs.com/' + this.detail.pic_2)
                }
                if(this.detail.pic_3 != '') {
                    arr.push('https://supernatural.oss-cn-beijing.aliyuncs.com/' + this.detail.pic_3)
                }
                if(this.detail.pic_4 != '') {
                    arr.push('https://supernatural.oss-cn-beijing.aliyuncs.com/' + this.detail.pic_4)
                }
                return arr;
            }
        },
        methods: {
            preview(idx) {
                this.previewUrl = this.image_urls[idx];
                this.selectedImg = idx;
            },
        },
        mounted() {
            this.previewUrl = this.image_urls[0];
        }
    }
</script>

<style scoped lang="less">
    .hotelCard {
        margin-top: 15px;
        margin-left: 20px;
        min-width: 180px;
        height: 230px;
        .img-box {
            width: 180px;
            height: 145px;
            overflow: hidden;
        }
        img {
            width: 220px;
            overflow: hidden;
        }
    }
    .text-box {
        font-size: 14px;
        font-weight: bold;
    }
    .symbol-box {
        font-size: 16px;
        margin-left: 20px;
    }
    .name-box {
        font-size: 23px;
        font-weight: 500;
    }
    .price-box {
        color: #18a581;
        font-size: 30px;
        text-shadow: 0 0 2px rgba(138, 255, 173, 0.3)
    }
    .desc-box {
        font-size: 13px;
        color: grey;
    }
    .assess-num-box {
        color: #5287df;
    }
    .selectedImg {
        border:2.5px solid #18a581;
    }
</style>
