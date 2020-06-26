<template>
    <div id="layout">
        <Header v-if="isLw"></Header>
        <transition name="fade-transform" mode="out-in">
            <router-view style="padding: 20px 80px 144px;"/>
        </transition>
    </div>
</template>
<script>
import Header from '@/components/header'
import {mapGetters,mapMutations} from 'vuex'
export default {
    name: 'layout',
    components: {
        Header
    },
    computed:{
        ...mapGetters([
            'isLw'
        ])
    },
    data(){
        return{

        }
    },
    methods:{

    },
    mounted() {
        console.log('mounted里的isShow')
      console.log(this.isLw)
    }
}
</script>
