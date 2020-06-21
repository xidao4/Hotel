<template>
    <div>
        <div class="step-form-style-desc">
            <h3>说明</h3>
            <h4>私信模式</h4>
            <p>将显示在客户收件箱中。</p>
            <h4>公告模式</h4>
            <p>公告模式将会展示在网站首页，默认全体可见。</p>
        </div>

        <a-divider />

        <div class="card-container">
            <a-radio-group v-model="value" @change="changeMode">
                <a-radio :value="0">
                    私信
                </a-radio>
                <a-radio :value="1">
                    公告
                </a-radio>
            </a-radio-group>

            <a-divider type="vertical"></a-divider>

            <a-button
                    type="primary"
                    :disabled="!hasSelected"
                    :loading="loading"
                    @click="start">
                重置
            </a-button>
            <span style="margin-left: 8px">
                <template v-if="hasSelected">
                    {{ `已选中 ${selectedRowKeys.length} 位客户` }}
                </template>
            </span>
        </div>
        <a-table
                :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
                :columns="columns"
                :dataSource="showMsgClientList"
                bordered
        >
        </a-table>
        <a-form>
            <a-form-item :wrapperCol="{span: 3, offset: 10}">
                <a-button type="primary" @click="nextStep">下一步</a-button>
            </a-form-item>
        </a-form>
    </div>
</template>

<script>
    import {message} from 'ant-design-vue';
    import { mapGetters, mapMutations, mapActions } from 'vuex';

    const columns = [
        {
            title:'用户编号',
            dataIndex:'id',
        },
        {
            title: '用户名',
            dataIndex: 'userName',
        },
        {
            title: '是否为会员',
        }
    ];

    export default {
        name: 'ChooseClients',
        data () {
            return {
                labelCol: { lg: { span: 5 }, sm: { span: 5 } },
                wrapperCol: { lg: { span: 19 }, sm: { span: 19 } },
                columns,
                selectedRowKeys: [],
                loading: false,
                value: 0,
                currentTab: 0,
            }
        },
        methods: {
            ...mapMutations([
                'set_currentGroupType',
                'set_currentGroupTos',
            ]),
            ...mapActions([
                'getMsgClientList'
            ]),
            start() {
                this.loading = true;
                setTimeout(() => {
                    this.loading = false;
                    this.selectedRowKeys = [];
                    this.value = 0;
                }, 500);
            },
            onSelectChange(selectedRowKeys) {
                this.selectedRowKeys = selectedRowKeys;
            },
            changeMode() {
                if(this.value === 1) {
                    this.selectedRowKeys = [...Array(this.msgClientList.length).keys()];
                }
            },
            nextStep () {
                if (this.selectedRowKeys.length > 0) {
                    this.set_currentGroupType(this.value);
                    let selectedIds = [];
                    for(let i in this.selectedRowKeys) {
                        selectedIds.push(this.msgClientList[i].id)
                    }
                    this.set_currentGroupTos(selectedIds);
                    this.$emit('nextStep')
                } else {
                    message.error('请选择收件方')
                }
            },
        },
        computed: {
            ...mapGetters([
                'msgClientList'
            ]),
            showMsgClientList() {
                return this.msgClientList;
            },
            hasSelected() {
                return this.selectedRowKeys.length > 0;
            },
        },
        async mounted() {
            await this.getMsgClientList();
        }
    }
</script>

<style lang="less" scoped>
    .card-container {
        margin: 16px auto;
        padding: 1vw 1vw;
    }
    .step-form-style-desc {
        padding: 0 56px;
        color: rgba(0,0,0,.45);

        h3 {
            margin: 0 0 12px;
            color: rgba(0,0,0,.45);
            font-size: 16px;
            line-height: 32px;
        }

        h4 {
            margin: 0 0 4px;
            color: rgba(0,0,0,.45);
            font-size: 14px;
            line-height: 22px;
        }

        p {
            margin-top: 0;
            margin-bottom: 12px;
            line-height: 22px;
        }
    }
</style>
