<template>
    <div>
        <a-affix :offset-top="460" style="text-align: right">
            <a-tooltip placement="left">
                <template slot="title">
                    <span>留言</span>
                </template>
                <a-button
                        type="primary"
                        shape="circle"
                        icon="customer-service"
                        size="large"
                        @click="askQuestion"></a-button>
            </a-tooltip>
        </a-affix>

<!--        <a-affix :offset-top="520" style="text-align: right">-->
<!--            <a-tooltip placement="left">-->
<!--                <template slot="title">-->
<!--                    <span>信箱</span>-->
<!--                </template>-->
<!--                <a-button-->
<!--                        type="primary"-->
<!--                        shape="circle"-->
<!--                        icon="mail"-->
<!--                        size="large"-->
<!--                        @click="getMsg"></a-button>-->
<!--            </a-tooltip>-->
<!--        </a-affix>-->

        <LeaveMsgModal></LeaveMsgModal>
    </div>
</template>

<script>
    import LeaveMsgModal from "./LeaveMsgModal";
    import { mapGetters, mapMutations } from 'vuex';
    export default {
        name: "MsgAffix",
        components: {LeaveMsgModal},
        methods: {
            ...mapMutations([
                'set_MsgAffixVisible',
                'set_LeaveMsgModalVisible',
                'set_ChatVisible'
            ]),
            askQuestion() {
                this.set_LeaveMsgModalVisible(true);
            },
            getMsg() {
                this.$router.push('/user/opMessage')
            }
        },
        computed: {
            ...mapGetters([
                'LeaveMsgModalVisible'
            ])
        }
    }
</script>

<style scoped>

</style>
