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
                        'email',
                        { rules: [{required: true, message: '请输入用户邮箱', }] }
                    ]"
        />
      </a-form-item>


    </a-Form>
  </a-modal>
</template>

<script>
    export default {
        name: "addHotelModal.vue"
    }
</script>

<style scoped>

</style>
