<template>
  <a-config-provider :locale="zh_CN">
    <div id="app" v-title data-title="NJUSE酒店管理系统">
      <transition name="fade-transform" mode="out-in">
        <router-view/>
      </transition>
    </div>
  </a-config-provider>
</template>
<script>
  import zh_CN from 'ant-design-vue/lib/locale-provider/zh_CN';

  export default {
    name: 'app',
    data() {
      return {
        zh_CN,
      };
    },
      //解决刷新userInfo丢失问题，https://juejin.im/post/5c809599f265da2dbe030ec6
      created(){
          //在页面加载时读取sessionStorage里的状态信息
        if(sessionStorage.getItem("store")){
            this.$store.replaceState(Object.assign({},this.$store.state,JSON.parse(sessionStorage.getItem("store"))))
        }
          //在页面刷新时将vuex里的信息保存到sessionStorage里
        window.addEventListener("beforeunload",()=>{
            sessionStorage.setItem("store",JSON.stringify(this.$store.state))
        })
      }
  };
</script>
<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
    width: 100%;
    padding: 20px 100px 144px;
    background: #f0f2f5 url('assets/background.svg') repeat 100%;
    min-height: 800px
  }

  #nav {
    padding: 30px;
  }

  #nav a {
    font-weight: bold;
    color: #2c3e50;
  }

  #nav a.router-link-exact-active {
    color: #42b983;
  }

  /* fade-transform */
  .fade-transform-leave-active,
  .fade-transform-enter-active {
    transition: all .5s;
  }

  .fade-transform-enter {
    opacity: 0;
    transform: translateX(-30px);
  }

  .fade-transform-leave-to {
    opacity: 0;
    transform: translateX(30px);
  }
</style>
