<template>
    <a-layout>
        <a-layout-content>
            <div class="hotelDetailCard">
                <h1>
                    {{ currentHotelInfo.title }}
                </h1>
                <div class="hotel-info">
                    <a-card style="width: 240px">
                        <img
                                alt="example"
                                src="@/assets/cover.jpeg"
                                slot="cover"
                                referrerPolicy="no-referrer"
                        />
                    </a-card>
                    <div class="info">
                        <div class="items" v-if="currentHotelInfo.name">
                            <span class="label">酒店名称：</span>
                            <span class="value">{{ currentHotelInfo.name }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.address">
                            <span class="label">地址</span>
                            <span class="value">{{ currentHotelInfo.address }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.rate">
                            <span class="label">评分:</span>
                            <span class="value">{{ currentHotelInfo.rate }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">星级:</span>
                            <a-rate style="font-size: 15px" :value="currentHotelInfo.rate" disabled allowHalf/>
                        </div>
                        <div class="items" v-if="currentHotelInfo.description">
                            <span class="label">酒店简介:</span>
                            <span class="value">{{ currentHotelInfo.description }}</span>
                        </div>
                        <div class="items">
                            <span class="label">酒店服务：</span>
                            <span class="value">
                                <template v-for="tag in tags">
                                <a-tag :key="tag.tagName" >
                                    {{tag.tagName}}
                                </a-tag>
                            </template>
                            </span>
                        </div>
                        <div class="items">
                            <span class="label">是否预订过:</span>
                            <span class="value">
                                <div v-if="currentHotelInfo.hasOrderedBefore">预订过</div>
                                <div v-else>未预订过</div>
                            </span>
                        <div>

                        </div>
                    </div>
                    </div>
                </div>
                <a-divider></a-divider>
                <a-tabs>
                    <a-tab-pane tab="房间信息" key="1">
                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
                    </a-tab-pane>
                    <a-tab-pane tab="酒店详情" key="2">

                    </a-tab-pane>
                    <a-tab-pane tab="酒店评价" key="3">
                        <a-list
                            class="comment-list"
                            :header="`${comment.length} comments`"
                            item-layout="horizontal"
                            :data-source="comment"
                        >
                            <a-list-item slot="renderItem" slot-scope="item">
                                <a-comment :author="item.userName" avatar="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png">
                                    <template slot="actions" v-if="userInfo.userType==='HotelManager'&&(item.reply==='')">
                                        <span @click="showReply(item)">{{ actions }}</span>
                                        <a-modal :visible="visible" title="回复评价" cancelText="取消" okText="确定" @cancel="cancel" @ok="handleSubmit(item)">
                                            <a-textarea placeholder="请输入回复" v-model="reply"></a-textarea>
                                        </a-modal>
                                    </template>
                                    <p slot="content">
                                        <a-rate :default-value="item.commentValue" disabled /><br/><br/>
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
            </div>
        </a-layout-content>
    </a-layout>
</template>
<script>
	import {mapGetters, mapActions, mapMutations} from 'vuex'
	import RoomList from './components/roomList'

	export default {
		name: 'hotelDetail',
		components: {
			RoomList,
		},
		data() {
			return {
                        actions: 'reply to',
                        visible: false,
                        reply: ''
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
            await this.getHotelById(Number(this.userId)),
            await this.getAllTags(this.currentHotelId),
            console.log('currentHotelId',this.currentHotelId)
            await this.getCommentByHotelId(Number(this.$route.params.hotelId))
            console.log(this.userInfo)
        },
		beforeRouteUpdate(to, from, next) {
			this.set_currentHotelId(Number(to.params.hotelId))
			this.getHotelById(Number(this.userId))
			next()
		},
		methods: {
			...mapMutations([
				'set_currentHotelId',
			]),
			...mapActions([
                'getHotelById',
                'getAllTags',
                'getCommentByHotelId',
                'updateReply'
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
</style>
