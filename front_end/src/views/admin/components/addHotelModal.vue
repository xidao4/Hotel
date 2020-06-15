<template>
  <a-modal
          :visible="addHotelModalVisible"
          title="添加酒店"
          cancelText="取消"
          okText="确定"
          @cancel="cancel"
          @ok="handleSubmit"
  >
    <a-Form :form="form">
      <a-form-item v-bind="formItemLayout" label="酒店名称">
        <a-input
                v-decorator="[
                        'name',
                        { rules: [{required: true, message: '请输入名称', }] }
                    ]"
        />
      </a-form-item>
      <a-form-item label="商圈" v-bind="formItemLayout">
        <a-select
                v-decorator="[
                    'bizRegion',
                    { rules: [{ required: true, message: '请选择酒店商圈' }] }]"
                @change="changeRegion"
        >
          <a-select-option value="XiDan">西单</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="地址">
        <a-input
                v-decorator="[
                        'address',
                        { rules: [{required: true, message: '请输入地址', }] }
                    ]"
        />
      </a-form-item>
    </a-Form>
  </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "addHotelModal.vue",
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
            this.form = this.$form.createForm(this, { name: 'addHotelModal' });
        },
        computed: {
            ...mapGetters([
                'addHotelModalVisible',
            ])
        },
        methods:{
            ...mapMutations([
                'set_addHotelModalVisible'
            ]),
            ...mapActions([
              'addHotel'
            ]),
            cancel() {
                this.set_addHotelModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            name: this.form.getFieldValue('name'),
                            bizRegion: this.form.getFieldValue('bizRegion'),
                            address: this.form.getFieldValue('address')
                        }
                        this.addHotel(data)
                    }
                });
            },
            changeRegion(v){

            }
        }
    }
</script>

<style scoped>

</style>
