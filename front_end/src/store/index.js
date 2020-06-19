import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import hotel from './modules/hotel'
import hotelManager from './modules/hotelManager'
import admin from './modules/admin'
import operator from './modules/operator'
import chat from './modules/chat'
import getters from './getters'
Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    hotel,
    user,
    hotelManager,
    admin,
    operator,
    chat
  },
  state: {
  },
  mutations: {
  },
  actions: {
  },
  getters
})
