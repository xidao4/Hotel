<template>
  <div class="main">
      <div class="top">
        <div class="header">
          <div>
            <!-- <img src="@/assets/logo.svg" class="logo" alt="logo"> -->
            <span class="title">NJU酒店管理系统</span>
          </div>
        </div>
        <div class="desc">

        </div>
      </div>
    <a-form
      id="formLogin"
      class="user-layout-login"
      ref="formLogin"
      :form="form"
    >
      <a-tabs
        :activeKey="customActiveKey"
        :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }"
        @change="handleTabClick"
      >
        <a-tab-pane key="tab1" tab="账号密码登录">
          <a-form-item>
            <a-input
              size="large"
              type="text"
              placeholder="邮箱"
              v-decorator="[
                'username',
                {rules: [{ required: true, message: '请输入邮箱地址' }], validateTrigger: 'blur'}
              ]"
            >
              <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>

          <a-form-item>
            <a-input
              size="large"
              type="password"
              autocomplete="false"
              placeholder="密码"
              v-decorator="[
                'password',
                {rules: [{ required: true, message: '请输入密码' }], validateTrigger: 'blur'}
              ]"
            >
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-col span="16">
              <a-input
                      size="large"
                      type="text"
                      autocomplete="false"
                      placeholder="验证码"
                      v-model="identify"
                      v-decorator="[
                              'identify',
                              {rules: [{ required: true, message: '请输入验证码' }, { validator: this.handleIdentify }], validateTrigger: 'blur'}
                              ]"
              >
                <a-icon slot="prefix" type="smile" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input>
            </a-col>
            <a-col span="2" offset="2">
              <Identify :identifyCode="identifyCode" @click.native="refreshCode"></Identify>
            </a-col>
          </a-form-item>
          <a-form-item style="margin-top:24px">
            <a-button
              size="large"
              type="primary"
              class="login-button"
              :loading="loginLoading"
              @click="handlelogin()"
            >确定</a-button>
          </a-form-item>
        </a-tab-pane>

        <a-tab-pane key="tab2" tab="注册新账号">
          <a-form-item>
            <a-input
              size="large"
              type="email"
              placeholder="邮箱"
              v-decorator="[
              'registerUserMail',
              {rules: [{ required: true, type: 'email', message: '请输入邮箱' }], validateTrigger: 'blur'}]">
              <a-icon slot="prefix" type="mail" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-input
              size="large"
              placeholder="用户名"
              v-decorator="[
              'registerUsername',
              {rules: [{ required: true, message: '请输入用户名' }], validateTrigger: 'blur'}]">
              <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-date-picker size="large" style="width: 368px;" placeholder="请选择您的生日" v-model="birthday" />
          </a-form-item>
           <a-form-item>
            <a-input
              size="large"
              placeholder="手机号"
              v-decorator="[
              'registerPhoneNumber',
              {rules: [{ required: true, message: '请输入手机号' }], validateTrigger: 'blur'}]">
              <a-icon slot="prefix" type="book" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-input
              size="large"
              type="password"
              placeholder="密码"
              v-decorator="[
                'registerPassword',
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePassword }], validateTrigger: 'blur'}]">
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-input
              size="large"
              type="password"
              placeholder="确认密码"
              v-decorator="[
                'registerPasswordconfirm',
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePasswordCheck }], validateTrigger: 'blur'}]">
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
           <a-form-item style="margin-top:24px">
            <a-button
              size="large"
              type="primary"
              class="login-button"
              :loading="registerLoading"
              @click="handleRegister()"
            >确定</a-button>
          </a-form-item>
        </a-tab-pane>
      </a-tabs>
    </a-form>

  </div>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import Identify from "../components/Identify";
import moment from "moment";
export default {
  name: 'login',
  components: {
    Identify
  },
  data () {
    return {
      customActiveKey: 'tab1',
      loginLoading: false,
      registerLoading: false,
      form: this.$form.createForm(this),
      identify: '',
      identifyCode: '',
      identifyCodes: '1234567890abcdefjhijklinopqrsduvwxyz',
      birthday: '',
    }
  },
  computed: {
    ...mapGetters([
      'token'
    ])
  },
  mounted() {
    // 初始化验证码
    this.identifyCode = '';
    this.makeCode(4)
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    },
  },
  methods: {
    ...mapActions([
      'login',
      'register'
      ]),

    // handler
    handleUsernameOrEmail (rule, value, callback) {
      const { state } = this
      const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
      if (regex.test(value)) {
        callback()
      } else {
        callback(new Error('请输入有效用户名或邮箱'))
      }
      callback()
    },
    checkEmail(rule, value, callback) {
        const re = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
        if (re.test(value)) {
            callback();
        } else {
            callback(new Error('请输入有效邮箱'));
        }
        callback()
    },
    handlePassword(rule, value, callback) {
      if (value.length < 6) {
        callback(new Error('密码长度至少6位'))
      }
      callback()
    },
    handlePasswordCheck (rule, value, callback) {
      const password = this.form.getFieldValue('registerPassword')
      console.log(password)
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
    },
    handleIdentify (rule, value, callback) {
      console.log("value:"+ value.toLowerCase())
      console.log("id-code: "+ this.identifyCode.toLowerCase())
      console.log(value.toLowerCase() !== this.identifyCode.toLowerCase())
      if (value.toLowerCase() !== this.identifyCode.toLowerCase()){
        callback(new Error('验证码错误'))
        this.refreshCode()
      }
      callback()
    },
    handleTabClick (key) {
      this.customActiveKey = key
    },
    handlelogin() {
      const { form: { validateFields } } = this
      const validateFieldsKey = this.customActiveKey === 'tab1' ? ['username', 'password', 'identify'] : ['registerUsername', 'registerUserMail','registerPassword','registerPasswordconfirm']
      validateFields(validateFieldsKey, { force: true }, async (err, values) => {
        if(!err){
          this.loginLoading = true
          const data = {
            email: this.form.getFieldValue("username"),
            password: this.form.getFieldValue("password")
          }
          await this.login(data)
          this.loginLoading = false
        }
      });
    },

    handleRegister() {
      const { form: { validateFields } } = this
      const validateFieldsKey = this.customActiveKey === 'tab1' ? ['username', 'password', 'identify'] : ['registerUsername','registerPhoneNumber','registerUserMail','registerPassword','registerPasswordconfirm']
      validateFields(validateFieldsKey, { force: true }, async (err, values) => {
        if (!err) {
          this.registerLoading = true
          const data = {
            email: this.form.getFieldValue('registerUserMail'),
            password: this.form.getFieldValue('registerPassword'),
            phoneNumber: this.form.getFieldValue('registerPhoneNumber'),
            username: this.form.getFieldValue('registerUsername'),
            credit: 100,
            birthday: this.birthday,
            userType: 1
          }
          await this.register(data).then(() => {
            this.customActiveKey = 'tab1'
            this.form.setFieldsValue({
              'registerUserMail': '',
              'registerPassword': '',
              'registerPasswordconfirm': ''
            })
          })
          this.registerLoading = false
        }
      });
    },
    // 重置验证码
    refreshCode () {
      this.identifyCode = '';
      this.makeCode(4)
    },
    makeCode (l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[this.randomNum(0, this.identifyCodes.length)]
      }
    },
    randomNum (min, max) {
      return Math.floor(Math.random() * (max - min) + min)
    },
  }
}
</script>

<style lang="less" scoped>

.main{
    min-width: 260px;
    width: 368px;
    margin: 100px auto;
    .top {
        text-align: center;

        .header {
          height: 44px;
          line-height: 44px;

          .badge {
            position: absolute;
            display: inline-block;
            line-height: 1;
            vertical-align: middle;
            margin-left: -12px;
            margin-top: -10px;
            opacity: 0.8;
          }

          .logo {
            height: 44px;
            vertical-align: top;
            margin-right: 16px;
            border-style: none;
          }

          .title {
            font-size: 33px;
            color: rgba(0, 0, 0, .85);
            font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
            font-weight: 600;
            position: relative;
            top: 2px;
          }
        }
        .desc {
          font-size: 14px;
          color: rgba(0, 0, 0, 0.45);
          margin-top: 12px;
          margin-bottom: 40px;
        }
      }
}
.user-layout-login {
  label {
    font-size: 14px;
  }

  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }

  .forge-password {
    font-size: 14px;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }
}
</style>
