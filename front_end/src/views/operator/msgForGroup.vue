<template>
    <div>
        <a-tabs
                style="margin-top: 20px"
                :activeKey="customActiveKey"
                @change="handleTabClick">
            <a-tab-pane key="exit" tab="编辑推送">
                <a-card :bordered="false" style="background-color: inherit">
                    <a-steps class="steps" :current="currentTab">
                        <a-step title="选择发送对象"></a-step>
                        <a-step title="填写消息内容"></a-step>
                        <a-step title="完成"></a-step>
                    </a-steps>
                    <div class="content">
                        <choose-clients v-if="currentTab === 0" @nextStep="nextStep"/>
                        <edit-msg v-if="currentTab === 1" @nextStep="nextStep" @prevStep="prevStep"/>
                        <group-msg-complete v-if="currentTab === 2" @prevStep="prevStep" @finish="finish" @seeRecord="seeRecord"/>
                    </div>
                </a-card>
            </a-tab-pane>
            <a-tab-pane key="record" tab="推送记录">
                <GroupRecords></GroupRecords>
            </a-tab-pane>
        </a-tabs>
    </div>
</template>

<script>
    import ChooseClients from './components/GroupMsg/ChooseClients';
    import EditMsg from './components/GroupMsg/EditMsg';
    import GroupMsgComplete from './components/GroupMsg/GroupMsgComplete';
    import GroupRecords from './components/GroupMsg/GroupRecords';

    export default {
        name: "msgForGroup",
        components: { ChooseClients, EditMsg, GroupMsgComplete, GroupRecords },
        data() {
            return {
                currentTab: 0,
                customActiveKey: 'exit'
            }
        },
        methods: {
            nextStep () {
                if (this.currentTab < 2) {
                    this.currentTab += 1
                }
            },
            prevStep () {
                if (this.currentTab > 0) {
                    this.currentTab -= 1
                }
            },
            finish () {
                this.currentTab = 0
            },
            seeRecord() {
                this.customActiveKey = 'record'
            },
            handleTabClick (key) {
                this.customActiveKey = key
            },
        },
    }
</script>

<style lang="less" scoped>

    .card-container {
        margin-bottom: 16px;
        margin-top: 30px;
        padding: 1vw 1vw;
    }
    .content {
        position: relative;
        top: 4px;
        flex: 1 1 auto;
        margin-left: 24px;
        line-height: 22px;
    }
    .steps {
        max-width: 750px;
        margin: 16px auto;
    }

</style>
