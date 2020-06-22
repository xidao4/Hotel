import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import hotel from './modules/hotel'
import hotelManager from './modules/hotelManager'
import admin from './modules/admin'
import operator from './modules/operator'
import tag from './modules/tag'
import chatMessage from './modules/chatMessage'
import getters from './getters'
Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    hotel,
    user,
    hotelManager,
    admin,
    operator,
    tag,
    chatMessage,
  },
  state: {
  },
  mutations: {
  },
  actions: {
  },
  getters
})
