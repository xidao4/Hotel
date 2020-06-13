<template>
  <a-modal
          :visible="modifyClientModalVisible"
          title="修改客户信息"
          cancelText="取消"
          okText="确定"
          @cancel="cancel"
          @ok="saveModify"
  >
    <a-Form :form="form">
      <a-form-item v-bind="formItemLayout" label="用户邮箱">
        {{ this.tmpClientInfo.email }}
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
      <a-form-item v-bind="formItemLayout" label="手机号">
        <a-input
                v-decorator="[
                        'phoneNumber',
                        { rules: [{required: true, message: '请输入手机号', }] }
                    ]"
        />
      </a-form-item >
    </a-Form>
  </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "ModifyClientModal.vue",
        data(){
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
                'modifyClientModalVisible',
                'tmpClientInfo'
            ])
        },
        watch:{
            modifyClientModalVisible:function() {
                this.modifyInfo()
            }
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'modifyClientModal' });
            // console.log('before modifyInfo()')
            // this.modifyInfo()
        },
        methods:{
            ...mapMutations([
                'set_modifyClientModalVisible',
            ]),
            ...mapActions([
                'updateTmpClientInfo',
            ]),
            saveModify() {
                this.form.validateFields(async (err, values) => {
                    if (!err) {
                        const data = {
                            userName: this.form.getFieldValue('userName'),
                            password: this.form.getFieldValue('password'),
                            phoneNumber: this.form.getFieldValue('phoneNumber')
                        }
                        await this.updateTmpClientInfo(data)
                        this.set_modifyClientModalVisible(false)
                    }
                });
            },
            cancel() {
                this.set_modifyClientModalVisible(false)
            },
            modifyInfo() {
                //console.log('in modifyInfo()',this.tmpClientInfo.userName)
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'userName': this.tmpClientInfo.userName,
                        'password': this.tmpClientInfo.password,
                        'phoneNumber':this.tmpClientInfo.phoneNumber
                    })
                }, 0)
            },
        }
    }
</script>

<style scoped>

</style>
