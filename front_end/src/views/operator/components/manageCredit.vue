<template>
    <a-modal
            :visible="manageCreditVisible"
            title="更新信用值"
            cancelText="取消"
            okText="更新"
            @cancel="cancel"
            @ok="handleUpdate"
    >
        <a-form :form="form" style="margin-top: 30px">
            <a-form-item label="订单号" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                <span>{{ currentUpdateInfo.orderId }}</span>
            </a-form-item>
            <a-form-item label="用户ID" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1 }">
                <span>{{ currentUpdateInfo.userId }}</span>
            </a-form-item>
            <a-form-item label="更新方式" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1 }">
                <a-select
                        default-value="defaultDecre"
                        @change="handleSelectChange">
                    <a-select-option value="defaultDecre">
                        系统默认
                    </a-select-option>
                    <a-select-option value="manualUpdate">
                        手动更新
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="更新为" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1 }" v-show="showInput">
                <a-input
                        placeholder="请输入更新后信用值"
                        v-decorator="['creditVal', { rules: [{ required: true, message: '请输入更新后信用值' }] }]"
                />
            </a-form-item>
            <a-form-item label="更新说明" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1 }" v-show="showInput">
                <a-input
                        placeholder="请输入更新说明"
                        v-decorator="['desc', { rules: [{ required: true, message: '请输入更新说明' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'

    export default {
        name: 'manageCredit',
        components:{
        },
        data() {
            return {
                formLayout: 'horizontal',
                form: this.$form.createForm(this, { name: 'coordinated' }),
                changeMethod: 'decreDefault',
                showInput: false,
            }
        },
        computed: {
            ...mapGetters([
                'manageCreditVisible',
                'currentUpdateInfo',
                'orderList',
                'showOrderList'
            ])
        },
        methods: {
            ...mapMutations([
                'set_manageCreditVisible',
                'set_showOrderList'
            ]),
            ...mapActions([
                'updateCredit',
                'getAllOrders'
            ]),
            cancel() {
                this.set_manageCreditVisible(false)
            },
            handleSelectChange(value) {
                this.changeMethod = value;
                if(value === "manualUpdate") {
                    this.showInput = true
                } else {
                    this.showInput = false
                }
            },
            async handleUpdate() {
                if(this.changeMethod === 'decreDefault') {
                    await this.updateCredit({
                        method: this.changeMethod,
                        data: {
                            orderId: this.currentUpdateInfo.orderId,
                            desc: this.form.getFieldValue('desc')
                        }
                    })
                } else {
                    await this.updateCredit({
                        method: this.changeMethod,
                        data: {
                            orderId: this.currentUpdateInfo.orderId,
                            creditVal: this.form.getFieldValue('creditVal'),
                            desc: this.form.getFieldValue('desc')
                        }
                    })
                }
                await this.getAllOrders();
                this.set_showOrderList(this.orderList);
            }
        }
    }
</script>
