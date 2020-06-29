<template>
    <div class="header">
        <div class="label">
            <img src="@/assets/logo.svg" class="logo" alt="logo" @click="jumpToHome">
            <span class="title">NJUSE 酒店管理系统</span>
        </div>

<!--        <a-menu v-model="current" mode="horizontal" theme="light" style="height: 50px;">-->
<!--            <a-menu-item key="1" @click="selectMenu" v-if="userInfo.userType=='Client'">-->
<!--                <router-link to="/hotel/hotelList">-->
<!--                    <a-icon type="home" />酒店-->
<!--                </router-link>-->
<!--            </a-menu-item>-->
<!--            <a-menu-item key="2" @click="jumpToUserInfo" v-if="userInfo.userType=='Client'">-->
<!--                <a-icon type="user"/>-->
<!--                个人中心-->
<!--            </a-menu-item>-->
<!--            <a-menu-item key="3" @click="getMsg" v-if="userInfo.userType=='Client'">-->
<!--                <a-icon type="user" />消息-->
<!--            </a-menu-item>-->
<!--            <a-menu-item key="4" @click="selectMenu" v-if="userInfo.userType=='HotelManager'">-->
<!--                <router-link :to="{ name: 'manageHotel'}">-->
<!--                    <a-icon type="switcher"/>-->
<!--                    酒店管理-->
<!--                </router-link>-->
<!--            </a-menu-item>-->
<!--            <a-menu-item key="5" @click="selectMenu" v-if="userInfo.userType=='Manager'">-->
<!--                <router-link :to="{ name: 'manageUser'}">-->
<!--                    <a-icon type="user"/>-->
<!--                    账户管理-->
<!--                </router-link>-->
<!--            </a-menu-item>-->
<!--            <a-menu-item key="6" @click="selectMenu" v-if="userInfo.userType=='Operator'">-->
<!--                <router-link :to="{ name: 'manageOrder'}">-->
<!--                    <a-icon type="user" />信用判定-->
<!--                </router-link>-->
<!--            </a-menu-item>-->
<!--            <a-menu-item key="7" @click="selectMenu" v-if="userInfo.userType=='Operator'">-->
<!--                <router-link :to="{ name: 'creditList'}">-->
<!--                    <a-icon type="user" />信用变更记录-->
<!--                </router-link>-->
<!--            </a-menu-item>-->
<!--            <a-menu-item key="8" @click="selectMenu" v-if="userInfo.userType=='Operator'">-->
<!--                <router-link :to="{ name: 'proposalCurve'}">-->
<!--                    <a-icon type="user" />推广度曲线-->
<!--                </router-link>-->
<!--            </a-menu-item>-->
<!--            <a-sub-menu @click="selectMenu"  v-if="userInfo.userType=='Operator'">-->
<!--                <span slot="title"><a-icon type="appstore" /><span>消息</span></span>-->
<!--                <a-menu-item key="9">-->
<!--                    <router-link :to="{ name: 'userChatList'}">-->
<!--                        <a-icon type="user" />问题-->
<!--                    </router-link>-->
<!--                </a-menu-item>-->
<!--                <a-menu-item key="10">-->
<!--                    <router-link :to="{ name: 'msgForGroup'}">-->
<!--                        <a-icon type="user" />发送消息-->
<!--                    </router-link>-->
<!--                </a-menu-item>-->
<!--            </a-sub-menu>-->
<!--        </a-menu>-->

        <a-tooltip placement="bottom" v-if="userInfo.userType=='Client'">
            <template slot="title">
                <span>推送消息</span>
            </template>
            <a-badge :dot="dot" class="noti-box">
                <a-icon type="bell"/>
            </a-badge>
        </a-tooltip>

        <div class="logout">
            <a-dropdown placement="bottomCenter">
                <div class="user" v-if="f5">
                    <a-avatar :src="avatar_url" v-if="userInfo.userType==='Client'"></a-avatar>
                    <span style="font-size: 14px">{{ userInfo.userName }}</span>
                    <a-icon style="margin-left: 3px; font-size: 16px" type="down"></a-icon>
                </div>
                <a-menu slot="overlay">
                    <!--                <a-menu-item  @click="jumpToHome()">-->
                    <!--                    <a-icon type="home"></a-icon>-->
                    <!--                    首页-->
                    <!--                </a-menu-item>-->
                    <!--                <a-menu-item @click="jumpToUserInfo()">-->
                    <!--                    <a-icon type="profile"></a-icon>-->
                    <!--                    我的信息-->
                    <!--                </a-menu-item>-->
                    <a-menu-item @click="quit()">
                        <a-icon type="poweroff"></a-icon>
                        退出登录
                    </a-menu-item>
                </a-menu>
            </a-dropdown>
        </div>

        <MsgAffix v-show="userInfo.userType=='Client'"></MsgAffix>
    </div>



</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex';
import MsgAffix from '../views/user/components/MsgAffix';
import ARow from "ant-design-vue/es/grid/Row";
import ACol from "ant-design-vue/es/grid/Col";

export default {
    name: '',
    data() {
        return {
            current: ['1'],
            avatar_url: '',
            f5: true
        }
    },
    components: {ACol, ARow, MsgAffix },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo',
            'userOrderList'
        ]),
        dot() {
            return true;
        },
    },
    mounted() {
        this.avatar_url = "https://supernatural.oss-cn-beijing.aliyuncs.com/" + this.userInfo.avatar_url
    },
    methods: {
        ...mapMutations([
            'set_hasShownNoti'
        ]),
        ...mapActions([
            'logout',
            'getUserInfo'
        ]),
        selectMenu(v) {
        },
        async quit() {
            this.set_hasShownNoti(false)
            await this.$store.dispatch('logout')
            this.$router.push(`/login?redirect=${this.$route.fullPath}`)
        },
        jumpToHome() {

        },

    },
    watch:{
        userInfo(val){
            console.log('watch里的userInfo')
            console.log(this.userInfo)
            this.avatar_url = "https://supernatural.oss-cn-beijing.aliyuncs.com/" + this.userInfo.avatar_url
            this.f5=false
            console.log('watch关闭')
            this.f5=true
            console.log('watch开启')
        }
    }
}
</script>
<style scoped lang="less">
    .header {
        display: flex;
        line-height: 50px;
        height: 59px;
        width: 100%;
        align-items: center;
        justify-content: space-between;
        min-width: 800px;
        background-color: #ffffff;
        padding: 10px 20px 0 20px;

        .label {
            height: 50px;
            line-height: 44px;
            display: flex;
            align-items: center;
            min-width: 200px;

            .logo {
                width: 8%;
                vertical-align: top;
                margin-right: 16px;
                border-style: none;
                cursor: pointer;
            }

            .title {
                font-size: 30px;
                color: rgba(0, 0, 0, .85);
                font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
                font-weight: 500;
                position: relative;
                top: 2px;
            }

            /*.search {*/
            /*    width: 300px;*/
            /*    margin-left: 30px*/
            /*}*/
        }

        .noti-box {
            margin-left: 28%;
            text-align: right;
        }

        .logout {
            /*margin-right: 10px;*/
            /*width: 30%;*/
            /*margin-left: 10px;*/

            .user {
                cursor: pointer;
                display: flex;
                align-items: center;

                span {
                    margin-left: 5px
                }
            }
        }

    }
</style>
<style lang="less">
    .header {
        .ant-menu {
            background: none
        }
    }
</style>
