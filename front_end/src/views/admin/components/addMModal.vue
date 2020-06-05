<template>
  <a-modal
          :visible="addMModalVisible"
          title="添加酒店工作人员"
          cancelText="取消"
          okText="确定"
          @cancel="cancel"
          @ok="handleSubmit"
  >
    <a-Form :form="form">
      <a-form-item v-bind="formItemLayout" label="账号">
        <a-input
                v-decorator="[
                        'email',
                        { rules: [{required: true, message: '请输入账号', }] }
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
    export default {
        name: "addMModal.vue",
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
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'addMModal' });
        },
        computed: {
            ...mapGetters([
                'addMModalVisible',
                'tmpHotelId'
            ])
        },
        methods:{
            ...mapMutations([
                'set_addMModalVisible'
            ]),
            ...mapActions([
                'addManager',
            ]),
            cancel() {
                this.set_addMModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            hotelId: this.tmpHotelId,
                            email: this.form.getFieldValue('email'),
                            userName: this.form.getFieldValue('userName'),
                            password: this.form.getFieldValue('password')
                        }
                        this.addManager(data)
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>
