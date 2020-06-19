<template>
    <div>
        <a-layout>
            <a-layout-content style="min-width: 800px">
                <a-spin :spinning="hotelListLoading">
                    <br>
                    <a-select mode="multiple" size="default" placeholder="请选择商圈" @change="onChangeBizRegion"
                              style="width: 200px">
                        <a-select-option v-for="item in ['全部','XiDan']" :key="item">{{item}}</a-select-option>
                    </a-select>
                    <a-select mode="multiple" size="default" placeholder="请选择星级" @change="onChangeStar"
                              style="width: 200px">
                        <a-select-option key="全部">全部</a-select-option>
                        <a-select-option key="One">一星</a-select-option>
                        <a-select-option key="Two">两星</a-select-option>
                        <a-select-option key="Three">三星</a-select-option>
                        <a-select-option key="Four">四星</a-select-option>
                        <a-select-option key="Five">五星</a-select-option>
                    </a-select>
                    <a-range-picker @change="onChangeDate" format="YYYY-MM-DD"/>
                </a-spin>

                <a-row>
                    <a-col :span="6">
                        <a-card></a-card>
                    </a-col>
                    <a-col :span="17" :offset="1">
                        <HotelCard></HotelCard>
                    </a-col>
                </a-row>


            </a-layout-content>
        </a-layout>
    </div>
</template>

<script>
	import {mapGetters, mapActions, mapMutations} from 'vuex';
	import moment from 'moment';
	import HotelCard from './components/hotelCard';
    import ARow from "ant-design-vue/es/grid/Row";
    import ACol from "ant-design-vue/es/grid/Col";

	export default {
		name: 'home',
		components: {ACol, ARow, HotelCard },
		data() {
			return {
				tmpHotelList: [],
				hotelListByBizRegion: [],
				hotelListByStar: [],
				hotelListByDate: [],
        emptyBox: [{ name: 'box1' }, { name: 'box2'}, {name: 'box3'}],
        msg:'订单价格三倍积分，积分当钱花。可享9折，价格更优惠。提早入住，延迟退房，入住更随心。生日惊喜好礼，礼券更丰富。',

			}
		},
		async mounted() {
			await this.getHotelList(this.userId)
			this.tmpHotelList = this.hotelList
			this.hotelListByBizRegion = this.hotelList
			this.hotelListByStar = this.hotelList
			this.hotelListByDate = this.hotelList
            console.log('before Notification',this.hasShownNoti)
            this.showNotification()
            console.log('after Notification',this.hasShownNoti)
		},
		computed: {
			...mapGetters([
				'userId',
				'hotelList',
                'hasShownNoti',
				'hotelListLoading',

			])
		},
		methods: {
			...mapMutations([
				'set_hotelListParams',
				'set_hotelListLoading',
        'set_hasShownNoti'
			]),
			...mapActions([
				'getHotelList',
				'getHotelByDate'
			]),
      showNotification() {
          if (this.hasShownNoti===false) {
              this.openNotification('bottomLeft')
              this.set_hasShownNoti(true)
          }
      },
			onChangeBizRegion(value) {
				this.hotelListByBizRegion = []
				let all = false
				if(value.length==0){
				    this.hotelListByBizRegion = this.hotelList
                }else{
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
				//console.log(this.hotelListByBizRegion)
			},
			onChangeStar(value) {
				this.hotelListByStar = []
				let all = false
				if(value.length==0){
				    this.hotelListByStar = this.hotelList
                }else{
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
				//console.log(checkInDate)
				//console.log(checkOutDate)
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
				console.log(this.tmpHotelList)
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
