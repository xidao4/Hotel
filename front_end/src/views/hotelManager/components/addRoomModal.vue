<template>
    <a-modal
        :visible="addRoomModalVisible"
        title="录入客房"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="房型" v-bind="formItemLayout" help="如果不是第一次录入该种房间类型，将会覆盖之前的记录，请慎重" validate-status="error">
                <a-select
                    v-decorator="[
                    'roomType', 
                    { rules: [{ required: true, message: '请选择房型' }] }]"
                >
                  <a-select-option value="BigBed">大床房</a-select-option>
                  <a-select-option value="DoubleBed">双床房</a-select-option>
                  <a-select-option value="Family">家庭房</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="房间数量" v-bind="formItemLayout">
                <a-input
                    placeholder="请填写房间数量"
                    v-decorator="['roomNum', { rules: [{ required: true, message: '请输入房间数量' }] }]"
                />
            </a-form-item>
            <a-form-item label="原始价格" v-bind="formItemLayout">
                <a-input
                    placeholder="请填写原始价格"
                    v-decorator="['price', { rules: [{ required: true, message: '请输入原始价格' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: 'addRoomModal',
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
            'addRoomModalVisible',
            'activeHotelId',
            'addRoomParams',
            'bigV',
            'familyV',
            'doubleV'
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'addRoomModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addRoomModalVisible',
            'set_addRoomParams',
            'set_bigV',
            'set_doubleV',
            'set_familyV',
        ]),
        ...mapActions([
            'addRoom',
            'updateRoom'
        ]),
        cancel() {
            this.set_addRoomModalVisible(false)
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll(async (err, values) => {
                if (!err) {
                    const data = {
                        roomType: this.form.getFieldValue('roomType'),
                        price: Number(this.form.getFieldValue('price')),
                        total: Number(this.form.getFieldValue('roomNum')),
                        curNum: Number(this.form.getFieldValue('roomNum')),
                        hotelId: this.activeHotelId,
                    }
                    if((data.roomType==='DoubleBed' && this.doubleV===true) || (data.roomType==='BigBed' && this.bigV===true) || (data.roomType==='Family' && this.familyV===true)){
                        await this.updateRoom(data)
                    }
                    if(data.roomType==='DoubleBed' && this.doubleV===''){
                        this.set_doubleV()
                        this.set_addRoomParams(data)
                        await this.addRoom()
                    }
                    if(data.roomType==='BigBed' && this.bigV===''){
                        this.set_bigV()
                        this.set_addRoomParams(data)
                        await this.addRoom()
                    }
                    if(data.roomType==='Family' && this.familyV===''){
                        this.set_familyV()
                        this.set_addRoomParams(data)
                        await this.addRoom()
                    }
                    this.form.resetFields()
                }
            });
        },
    }
}
</script>