<template>
    <div id="pic2" style="width: 100%;height:400px;"></div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import echarts from 'echarts'
    export default {
        name: "proposalCurve",
        data: {

        },
        computed: {
            ...mapGetters([
                'dateList',
                'numList'
            ])
        },
        async mounted(){
            await this.getCurveDataset()
            //console.log("loading...")
            //console.log(this.dateList)
            //console.log(this.numList)
            this.$nextTick(function () {
                let dom = document.getElementById('pic2')
                let myChart = echarts.init(dom)
                myChart.setOption({
                    title: {
                        text: '推广度曲线'
                    },
                    toolbox: {
                        show: true
                    },
                    legend: {
                        data: ['累计注册用户数']
                    },
                    xAxis: {
                        data: this.dateList
                    },
                    yAxis: {},
                    series: [{
                        name: '注册用户数',
                        type: 'line',
                        data: this.numList
                    }]
                })
            })
        },
        methods: {
            ...mapActions([
                'getCurveDataset',
            ]),
        }
    }
</script>

<style scoped>

</style>