<template>
    <a-layout>
        <a-layout-content style="width:1000px;margin:auto">
            <a-row style="margin-top: 40px">
                <a-col :span="12" >
                    <a-carousel autoplay>
                        <div v-for="item in this.photos" style="width:550px;height:300px">
                            <img :src="item.url" style="width:550px;height:300px"/>
                        </div>
<!--                        <div><h3>1</h3></div>-->
<!--                        <div><h3>2</h3></div>-->
<!--                        <div><h3>3</h3></div>-->
<!--                        <div><h3>4</h3></div>-->
                    </a-carousel>
                    <a-tabs style="margin:auto">
                        <a-tab-pane tab="房间信息" key="1">
                            <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
                        </a-tab-pane>
                    </a-tabs>

                </a-col>
                <a-col :span="12" >
<!--                    <h1>-->
<!--                        {{ currentHotelInfo.title }}-->
<!--                    </h1>-->
                <a-card style="width:350px;margin:auto;height:550px;">
<!--                    <div class="hotel-info">-->
                    <div>
                        <div class="info">
                            <div class="items" v-if="currentHotelInfo.name">
                                <div class="label">名称：</div>
                                <div class="value">{{ currentHotelInfo.name }}</div>
                            </div>
                            <div class="items" v-if="currentHotelInfo.address">
                                <div class="label">地址:</div>
                                <div class="value">{{ currentHotelInfo.address }}</div>
                            </div>
                            <div class="items" v-if="currentHotelInfo.rate">
                                <div class="label">评分:</div>
                                <div class="value">{{ currentHotelInfo.rate }}</div>
                            </div>
                            <div class="items" v-if="currentHotelInfo.hotelStar">
                                <div class="label">星级:</div>
                                <a-rate style="font-size: 15px;margin-left:10px" :value="currentHotelInfo.rate" disabled allowHalf/>
                            </div>
                            <div class="items" v-if="currentHotelInfo.description">
                                <div class="label">简介:</div>
                                <div class="value">{{ currentHotelInfo.description }}</div>
                            </div>
                            <div class="items">
                                <div class="label">设施服务：</div>
                                <div class="value" style="text-align: left">
                                <template v-for="tag in tags">
                                <a-tag :key="tag.tagName" color="#1DA57A">
                                    {{tag.tagName}}
                                </a-tag>
                            </template>
                            </div>
                            </div>
                            <div class="items">
<!--                                <span class="label">是否预订过:</span>-->
                                <div class="label">
                                    <div v-if="currentHotelInfo.hasOrderedBefore">预订过</div>
                                    <div v-else>未预订过</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </a-card>

                </a-col>
            </a-row>
            <a-row style="width:1000px;margin:auto;margin-top: 50px">
                <a-tabs>
                    <a-tab-pane tab="酒店评价" key="3">
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
                                        <a-modal :visible="visible" title="回复评价" cancelText="取消" okText="确定" @cancel="cancel" @ok="handleSubmit(item)">
                                            <a-textarea placeholder="请输入回复" v-model="reply"></a-textarea>
                                        </a-modal>
                                    </template>
                                    <p slot="content">
                                        <a-rate :default-value="item.commentValue" disabled ><a-icon slot="character" type="heart" /></a-rate><br/><br/>
                                        {{ item.commentContent }}
                                    </p>
                                    <a-comment author="酒店掌柜" avatar="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" v-if="item.reply!==''">
                                        <p slot="content">
                                            {{ item.reply }}
                                        </p>
                                    </a-comment>
                                </a-comment>
                            </a-list-item>
                        </a-list>
                    </a-tab-pane>
                </a-tabs>
            </a-row>
<!--            <div class="hotelDetailCard">-->
<!--                <h1>-->
<!--                    {{ currentHotelInfo.title }}-->
<!--                </h1>-->
<!--                <div class="hotel-info">-->
<!--                    <a-card style="width: 240px">-->
<!--                        <img-->
<!--                                alt="example"-->
<!--                                src="@/assets/cover.jpeg"-->
<!--                                slot="cover"-->
<!--                                referrerPolicy="no-referrer"-->
<!--                        />-->
<!--                    </a-card>-->
<!--                    <div class="info">-->
<!--                        <div class="items" v-if="currentHotelInfo.name">-->
<!--                            <span class="label">酒店名称：</span>-->
<!--                            <span class="value">{{ currentHotelInfo.name }}</span>-->
<!--                        </div>-->
<!--                        <div class="items" v-if="currentHotelInfo.address">-->
<!--                            <span class="label">地址</span>-->
<!--                            <span class="value">{{ currentHotelInfo.address }}</span>-->
<!--                        </div>-->
<!--                        <div class="items" v-if="currentHotelInfo.rate">-->
<!--                            <span class="label">评分:</span>-->
<!--                            <span class="value">{{ currentHotelInfo.rate }}</span>-->
<!--                        </div>-->
<!--                        <div class="items" v-if="currentHotelInfo.hotelStar">-->
<!--                            <span class="label">星级:</span>-->
<!--                            <a-rate style="font-size: 15px" :value="currentHotelInfo.rate" disabled allowHalf/>-->
<!--                        </div>-->
<!--                        <div class="items" v-if="currentHotelInfo.description">-->
<!--                            <span class="label">酒店简介:</span>-->
<!--                            <span class="value">{{ currentHotelInfo.description }}</span>-->
<!--                        </div>-->
<!--                        <div class="items">-->
<!--                            <span class="label">酒店服务：</span>-->
<!--                            <span class="value" style="text-align: left">-->
<!--                                <template v-for="tag in tags">-->
<!--                                <a-tag :key="tag.tagName" style="font-size: medium" color="#abc2e8">-->
<!--                                    {{tag.tagName}}-->
<!--                                </a-tag>-->
<!--                            </template>-->
<!--                            </span>-->
<!--                        </div>-->
<!--                        <div class="items">-->
<!--                            <span class="label">是否预订过:</span>-->
<!--                            <span class="value">-->
<!--                                <div v-if="currentHotelInfo.hasOrderedBefore">预订过</div>-->
<!--                                <div v-else>未预订过</div>-->
<!--                            </span>-->
<!--                        <div>-->

<!--                        </div>-->
<!--                    </div>-->
<!--                    </div>-->
<!--                </div>-->
<!--                <a-divider></a-divider>-->
<!--                <a-tabs>-->
<!--                    <a-tab-pane tab="房间信息" key="1">-->
<!--                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>-->
<!--                    </a-tab-pane>-->
<!--&lt;!&ndash;                    <a-tab-pane tab="酒店评价" key="3">&ndash;&gt;-->
<!--&lt;!&ndash;                        <a-list&ndash;&gt;-->
<!--&lt;!&ndash;                            class="comment-list"&ndash;&gt;-->
<!--&lt;!&ndash;                            :header="`${comment.length} 评价`"&ndash;&gt;-->
<!--&lt;!&ndash;                            item-layout="horizontal"&ndash;&gt;-->
<!--&lt;!&ndash;                            :data-source="comment"&ndash;&gt;-->
<!--&lt;!&ndash;                        >&ndash;&gt;-->
<!--&lt;!&ndash;                            <a-list-item slot="renderItem" slot-scope="item">&ndash;&gt;-->
<!--&lt;!&ndash;                                <a-comment :author="item.userName" :avatar="'https://supernatural.oss-cn-beijing.aliyuncs.com/'+item.avatar">&ndash;&gt;-->
<!--&lt;!&ndash;                                    <template slot="actions" v-if="userInfo.userType==='HotelManager'&&(item.reply==='')">&ndash;&gt;-->
<!--&lt;!&ndash;                                        <span @click="showReply(item)">{{ actions }}</span>&ndash;&gt;-->
<!--&lt;!&ndash;                                        <a-modal :visible="visible" title="回复评价" cancelText="取消" okText="确定" @cancel="cancel" @ok="handleSubmit(item)">&ndash;&gt;-->
<!--&lt;!&ndash;                                            <a-textarea placeholder="请输入回复" v-model="reply"></a-textarea>&ndash;&gt;-->
<!--&lt;!&ndash;                                        </a-modal>&ndash;&gt;-->
<!--&lt;!&ndash;                                    </template>&ndash;&gt;-->
<!--&lt;!&ndash;                                    <p slot="content">&ndash;&gt;-->
<!--&lt;!&ndash;                                        <a-rate :default-value="item.commentValue" disabled ><a-icon slot="character" type="heart" /></a-rate><br/><br/>&ndash;&gt;-->
<!--&lt;!&ndash;                                        {{ item.commentContent }}&ndash;&gt;-->
<!--&lt;!&ndash;                                    </p>&ndash;&gt;-->
<!--&lt;!&ndash;                                    <a-comment author="酒店掌柜" avatar="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" v-if="item.reply!==''">&ndash;&gt;-->
<!--&lt;!&ndash;                                        <p slot="content">&ndash;&gt;-->
<!--&lt;!&ndash;                                            {{ item.reply }}&ndash;&gt;-->
<!--&lt;!&ndash;                                        </p>&ndash;&gt;-->
<!--&lt;!&ndash;                                    </a-comment>&ndash;&gt;-->
<!--&lt;!&ndash;                                </a-comment>&ndash;&gt;-->
<!--&lt;!&ndash;                            </a-list-item>&ndash;&gt;-->
<!--&lt;!&ndash;                        </a-list>&ndash;&gt;-->
<!--&lt;!&ndash;                    </a-tab-pane>&ndash;&gt;-->
<!--                </a-tabs>-->
<!--            </div>-->
        </a-layout-content>
    </a-layout>
</template>
<script>
	import {mapGetters, mapActions, mapMutations} from 'vuex'
	import RoomList from './components/roomList'
  import {
	    getPicsAPI
	} from "../../api/hotelManager";

  export default {
		name: 'hotelDetail',
		components: {
			RoomList,
		},
		data() {
			return {
        actions: 'reply to',
        visible: false,
        reply: '',
        photos:[]
      }
		},
		computed: {
			...mapGetters([
				'userId',
        'currentHotelId',
        'currentHotelInfo',
        'tags',
        'comment',
        'userInfo'
			])
		},
        async mounted() {
            await this.set_currentHotelId(Number(this.$route.params.hotelId))
            await this.getHotelById(this.userId),
            //await this.getHotelByIdLJY()
            await this.getAllTags(this.currentHotelId),
            console.log('currentHotelId',this.currentHotelId)
            await this.getCommentByHotelId(this.currentHotelId)
            console.log(this.userInfo)
            await this.getPhotos()

        },
		beforeRouteUpdate(to, from, next) {
			this.set_currentHotelId(Number(to.params.hotelId))
			// this.getHotelById(Number(this.userId))
        this.getHotelByIdLJY()
			next()
		},
		methods: {
			...mapMutations([
				'set_currentHotelId',
			]),
			...mapActions([
                'getHotelByIdLJY',
                'getAllTags',
                'getCommentByHotelId',
                'updateReply',
                'getHotelById'
			]),
			showReply(item){
                        console.log('item:')
                        console.log(item)
                        this.visible=true
                        console.log()
                    },
      cancel(){
        this.visible=false
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
        this.visible=false
        this.reply=''
    },
      async getPhotos(){
        const res=await getPicsAPI(this.currentHotelId)
        console.log("getPicsAPI",res)
        this.photos=res
        console.log("this.photos",this.photos)
      }
		}
	}
</script>
<style scoped lang="less">
    .hotelDetailCard {
        padding: 50px 50px;
    }

    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;

        .info {
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;

            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;

                .label {
                    margin-right: 10px;
                    font-size: 18px;
                }

                .value {
                    margin-right: 15px
                }
            }
        }
    }

    .label {
        margin-right: 10px;
        margin-left:10px;
        font-size: 22px;
        margin-top:10px;
        margin-bottom: 4px;
    }

    .value {
        font-size:15px;
        margin-right: 15px;
        margin-left:10px;
        margin-top:4px;
        margin-bottom: 4px;
    }
    /*.ant-carousel >>> .slick-slide {*/
    /*    text-align: center;*/
    /*    height: 160px;*/
    /*    line-height: 160px;*/
    /*    background: #364d79;*/
    /*    overflow: hidden;*/
    /*}*/

    /*.ant-carousel >>> .slick-slide h3 {*/
    /*    color: #fff;*/
    /*}*/
</style>
