<template>
    <div>
        <a-form :form="form" style="max-width: 600px; margin: 40px auto 0;">
            <a-alert
                    :closable="true"
                    message="确认发送后，消息无法撤回。"
                    style="margin-bottom: 24px;"
            />
            <a-form-item
                    label="主题"
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    class="stepFormText">
                <a-input
                        placeholder="请填写主题"
                        v-decorator="['title', { rules: [{ required: true, message: '请输入主题' }] }]"
                />
            </a-form-item>
            <a-form-item
                    label="内容"
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    class="stepFormText">
                <a-input type="textarea"
                         :rows="4"
                         placeholder="请填写内容"
                         v-decorator="['content', { rules: [{ required: true, message: '请输入消息内容' }] }]"/>
            </a-form-item>
            <a-form-item
                    label="优先级"
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    class="stepFormText">
                <a-select
                        default-value="0"
                        @change="handleSelectChange">
                    <a-select-option value="1">
                        高
                    </a-select-option>
                    <a-select-option value="0">
                        低
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item :wrapperCol="{span: 19, offset: 5}">
                <a-button :loading="loading" type="primary" @click="nextStep">发送</a-button>
                <a-button style="margin-left: 8px" @click="prevStep">上一步</a-button>
            </a-form-item>
        </a-form>
    </div>
</template>

<script>
    import { mapGetters, mapActions } from 'vuex';
    import { message } from 'ant-design-vue';

    export default {
        name: 'EditMsg',
        data () {
            return {
                labelCol: { lg: { span: 3 }, sm: { span: 3 } },
                wrapperCol: { lg: { span: 21 }, sm: { span: 21 } },
                form: this.$form.createForm(this, { name: 'coordinated' }),
                loading: false,
                timer: 0,
                priority: 0
            }
        },
        computed: {
            ...mapGetters([
                'currentGroupType'
            ])
        },
        methods: {
            ...mapActions([
                'sendBroadcast'
            ]),
            nextStep () {
                this.loading = true;
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const res = this.sendBroadcast({
                            title: this.form.getFieldValue('title'),
                            content: this.form.getFieldValue('content'),
                            priority: this.priority
                        });
                        if(res) {
                            this.$emit('nextStep');
                        } else {
                            message.error(res);
                            this.$emit('prevStep');
                        }
                    } else {
                        this.loading = false;
                    }
                });
            },
            prevStep () {
                this.$emit('prevStep')
            },
            handleSelectChange(value) {
                this.priority = value;
            },
        },
        beforeDestroy () {
            clearTimeout(this.timer)
        }
    }
</script>

<style lang="less" scoped>
    .stepFormText {
        margin-bottom: 24px;

        .ant-form-item-label,
        .ant-form-item-control {
            line-height: 22px;
        }
    }

</style>
