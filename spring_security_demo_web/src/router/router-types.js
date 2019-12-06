// ------------------------------ 用 户 服 务 ------------------------------
export const kLogin = '/login'
export const kNotYet = '/*'
export const kIndex = '/'
// ------------------------------ 用 户 服 务 ------------------------------

// ------------------------------ 系 统 服 务 ------------------------------

// 菜单管理
export const KMenuManager = '/sys/menu'
export const KMenuManagerChild = '/sys/menu/child/:menuId'
export const KMenuManagerFunc = '/sys/menu/child/:menuId/func/:id'

// 权限中心->角色
export const kUwRole = '/sys/role'

// ------------------------------ 系 统 服 务 ------------------------------

// ------------------------------ 业 务 系 统 ------------------------------
// 首页
export const kIndexMessage = '/business/home/index-message'
// 企业信息查询 --＞ 工商信息
export const QIndustry = '/business/enterprise/industry/Manager'
export const QIndustryView = '/business/enterprise/industry/view/:id'
// 企业信息查询 --＞ 对外贸易
export const QForeignTrader = '/business/enterprise/foreignTrader/Manager'
export const QForeignTraderView = '/business/enterprise/foreignTrader/view/:id'
// 信用信息管理 --＞ 失信企业管理
export const QLostLetter = '/business/credit/lostLetter/Manager'
export const QLostLetterView = '/business/credit/lostLetter/view/:id'

// ------------------------------ 业 务 系 统 ------------------------------
