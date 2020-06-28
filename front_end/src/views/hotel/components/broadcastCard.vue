<template>
    <a-card class="broadcastCard">
        <a-carousel
                autoplay
                arrows>
            <div
                    slot="prevArrow"
                    slot-scope="props"
                    class="custom-slick-arrow"
                    style="left: 10px;zIndex: 1"
            >
                <a-icon type="left-circle" />
            </div>
            <div slot="nextArrow" slot-scope="props" class="custom-slick-arrow" style="right: 10px">
                <a-icon type="right-circle" />
            </div>
            <div v-for="item in broadcastList" :key="id" @click="getDetail">
                <div class="title-box">
                    {{item.title}}
                </div>
                <div class="content-box">
                    {{item.content}}
                </div>
            </div>
        </a-carousel>

        <a-list class="list-box" bordered :data-source="data">
            <a-list-item slot="renderItem" slot-scope="item, index" style="cursor: pointer;" @click="getDetail">
                <div>
                    <div class="s-title-box">{{item.title}}</div>
                    <div>
                        <span class="s-content-box">{{item.content}}</span>
                        <span class="for-detail">
                            了解详情>>>
                        </span>
                    </div>
                </div>
            </a-list-item>
        </a-list>
    </a-card>

</template>

<script>
    import {mapActions,mapGetters} from 'vuex'
    const data = [
        {
            title: '网站会员专属优惠',
            content: '订单价格三倍积分...'
        },
        {
            title: '网站会员专属优惠',
            content: '订单价格三倍积分...'
        },
        {
            title: '网站会员专属优惠',
            content: '订单价格三倍积分...'
        },
        {
            title: '网站会员专属优惠',
            content: '订单价格三倍积分...'
        },
    ];
    export default {
        name: "broadcastCard",
        data() {
            return {
                data
            }
        },
        methods: {
            ...mapActions([
                'getBroadcastList'
            ]),
            getDetail() {
                alert("hahahha")
            },

        },
        computed:{
            ...mapGetters([
                'broadcastList'
            ])
        },
        mounted() {
            this.getBroadcastList()
        },
        watch:{
            broadcastList(val){

            }
        }

    }
</script>

<style scoped>

    .broadcastCard {
        margin-top: 15px;
        min-width: 180px;
    }

    .ant-carousel >>> .slick-slide {
        text-align: center;
        height: 290px;
        line-height: 20px;
        background: #18a581;
        overflow: hidden;
        cursor: pointer;
    }

    .ant-carousel >>> .custom-slick-arrow {
        width: 25px;
        height: 25px;
        font-size: 25px;
        color: #fff;
        background-color: rgba(31, 45, 61, 0.11);
        opacity: 0.3;

    }
    .ant-carousel >>> .custom-slick-arrow:before {
        display: none;
    }
    .ant-carousel >>> .custom-slick-arrow:hover {
        opacity: 0.5;
    }

    .title-box {
        color: white;
        font-size: 17px;
        font-weight: bold;
        margin-top: 20px;
    }
    .content-box {
        margin: 25px 39px;
        color: white;
        font-size: 13px;
        line-height: 30px;
        text-align: left;
    }
    .list-box {
        margin-top: 20px;
    }
    .s-title-box {
        font-size: 14px;
        font-weight: bold;
        /*color: #18a581;*/
    }
    .s-content-box {
        font-size: 10px;
    }
    .for-detail {
        margin-left: 5px;
        color: #18a581;
        font-size: 10px;
    }

</style>
