import Indicator from './Indicator'

export default {
  install (Vue, config = {}) {
    Vue.$indicator = Vue.prototype.$indicator = Indicator
  }
}
