<template>
    <div style="margin-top: 20px;">
        <a-row class="header">
            <a-button type="primary" @click="goBack"> <a-icon type="arrow-left" />返回列表</a-button>
            <a-popconfirm
                    title="您确定结束与客服的聊天吗？"
                    ok-text="确认"
                    cancel-text="取消"
                    @confirm="confirmDone"
                    @cancel="cancel"
            >
                <a-button type="primary" style="margin-left: 10px" v-if="this.userInfo.userType === 'Client' && currentQuesStatus!='2'">
                    <a-icon type="check" />确认结束</a-button>
            </a-popconfirm>
            <a-button type="primary" shape="circle" icon="redo" style="margin-left: 10px" @click="redo"/>
        </a-row>

        <div id="scroll">
            <a-card class="chat-container" style="margin-bottom: 30px">
                <div
                        class="demo-infinite-container"
                        id="chat-scroll"
                        :infinite-scroll-disabled="busy"
                        :infinite-scroll-distance="10">

                    <div
                            v-for="(item,index) in showRecords"
                            :key="index">
                        <a-timeline mode="right" v-if="item.id == userId">
                            <div v-for="(rec, idx) in item.records" :key="idx">
                                <a-timeline-item color="grey">
                                    <a-icon slot="dot" :type="myAvatar" style="font-size: 30px;" />
                                    <div style="text-align: right;">
                                        <div class="u-name-box">
                                            我
                                        </div>
                                        <div class="u-time-box">
                                            <span>
                                                {{rec.sendTime}}
                                                <a-icon type="clock-circle-o" class="time-box" ></a-icon>
                                            </span>
                                        </div>
                                        <a-card class="u-content-box">
                                            {{rec.content}}
                                        </a-card>
                                    </div>
                                </a-timeline-item>
                            </div>
                        </a-timeline>

                        <a-timeline style="margin-top: 5px" v-else>
                            <div v-for="(rec, idx) in item.records" :key="idx">
                                <a-timeline-item color="green">
                                    <a-icon slot="dot" :type="otherAvatar" style="font-size: 30px;" />
                                    <div class="o-name-box">
                                        {{otherName}}
                                    </div>
                                    <div class="o-time-box">
                                        <span>
                                            <a-icon type="clock-circle-o" class="time-box" ></a-icon>
                                            {{rec.sendTime}}
                                        </span>
                                    </div>
                                    <a-card class="o-content-box">
                                        {{rec.content}}
                                    </a-card>
                                </a-timeline-item>
                            </div>
                        </a-timeline>
                    </div>

                </div>
            </a-card>

        </div>
        <a-card class="chat-container" v-if="currentQuesStatus!='2'">
            <a-textarea
                    :placeholder="placeholder"
                    :rows="6"
                    v-model="content"></a-textarea>
            <a-button type="primary" class="btn-box" @click="sendMsg">
                发送
            </a-button>
        </a-card>
    </div>
</template>

<script>
    import infiniteScroll from 'vue-infinite-scroll';
    import { mapGetters,mapActions } from 'vuex'
    import ARow from "ant-design-vue/es/grid/Row";
    import { message } from 'ant-design-vue'

    export default {
        name: "ChatBox",
        components: {ARow},
        directives: { infiniteScroll },
        data() {
            return {
                loading: false,
                busy: false,
                showRecords: [],
                myAvatar: '',
                otherName: '',
                otherAvatar: '',
                placeholder: '',
                content: '',
            }
        },
        computed: {
            ...mapGetters([
                'quesRecords',
                'userId',
                'userInfo',
                'currentQuesId',
                'currentQuesStatus',
            ])
        },
        methods: {
            ...mapActions([
                'getQuesDetail',
                'chat',
                'changeMessageStatus'
            ]),
            goBack() {
                if(this.userInfo.userType === "Operator") {
                    this.$router.push({ name: 'userChatList'})
                } else {
                    this.$router.push({ name: 'opChatList'})
                }
            },
            async redo() {
                this.showRecords = [];
                await this.preRecords();
                this.goDown();
            },
            goDown() {
                this.$nextTick(() => {
                    let outer = document.getElementById('scroll'); // 获取对象
                    outer.scrollTop = outer.scrollHeight;// 滚动高度
                    let inner = document.getElementById('chat-scroll');
                    inner.scrollTop = inner.scrollHeight;
                })
            },
            sendMsg() {
                if(this.userInfo.userType === "Operator") {
                    this.chat({
                        content: this.content,
                        clientId: this.quesRecords[0].from,
                        retMsgId: this.quesRecords[0].id
                    }).then(() => {
                        this.content = '';
                        this.redo()
                    })
                } else {
                    this.chat({
                        content: this.content,
                        clientId: this.quesRecords[0].from,
                        retMsgId: this.quesRecords[0].id
                    }).then(() => {
                        this.content = '';
                        this.redo();
                    })
                }
            },
            async preRecords() {
                await this.getQuesDetail();
                // console.log("ques-----------------", this.quesRecords[0].from)
                let preId = this.quesRecords[0].from;
                let tempArr = [this.quesRecords[0]];
                for(let i = 1; i < this.quesRecords.length; i++) {
                    if(this.quesRecords[i].from === preId){
                        tempArr.push(this.quesRecords[i]);
                    } else {
                        this.showRecords.push({
                            id: preId,
                            records: tempArr
                        });
                        preId = this.quesRecords[i].from
                        tempArr = [this.quesRecords[i]]
                    }
                }
                this.showRecords.push({
                    id: preId,
                    records: tempArr
                });
            },
            confirmDone() {
                this.changeMessageStatus({
                    id: this.currentQuesId,
                    status: '2'
                }).then(() => {
                    if(this.userInfo.userType === "Client"){
                        this.$router.push({ name: 'opChatList', params: { userType: this.userInfo.userType } });
                    }
                    message.success("感谢您的支持！")
                })
            }
        },
        async mounted() {
            await this.preRecords();

            this.goDown();

            if(this.userInfo.userType === "Operator"){
                this.myAvatar = "customer-service";
                this.otherName = this.quesRecords[0].fromName;
                this.otherAvatar = "user";
                this.placeholder = '填写回复';
            } else {
                this.myAvatar = "user";
                this.otherName = "客服人员";
                this.otherAvatar = "customer-service";
                this.placeholder = '如需反馈或补充，请在此处填写'
            }

            console.log("status-------------------------",this.currentQuesStatus)
        },
    }
</script>

<style scoped>
    .header {
        margin: 10px 5px;
    }
    .demo-infinite-container {
        overflow: auto;
        padding: 8px 24px;
        height: 400px;
    }
    .chat-container {
        border-style: solid;
        border-color: rgba(24,165,129,0.2);
        border-radius: 20px 20px 20px 20px;
        /*text-align: right;*/
    }
    .btn-box {
        margin-top: 10px;
        text-align: center;
    }
    .o-name-box {
        padding-top: 10px;
        margin-left: 10px;
        font-weight: 500;
    }
    .o-time-box {
        font-size: 13px;
        margin: 5px 10px;
        color: grey;
    }
    .o-content-box {
        margin-left: 10px;
        width: 40%;
        box-shadow:4px 4px 3px #999;
    }
    .u-name-box {
        padding-top: 10px;
        margin-right: 10px;
        font-weight: 500;
    }
    .u-time-box {
        font-size: 13px;
        margin: 5px 10px;
        color: grey;
    }
    .u-content-box {
        margin-left: 59%;
        width: 40%;
        box-shadow:4px 4px 5px #999;
        text-align: left;
    }

</style>
