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
                    <br><br>
                    <a-list item-layout="vertical" size="large" :data-source="tmpHotelList">
                        <a-pagination slot="pagination" showQuickJumper :total="tmpHotelList.totalElements"
                                      :defaultCurrent="1" @change="pageChange"></a-pagination>
                        <a-list-item slot="renderItem" :key="index" slot-scope="item,index"
                                     @click="jumpToDetails(item.id)" style="width: 100%">
                            <img slot="extra" height="200" alt="logo" src="@/assets/cover.jpeg"/>
                            <a-list-item-meta>
                                <template slot="title">{{item.name}}</template>
                                <template slot="description">
                                    <div v-if="item.hasOrderedBefore">预订过</div>
                                    <div v-else>未预订过</div>
                                    <div>
                                        <a-rate style="font-size: 15px" :value="item.rate" disabled allowHalf/>
                                        {{item.rate}}分
                                    </div>
                                    <br>
                                    <div>
                                        <a-tag v-for="(val,i) in item.couponNames">
                                            {{val}}
                                        </a-tag>
                                    </div>
                                </template>
                            </a-list-item-meta>
                        </a-list-item>
                    </a-list>
                </a-spin>
            </a-layout-content>
        </a-layout>
    </div>
</template>

<script>
	import {mapGetters, mapActions, mapMutations} from 'vuex'
	import moment from 'moment'

	export default {
		name: 'home',
		components: {},
		data() {
			return {
				tmpHotelList: [],
				hotelListByBizRegion: [],
				hotelListByStar: [],
				hotelListByDate: []
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
				'hotelListLoading'
			])
		},
		methods: {
			...mapMutations([
				'set_hotelListParams',
				'set_hotelListLoading'
			]),
			...mapActions([
				'getHotelList',
				'getHotelByDate'
			]),
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
			}
		}
	}
</script>
<style scoped lang="less">
</style>