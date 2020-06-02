<template>
    <a-modal
            :visible="manageCreditVisible"
            title="更新信用值"
            cancelText="取消"
            okText="更新"
            @cancel="cancel"
            @ok="handleUpdate"
    >
        <a-form :form="form" style="margin-top: 30px">
            <a-form-item label="用户名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                <span>{{ userInfo.userName }}</span>
            </a-form-item>

            <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                <span>{{ userInfo.phoneNumber}}</span>
            </a-form-item>
            <a-form-item label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                <span>{{ userInfo.credit }}</span>
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'

    export default {
        name: 'manageCredit',
        components:{
        },
        data() {
            return {
                formLayout: 'horizontal',
            }
        },
        computed: {
            ...mapGetters([
                'manageCreditVisible'
            ])
        },
        methods: {
            ...mapMutations([
                'set_manageCreditVisible'
            ]),
            ...mapActions([

            ]),
            cancel() {
                this.set_manageCreditVisible(false)
            },
            handleUpdate() {

            }
        }
    }
</script>
