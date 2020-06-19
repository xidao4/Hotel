<template>
    <a-modal
            :visible="LeaveMsgModalVisible"
            title="留言反馈"
            cancelText="取消"
            okText="留言"
            @cancel="cancel"
            @ok="handleMsg">

        <a-form :form="form" style="margin-top: 15px">
            <a-form-item
                    label="问题类型"
                    :label-col="{ span: 4 }" :wrapper-col="{ span: 19, offset: 1 }">
                <a-cascader
                        :options="options"
                        v-decorator="[
                        'type',
                        { rules: [{ required: true, message: '请选择问题类型' }] }]"
                        placeholder="请选择您的问题类型，方便我们匹配更好的客服"
                        @change="onChange"/>
            </a-form-item>
            <a-form-item
                    label="问题说明"
                    :label-col="{ span: 4 }" :wrapper-col="{ span: 19, offset: 1 }">
                <a-textarea
                        v-decorator="[
                        'detail',
                        { rules: [{ required: true, message: '请填写问题说明' }] }]"
                        placeholder="请描述您遇到的问题或建议，我们会在第一时间联系您"
                        :rows="6"></a-textarea>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex';
    import ATextarea from "ant-design-vue/es/input/TextArea";
    import AFormItem from "ant-design-vue/es/form/FormItem";
    export default {
        name: "LeaveMsgModal",
        components: {AFormItem, ATextarea},
        data() {
            return {
                formLayout: 'horizontal',
                form: this.$form.createForm(this, { name: 'coordinated' }),
                options: [
                    {
                        value: '信用问题',
                        label: '信用问题',
                    },
                    {
                        value: '酒店问题',
                        label: '酒店问题',
                    },
                    {
                        value: '订单问题',
                        label: '订单问题',
                    }
                ],
                choice: []
            }
        },
        methods: {
            ...mapMutations([
                'set_LeaveMsgModalVisible',
            ]),
            ...mapActions([
                'getOperatorList',
                'leaveMessage'
            ]),
            handleMsg() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        this.leaveMessage({
                            title: this.form.getFieldValue('type')[0],
                            content: this.form.getFieldValue('detail'),
                            operatorId: this.operatorList[0].id
                        }).then(() => {
                            this.set_LeaveMsgModalVisible(false);
                            this.form.setFieldsValue({
                                type: [],
                                detail: ''
                            })
                        })
                    }
                });
            },
            cancel() {
                this.set_LeaveMsgModalVisible(false);
            },
            onChange(value) {
                this.form.setFieldsValue({
                    type: value
                });
                this.choice = value;
            },
        },
        computed: {
            ...mapGetters([
                'LeaveMsgModalVisible',
                'operatorList'
            ])
        },
        mounted() {
            this.getOperatorList()
        }
    }
</script>

<style scoped>

</style>
