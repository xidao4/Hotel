<template>
   <div>
    <a-modal
        :visible="couponVisible"
        title="优惠策略"
        width="900px"
        :footer="null"
        @cancel="cancel"
    >
        <!-- 这里是模态框内容区域，请编写列表代码与添加策略按钮 -->
        <div style="width: 100%; text-align: right; margin: 20px 0">
            <a-button type="primary" @click="addCoupon">
                <a-icon type="plus" />添加优惠策略
            </a-button>
        </div>
        <a-table
        :columns="columns"
        :dataSource="couponList"
        bordered
        v-if="couponShow"
        >
<!--定义好了columns、dataSource属性之后，a-table这个组件自动会去找columns、couponList渲染，不需要手动去写每一行-->
<!--            将couponName映射为text，并作为插值表达式插入，和columns里的第一个字段挂钩-->
<!--    问题一：        slot插槽？有一点不清楚-->
            <a-tag color="red" slot="couponName" slot-scope="text">
                {{text}}
            </a-tag>
            <button slot="delete">
                删除
            </button>
            <template slot="delete" slot-scope="record">
                <a-popconfirm
                    title="确认删除该优惠券吗?"
                    ok-text="确认"
                    @confirm="deleteCouponV(record.id)"
                >
                    <a href="#">删除</a>
                </a-popconfirm>
            </template>
        </a-table>
    </a-modal>
<!--AddCoupon这个组件默认addCouponVisible属性是false的-->
    <AddCoupon></AddCoupon>
   </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddCoupon from './addCoupon'

const columns = [
    // 这里定义列表头
    //columns中的dataIndex：列数据在数据项（couponList）中对应的key
    //customRender：生成复杂数据的渲染函数，参数分别为当前行的值，当前行数据，行索引
    //使用 columns 时，可以通过该slot、slot-scope配置支持 slot、slot-scope 的属性
    //问题二：从mapGetters里面获取的couponList的属性，是怎么和dataIndex挂钩的？
    {
        title: '优惠类型',
        dataIndex: 'couponName',
        scopedSlots: {customRender: 'couponName'}
    },
    // {
    //     title: '折扣',
    //     dataIndex: 'discount'
    // },
    {
        title: '优惠简介',
        dataIndex: 'description'
    },
    {
        title: '优惠金额',
        dataIndex: 'discountMoney'
    },
    {
        title: '操作',
        key: 'delete',
        scopedSlots: {customRender: 'delete'}
    }
  ];
export default {
    name: 'coupon',
    data() {
        return {
            columns,
            couponShow: true
        }
    },
    components: {
        AddCoupon,
    },
    computed: {
        // 从store里面获取全局数据
        ...mapGetters([
            'couponVisible',
            'couponList',
        ])
    },
    methods: {
        // 暴露mutation里面的方法接口
        ...mapMutations([
            // 将 `this.set_addCouponVisible(data)` 映射为 `this.$store.commit('set_addCouponVisible',data)`；下面一个同理
            // 这种方式必须在根结点里注入store
            'set_addCouponVisible',
            'set_couponVisible',
        ]),
        // 从store里面获取数据
        ...mapActions([
            // 将 `this.getHotelCoupon()` 映射为 `this.$store.dispatch('getHotelCoupon')`
            // 这种方式必须在根结点里注入store
            'getHotelCoupon',
            'deleteCoupon',
            'getHotelCoupon'
        ]),
        cancel() {
            // 下面三个通过调用mapXXX方法接口来改变state里面的全局数据
            this.set_couponVisible(false)
        },
        addCoupon() {
            this.set_addCouponVisible(true),
            this.set_couponVisible(false)
        },
        async deleteCouponV(recordId){
            console.log('recordID:')
            console.log(recordId)
            await this.deleteCoupon(recordId)
            await this.getHotelCoupon()
            this.couponShow=false
            console.log('不显示优惠券列表')
            this.couponShow=true
            console.log('显示优惠券列表')
        }
    },
}
</script>
<style scoped>

</style>