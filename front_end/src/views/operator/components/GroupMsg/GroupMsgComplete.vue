<template>
    <div>
        <a-form>
            <a-result title="发送成功" :is-success="true" style="max-width: 560px; margin: 40px auto 0;">
                <template #extra>
                    <a-button type="primary" @click="finish">继续推送</a-button>
                    <a-button style="margin-left: 8px" @click="toRecord" v-if="currentGroupType===0">查看推送记录</a-button>
                    <a-button style="margin-left: 8px" @click="toRecord" v-if="currentGroupType===1">管理公告</a-button>
                    <a-button style="margin-left: 8px" @click="toRecord" v-else>查看优惠券赠送记录</a-button>
                </template>
            </a-result>
        </a-form>
    </div>
</template>

<script>
    import { mapGetters } from 'vuex'
    export default {
        name: 'Step3',
        data () {
            return {
                loading: false
            }
        },
        computed: {
            ...mapGetters([
                'currentGroupType'
            ])
        },
        methods: {
            finish () {
                this.$emit('finish')
            },
            toRecord() {
                this.$emit('seeRecord')
            }
        }
    }
</script>
<style lang="less" scoped>

    .information {
        line-height: 22px;

        .ant-row:not(:last-child) {
            margin-bottom: 24px;
        }
    }
    .money {
        font-family: "Helvetica Neue",sans-serif;
        font-weight: 500;
        font-size: 20px;
        line-height: 14px;
    }
</style>
