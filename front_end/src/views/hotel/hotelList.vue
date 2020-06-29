<template>
    <div>
        <SearchCard></SearchCard>

        <a-layout>
            <a-layout-content style="min-width: 800px">
                <a-row>
                    <a-col :span="7">
                        <BroadcastCard></BroadcastCard>
                    </a-col>
                    <a-col :span="17">
                        <a-spin :spinning="spinning" :delay="500">
                            <div v-for="(item, index) in showHotelList.slice(thisPage[0], thisPage[1])" :key="index" v-if="showHotelList.length > 0">
                                <div @click="jumpToDetails(item.id)">
                                    <HotelCard :detail="item"></HotelCard>
                                </div>
                            </div>
                            <div v-if="showHotelList.length === 0">
                                <a-result status="404" title="未找到合适的酒店" sub-title="搜索其他酒店？">
                                    <template #extra>
                                        <a-button key="console" type="primary">
                                            为您推荐
                                        </a-button>
                                    </template>
                                </a-result>
                            </div>
                        </a-spin>
                        <div style="margin-top: 20px; text-align: right">
                            <a-pagination
                                    show-quick-jumper
                                    :total="showHotelList.length"
                                    :show-total="total => `共 ${total} 家酒店`"
                                    :default-current="1"
                                    :item-render="itemRender"
                                    @change="pageChange" />
                        </div>
                    </a-col>
                </a-row>

                <a-back-top />

<!--                <a-list item-layout="vertical" size="large" :data-source="tmpHotelList">-->
<!--                    <a-pagination slot="pagination" showQuickJumper :total="tmpHotelList.totalElements"-->
<!--                                  :defaultCurrent="1" @change="pageChange"></a-pagination>-->
<!--                    <a-list-item slot="renderItem" :key="index" slot-scope="item,index"-->
<!--                                 @click="jumpToDetails(item.id)" style="width: 100%">-->
<!--                        <img slot="extra" height="200" alt="logo" src="@/assets/cover.jpeg"/>-->
<!--                        <a-list-item-meta>-->
<!--                            <template slot="title">{{item.name}}</template>-->
<!--                            <template slot="description">-->
<!--                                <div v-if="item.hasOrderedBefore">预订过</div>-->
<!--                                <div v-else>未预订过</div>-->
<!--                                <div>-->
<!--                                    星级：<br><a-rate style="font-size: 15px" :value="item.rate" disabled allowHalf/>-->
<!--                                    <br>-->
<!--                                    好评率：<div style="width: 130px">-->
<!--                                    <a-progress :stroke-color="{-->
<!--        '0%': '#108ee9',-->
<!--        '100%': '#87d068',-->
<!--      }" :percent="item.rate*20" status="active" />-->

<!--                                </div>-->
<!--                                </div>-->
<!--                                <br>-->
<!--                                <div>-->
<!--                                    <a-tag v-for="(val,i) in item.couponNames">-->
<!--                                        {{val}}-->
<!--                                    </a-tag>-->
<!--                                </div>-->
<!--                            </template>-->
<!--                        </a-list-item-meta>-->
<!--                    </a-list-item>-->
<!--                </a-list>-->


            </a-layout-content>
        </a-layout>
    </div>
</template>

<script>
	import {mapGetters, mapActions, mapMutations} from 'vuex';
	import HotelCard from './components/hotelCard';
	import BroadcastCard from './components/broadcastCard';
	import SearchCard from './components/searchCard';
    import ARow from "ant-design-vue/es/grid/Row";

	export default {
		name: 'home',
		components: {SearchCard, ARow, HotelCard, BroadcastCard },
		data() {
			return {
                emptyBox: [{ name: 'box1' }, { name: 'box2'}, {name: 'box3'}],
                msg:'订单价格三倍积分，积分当钱花。可享9折，价格更优惠。提早入住，延迟退房，入住更随心。生日惊喜好礼，礼券更丰富。',
                thisPage: [0, 8],
			}
		},
		async mounted() {
			await this.getHotelCardInfos();
            // console.log('before Notification',this.hasShownNoti)
            // this.showNotification()
            // console.log('after Notification',this.hasShownNoti)
		},
		computed: {
			...mapGetters([
				'userId',
                'showHotelList',
                'hasShownNoti',
				'hotelListLoading',
                'spinning'
			]),
		},
		methods: {
			...mapMutations([
				'set_hotelListParams',
				'set_hotelListLoading',
                'set_hasShownNoti',
                'set_showHotelList',
			]),
			...mapActions([
				'getHotelCardInfos',
				'getHotelByDate'
			]),
            // showNotification() {
            //     if (this.hasShownNoti===false) {
            //         this.openNotification('bottomLeft')
            //         this.set_hasShownNoti(true)
            //     }
            // },
			pageChange(page, pageSize) {
				this.thisPage = [(page - 1) * 8, page * 8]
			},
			jumpToDetails(id) {
				this.$router.push({name: 'hotelDetail', params: {hotelId: id}})
			},
            itemRender(current, type, originalElement) {
                if (type === 'prev') {
                    return <a>上一页</a>;
                } else if (type === 'next') {
                    return <a>下一页</a>;
                }
                return originalElement;
            },
		}
	}
</script>
<style scoped lang="less">
    .hotelList {
        text-align: center;
        padding: 50px 0;
        .emptyBox {
            height: 0;
            margin: 10px 10px
        }
        .card-wrapper{
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
            flex-grow: 3;
            min-height: 800px
        }
        .card-wrapper .card-item {
            margin: 30px;
            position: relative;
            height: 188px;
        }
    }
</style>
