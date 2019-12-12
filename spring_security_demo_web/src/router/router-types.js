// ------------------------------ 用 户 服 务 ------------------------------
export const kLogin = '/login'
export const kRegister = '/register'
export const kForgetPassword = '/forgetPassword'
export const kNotYet = '/*'
export const kIndex = '/'
// ------------------------------ 用 户 服 务 ------------------------------

// ------------------------------ 系 统 服 务 ------------------------------

// 系统管理 -> 菜单管理
export const KMenuManager = '/sys/menu'
export const KMenuManagerChild = '/sys/menu/child/:menuId'
export const KMenuManagerFunc = '/sys/menu/child/:menuId/func/:id'

// 系统管理 -> 用户
export const kUserManager = '/sys/user'
// 系统管理 -> 日志
export const kLogManager = '/sys/log'
// 系统管理 -> 角色
export const kUwRole = '/sys/role'

// ------------------------------ 系 统 服 务 ------------------------------
