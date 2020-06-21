<template>
    <div class="header">
        <div class="label">
            <img src="@/assets/logo.svg" class="logo" alt="logo" @click="jumpToHome">
            <span class="title">NJUSE 酒店管理系统</span>

        </div>
        <a-menu v-model="current" mode="horizontal" theme="light">
            <a-menu-item key="1" @click="selectMenu">
                <router-link to="/hotel/hotelList">
                    <a-icon type="home" />首页
                </router-link>
            </a-menu-item>
            <a-menu-item key="2" @click="jumpToUserInfo" v-if="userInfo.userType=='Client'">
                <a-icon type="user" />个人中心
            </a-menu-item>
            <a-menu-item key="3" @click="getMsg" v-if="userInfo.userType=='Client'">
                <a-icon type="user" />消息
            </a-menu-item>
            <a-menu-item key="4" @click="selectMenu" v-if="userInfo.userType=='HotelManager'">
                <router-link :to="{ name: 'manageHotel'}">
                     <a-icon type="switcher" />酒店管理
                </router-link>
            </a-menu-item>
            <a-menu-item key="5" @click="selectMenu" v-if="userInfo.userType=='Admin'">
                <router-link :to="{ name: 'manageUser'}">
                     <a-icon type="user" />账户管理
                </router-link>
            </a-menu-item>
            <a-menu-item key="6" @click="selectMenu" v-if="userInfo.userType=='Operator'">
                <router-link :to="{ name: 'manageOrder'}">
                    <a-icon type="user" />信用判定
                </router-link>
            </a-menu-item>
            <a-menu-item key="7" @click="selectMenu" v-if="userInfo.userType=='Operator'">
                <router-link :to="{ name: 'creditList'}">
                    <a-icon type="user" />信用变更记录
                </router-link>
            </a-menu-item>
            <a-sub-menu @click="selectMenu"  v-if="userInfo.userType=='Operator'">
                <span slot="title"><a-icon type="appstore" /><span>消息</span></span>
                <a-menu-item key="8">
                    <router-link :to="{ name: 'userChatList'}">
                        <a-icon type="user" />问题
                    </router-link>
                </a-menu-item>
                <a-menu-item key="9">
                    <router-link :to="{ name: 'msgForGroup'}">
                        <a-icon type="user" />发送消息
                    </router-link>
                </a-menu-item>
            </a-sub-menu>
        </a-menu>

        <div class="logout">
            <a-dropdown placement="bottomCenter">
                <div class="user">
                    <a-avatar src="./defaultAvatar.png"></a-avatar>
                    <span style="font-size: 14px">{{ userInfo.userName }}</span>
                    <a-icon style="margin-left: 3px; font-size: 16px" type="down"></a-icon>
                </div>
                <a-menu slot="overlay">
                <a-menu-item  @click="jumpToHome()">
                    <a-icon type="home"></a-icon>
                    首页
                </a-menu-item>
                <a-menu-item @click="jumpToUserInfo()">
                    <a-icon type="profile"></a-icon>
                    我的信息
                </a-menu-item>
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

export default {
    name: '',
    data() {
        return {
            current: ['1']
        }
    },
    components: { MsgAffix },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo'
        ])
    },
    mounted() {
        if (this.$route.name == 'hotelList' || this.$route.name == 'hotelDetail') {
            this.current = ['1']
        } else if (this.$route.name == 'userInfo') {
            this.current = ['2']
        } else if (this.$route.name == 'opChatList') {
            this.current = ['3']
        } else if (this.$route.name == 'manageHotel') {
            this.current = ['4']
        } else if (this.$route.name == 'manageUser') {
            this.current = ['5']
        } else if (this.$route.name == 'manageOrder') {
            this.current = ['6']
        } else if (this.$route.name == 'creditList') {
            this.current = ['7']
        } else if (this.$route.name == 'msgFromUser') {
            this.current = ['8']
        } else if (this.$route.name == 'msgForGroup') {
            this.current = ['9']
        }
    },
    methods: {
        ...mapMutations([

        ]),
        ...mapActions([
            'logout'
        ]),
        selectMenu(v){
        },
        async quit() {
            await this.$store.dispatch('logout')
            this.$router.push(`/login?redirect=${this.$route.fullPath}`)
        },
        jumpToUserInfo() {
            this.$router.push({ name: 'userInfo', params: { userId: this.userId } })
        },
        jumpToHome() {

        },
        getMsg() {
            this.$router.push({ name: 'opChatList'})
        }
    }
}
</script>
<style scoped lang="less">
    .header {
        display: flex;
        line-height: 44px;
        height: 44px;
        align-items: center;
        justify-content: space-between;
        min-width: 800px;
        .label{
            height: 44px;
            line-height: 44px;
            vertical-align: middle;
            min-width: 400px;

            .logo {
            height: 44px;
            vertical-align: top;
            margin-right: 16px;
            border-style: none;
            cursor: pointer;
          }

          .title {
            font-size: 33px;
            color: rgba(0, 0, 0, .85);
            font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
            font-weight: 600;
            position: relative;
            top: 2px;
          }

          .search {
              width: 300px;
              margin-left: 30px
          }
        }
        .logout {
            margin-right: 40px;
            .user {
                cursor: pointer;
                display:flex;
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
