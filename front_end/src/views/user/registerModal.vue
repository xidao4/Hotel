<template>
  <a-modal
          :visible="registerModalVisible"
          title="填写会员信息"
          cancelText="取消"
          okText="确定"
          @cancel="cancel"
          @ok="handleSubmit"
  >
    <a-Form :form="form">
      <a-form-item v-bind="formItemLayout" label="生日">
        <a-input
                v-decorator="[
                        'birthday',
                        { rules: [{required: true, message: '请输入生日', }] }
                    ]"
        />
      </a-form-item>
    </a-Form>
  </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "registerModal.vue",
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: {span: 12},
                        sm: {span: 6},
                    },
                    wrapperCol: {
                        xs: {span: 24},
                        sm: {span: 16},
                    },
                },
            }
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'registerModal'});
        },
        computed: {
            ...mapGetters([
                'registerModalVisible',
                'userId',
            ])
        },
        methods: {
            ...mapMutations([
                'set_registerModalVisible'
            ]),
            ...mapActions([
                'registerMem'
            ]),
            cancel() {
                this.set_registerModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            userId: this.userId,
                            birthday: this.form.getFieldValue('birthday'),
                        }
                        this.registerMem(data)
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>
