const getters = {
  token: state => state.user.token,
  userInfo: state => state.user.userInfo,
  isShowSide: state => state.user.isShowSide
}
export default getters
