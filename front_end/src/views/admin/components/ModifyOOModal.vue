<template>
  <a-modal
          :visible="modifyOOModalVisible"
          title="修改网站运营人员信息"
          cancelText="取消"
          okText="确定"
          @cancel="cancel"
          @ok="saveModify"
  >

    <a-Form :form="form">
      <a-form-item v-bind="formItemLayout" label="用户邮箱">
        {{ this.tmpUserInfo.email }}
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
                v-model="newPWD"
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
        name: "ModifyOOModal",
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
                newPWD:'',
                changePWD:false
            }
        },
        computed: {
            ...mapGetters([
                'modifyOOModalVisible',
                'OOIdx',
                'tmpUserInfo',
                'operatorList'
            ])
        },
        watch:{
            modifyOOModalVisible:function() {
                this.modifyInfo()
            },
            newPWD:function () {
                this.changePWD=true
            }
        },
        mounted(){

        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'modifyOOModal' });
        },
        methods:{
            ...mapMutations([
                'set_modifyOOModalVisible',
                'set_OOIdx',
                'set_OperatorList'
            ]),
            ...mapActions([
                'getTmpUserInfo',
                'updateTmpUserInfo',
                'getOperatorList',
                'getManagerList'
            ]),
            saveModify() {
                this.form.validateFields(async(err, values) => {
                    if (!err) {
                        const data = {
                            userName: this.form.getFieldValue('userName'),
                            password: this.changePWD?this.form.getFieldValue('password'):'',

                        }
                        await this.updateTmpUserInfo(data)
                        this.set_modifyOOModalVisible(false)

                        console.log('newPWD',this.newPWD)
                        console.log('changePWD',this.changePWD)
                        this.changePWD=false
                    }
                });

            },
            cancel() {
                this.set_modifyOOModalVisible(false)
            },
            modifyInfo() {
                //console.log("implementing modifyInfo")
                console.log('userName',this.tmpUserInfo.userName)
                console.log('数据库中取出的原密码password',this.tmpUserInfo.password)
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'userName': this.tmpUserInfo.userName,
                        'password': this.tmpUserInfo.password,
                    })
                }, 0)

            },
        }
    }
</script>

<style scoped>

</style>
