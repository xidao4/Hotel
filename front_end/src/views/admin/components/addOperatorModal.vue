<template>
    <a-modal
        :visible="addOperatorModalVisible"
        title="添加网站运营人员"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
    <a-Form :form="form">
        <a-form-item v-bind="formItemLayout" label="用户邮箱">
                <a-input
                    v-decorator="[
                        'email',
                        { rules: [{required: true, message: '请输入用户邮箱', }] }
                    ]"
                />
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="用户名">
        <a-input
                v-decorator="[
                        'userName',
                        { rules: [{required: true, message: '请输入用户名', }] }
                    ]"
        />
      </a-form-item >
        <a-form-item v-bind="formItemLayout" label="密码">
                <a-input
                    type="password"
                    autocomplete="false"
                    placeholder="密码"
                    v-decorator="[
                        'password',
                        { rules: [{required: true, message: '请输入密码', }] }
                    ]"
                />
            </a-form-item >
    </a-Form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
const moment = require('moment')
export default {
    name: 'addOperatorModal',
    data() {
        return {
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
            },
        }
    },
    computed: {
        ...mapGetters([
            'addOperatorModalVisible',
            'OperatorList',
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'addOperatorModal' });
    },
    mounted() {},
    watch:{
        addOperatorModalVisible() {
            this.form.resetFields()
        }
    },
    methods: {
        ...mapMutations([
            'set_addOperatorModalVisible',
            'set_addOperatorParams',
        ]),
        ...mapActions([
            'getOperatorList',
            'addOperator',
        ]),
        cancel() {
            this.set_addOperatorModalVisible(false)
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        email: this.form.getFieldValue('email'),
                        userName: this.form.getFieldValue('userName'),
                        password: this.form.getFieldValue('password'),
                    }
                    this.set_addOperatorParams(data)
                    this.addOperator()
                }
            });
        },
    }
}
</script>
