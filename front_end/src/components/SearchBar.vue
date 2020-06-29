<template>
    <div style="margin-bottom: 0; box-shadow:0px 12px 8px -15px #000;">
        <a-page-header
                :ghost="false"
                style="padding-bottom: 0; padding-top: 0;"
        >
            <div style="display: inline-flex; width: 100%">
                <a-menu v-model="current" mode="horizontal" theme="light" style="height: 50px; border-bottom: 0;">
                    <a-menu-item key="1" @click="selectMenu" v-if="userInfo.userType=='Client' || userInfo.userType=='Operator'">
                        <router-link to="/hotel/hotelList">
                            <a-icon type="home" />酒店
                        </router-link>
                    </a-menu-item>
                    <a-menu-item key="2" @click="jumpToUserInfo" v-if="userInfo.userType=='Client'">
                        <a-icon type="user"/>
                        个人中心
                    </a-menu-item>
                    <a-menu-item key="3" @click="getMsg" v-if="userInfo.userType=='Client'">
                        <a-icon type="user" />留言
                    </a-menu-item>
                    <a-menu-item key="4" @click="selectMenu" v-if="userInfo.userType=='HotelManager'">
                        <router-link :to="{ name: 'manageHotel'}">
                            <a-icon type="switcher"/>
                            酒店管理
                        </router-link>
                    </a-menu-item>
                    <a-menu-item key="5" @click="selectMenu" v-if="userInfo.userType=='Manager'">
                        <router-link :to="{ name: 'manageUser'}">
                            <a-icon type="user"/>
                            账户管理
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
                    <a-menu-item key="8" @click="selectMenu" v-if="userInfo.userType=='Operator'">
                        <router-link :to="{ name: 'proposalCurve'}">
                            <a-icon type="user" />推广度曲线
                        </router-link>
                    </a-menu-item>
                    <a-sub-menu @click="selectMenu"  v-if="userInfo.userType=='Operator'">
                        <span slot="title"><a-icon type="appstore" /><span>消息</span></span>
                        <a-menu-item key="9">
                            <router-link :to="{ name: 'userChatList'}">
                                <a-icon type="user" />问题
                            </router-link>
                        </a-menu-item>
                        <a-menu-item key="10">
                            <router-link :to="{ name: 'msgForGroup'}">
                                <a-icon type="user" />发送消息
                            </router-link>
                        </a-menu-item>
                    </a-sub-menu>
                </a-menu>

                <div v-if="showSearch" class="search-box">
                    <a-input-search
                            placeholder="搜索酒店"
                            enter-button="搜索"
                            @search="onSearch"></a-input-search>

                    <a-button type="primary" style="margin-left: 10px" @click="showChoices">筛选酒店</a-button>
                </div>
            </div>
        </a-page-header>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex';
    export default {
        name: "SearchBar",
        data() {
            return {
                current: ['1'],
                avatar_url: '',
                f5: true
            }
        },
        computed: {
            ...mapGetters([
                'userId',
                'userInfo',
                'userOrderList'
            ]),
            showSearch() {
                return this.$route.name == 'hotelList';
            },
        },
        methods: {
            ...mapMutations([
                'set_hasShownNoti',
                'set_showFilter',
            ]),
            ...mapActions([
                'logout',
                'getUserInfo'
            ]),
            showChoices() {
                this.set_showFilter(true);
            },
            selectMenu(v) {
            },
            async quit() {
                this.set_hasShownNoti(false)
                await this.$store.dispatch('logout')
                this.$router.push(`/login?redirect=${this.$route.fullPath}`)
            },
            jumpToUserInfo() {
                this.$router.push({name: 'userInfo', params: {userId: this.userId}})
            },
            jumpToHome() {

            },
            getMsg() {
                this.$router.push({ name: 'opChatList'})
            },

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
            } else if (this.$route.name == 'proposalCurve') {
                this.current = ['8']
            } else if (this.$route.name == 'userChatList') {
                this.current = ['9']
            } else if (this.$route.name == 'msgForGroup') {
                this.current = ['10']
            }
        }
    }
</script>

<style scoped>

    .search-box {
        width: 50%;
        margin-left: 20%;
        display: inline-flex;
        justify-content: space-between;
    }

</style>
