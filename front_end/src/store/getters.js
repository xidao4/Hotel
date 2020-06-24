const getters = {
  //user
  token: state => state.user.token,
  userId: state => state.user.userId,
  userInfo: state => state.user.userInfo,
  userOrderList: state => state.user.userOrderList,
  idOrder: state=>state.user.idOrder,
  memInfo:state=>state.user.memInfo,
  isMember:state=>state.user.isMember,
  hotelId:state=>state.user.hotelId,
  registerModalVisible:state=>state.user.registerModalVisible,
  dateRecord:state=>state.user.dateRecord,
  creditRecord:state=>state.user.creditRecord,
  imageUrl:state=>state.user.imageUrl,
  isLw:state=>state.user.isLw,
  usePoints:state=>state.user.usePoints,
  //hotel
  hotelListLoading: state => state.hotel.hotelListLoading,
  hotelList: state => state.hotel.hotelList,
  showHotelList: state => state.hotel.showHotelList,
  currentHotelInfo: state => state.hotel.currentHotelInfo,
  currentHotelId: state => state.hotel.currentHotelId,
  orderModalVisible: state => state.hotel.orderModalVisible,
  currentOrderRoom: state => state.hotel.currentOrderRoom,
  orderMatchCouponList: state => state.hotel.orderMatchCouponList,
  hotelInfo:state=>state.hotel.hotelInfo,
  comment: state=>state.hotel.comment,
  hasShownNoti:state=>state.hotel.hasShownNoti,
  //admin
  operatorList:state=>state.admin.operatorList,
  displayOperatorList:state=>state.admin.displayOperatorList,
  clientList:state=>state.admin.clientList,
  managerList: state => state.admin.managerList,
  addManagerModalVisible: state => state.admin.addManagerModalVisible,
  addManagerParams: state => state.admin.addManagerParams,
  addOperatorModalVisible: state => state.admin.addOperatorModalVisible,
  addOperatorParams: state => state.admin.addOperatorParams,
  modifyOOModalVisible:state=>state.admin.modifyOOModalVisible,
  OOIdx:state=>state.admin.OOIdx,
  tmpUserInfo:state=>state.admin.tmpUserInfo,
  isSearching:state=>state.admin.isSearching,
  displayClientList:state=>state.admin.displayClientList,
  tmpClientId:state=>state.admin.tmpClientId,
  tmpClientInfo:state=>state.admin.tmpClientInfo,
  modifyClientModalVisible:state=>state.admin.modifyClientModalVisible,
  HMList:state=>state.admin.HMList,
  displayHMList:state=>state.admin.displayHMList,
  addHMModalVisible:state=>state.admin.addHMModalVisible,
  modifyHMModalVisible:state=>state.admin.modifyHMModalVisible,
  addHotelModalVisible: state => state.admin.addHotelModalVisible,
  tmpHotelId:state=>state.admin.tmpHotelId,
  addMModalVisible:state=>state.admin.addMModalVisible,
  //hotelManager
  orderList: state => state.hotelManager.orderList,
  addRoomModalVisible: state => state.hotelManager.addRoomModalVisible,
  couponVisible: state => state.hotelManager.couponVisible,
  addCouponVisible: state => state.hotelManager.addCouponVisible,
  activeHotelId: state => state.hotelManager.activeHotelId,
  couponList: state => state.hotelManager.couponList,
  addRoomParams: state=>state.hotelManager.addRoomParams,
  bigV: state=>state.hotelManager.bigV,
  doubleV: state=>state.hotelManager.doubleV,
  familyV: state=>state.hotelManager.familyV,
  //operator
  manageCreditVisible: state => state.operator.manageCreditVisible,
  creditRecordsList: state => state.operator.creditRecordsList,
  currentOrderId: state => state.operator.currentOrderId,
  currentUpdateInfo: state => state.operator.currentUpdateInfo,
  dateList: state => state.operator.dateList,
  numList: state => state.operator.numList,
  //tag
  tags:state=>state.tag.tags,
  //message
  MsgAffixVisible: state => state.chatMessage.MsgAffixVisible,
  LeaveMsgModalVisible: state => state.chatMessage.LeaveMsgModalVisible,
  ChatVisible: state => state.chatMessage.ChatVisible,
  ChatBoxVisible: state => state.chatMessage.ChatBoxVisible,
  clientQuesList: state => state.chatMessage.clientQuesList,
  currentQuesId: state => state.chatMessage.currentQuesId,
  quesRecords: state => state.chatMessage.quesRecords,
  clientQuesListForC: state => state.chatMessage.clientQuesListForC,
  currentQuesStatus: state => state.chatMessage.currentQuesStatus,
  msgClientList: state => state.chatMessage.msgClientList,
  currentGroupType: state => state.chatMessage.currentGroupType,
  broadcastList: state => state.chatMessage.broadcastList,
  }

  export default getters
