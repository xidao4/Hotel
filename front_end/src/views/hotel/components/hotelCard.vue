<template>
    <div>
        <a-card hoverable class="hotelCard">
            <a-row type="flex" align="center">
                <a-col :span="6">
                    <div>
                        <img src="@/assets/cover.jpeg" alt="hotelImg">
                    </div>
                </a-col>
                <a-col :span="8" :offset="1">
                    <a-row type="flex">
                        <span class="name-box">{{detail.name}}</span>
                        <span style="color: gold; margin-left: 10px;margin-top: 7px" v-if="detail.hasOrderedBefore">
                            预定过的酒店
                        </span>
                    </a-row>
                    <a-row type="flex" align="bottom" style="margin-top: 5px">
                        <span class="assess-num-box" style="margin-right: 10px;">200条评价</span>

                        <span class="desc-box">早餐</span>
                        <a-divider type="vertical"></a-divider>
                        <span class="desc-box">叫醒服务</span>
                    </a-row>
                    <a-row type="flex" align="bottom" style="margin-top: 3px">
                        <span class="text-box">星级</span>
                        <div class="symbol-box">
                            <a-rate :value="hotelStar" disabled allowHalf/>
                        </div>
                    </a-row>
                    <a-row type="flex" align="bottom" style="margin-top: 5px">
                        <span class="text-box">好评率</span>
                        <div class="symbol-box" style="width: 130px;">
                            <a-progress :stroke-color="{
                                '0%': '#108ee9',
                                '100%': '#87d068',
                              }" :percent="detail.rate*20" status="active" /></div>
                    </a-row>
                    <a-row type="flex" align="bottom" style="margin-top: 5px">
                        <span>南京市鼓楼区珠江路268号</span>
                    </a-row>
                </a-col>
                <a-col :span="7" :offset="2">
                    <a-row style="margin-top: 40px;">
                        <span style="color: #18a581">
                        ¥
                    </span>
                        <span class="price-box">
                        256
                    </span>
                        <span>
                        起 >
                    </span>
                    </a-row>
                    <a-row type="flex" align="bottom" style="margin-top: 5px">
                        <a-tag color="#18a581">
                            满300减50
                        </a-tag>
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
            }
        }
    }
</script>

<style scoped lang="less">
    .hotelCard {
        margin-top: 15px;
        margin-left: 20px;
        min-width: 180px;
        max-height: 250px;
        img {
            height: 150px;
            width: 150px;
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
</style>
