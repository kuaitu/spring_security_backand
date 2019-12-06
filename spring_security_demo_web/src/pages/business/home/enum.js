// 数组表示当前路径页面存在tabs，参数1为页面名字，参数2为页面内tabs对应的名字
export const ROUTER_NAME = {
  complaint: 'ComplaintMenager', // 投诉
  train_policy: '', // 培训政策
  train_info: '', // 培训政策
  train_organization: '', // 培训机构
  check_record: {
    '01': ['SpotCheckManagement', 'checkRecord'], // 房屋监督抽查管理
    '02': 'MuniSpotCheckManagement' // 市政监督抽查记录
  },
  disclosure: {
    '01': 'ImpartManagement', // 房屋交底报告
    '02': 'MuniImpartManagement' // 市政交底报告
  },
  spot_check: ['SpotCheckManagement', 'spotCheck'], // 监督抽测
  part_check: ['ProjectAcceptanceManagement', 'projectPartCheck'], // 分部工程监督抽查记录
  part_supervise: ['ProjectAcceptanceManagement', 'projectPartSupervise'], // 分部工程验收现场监督记录
  finish_check: ['ProjectAcceptanceManagement', 'projectFinishCheck'], // 竣工工程验收监督抽查记录
  finish_supervise: ['ProjectAcceptanceManagement', 'projectFinishSupervise'], // 竣工工程验收现场监督记录
  reform_notify: {
    '01': ['NoticManagement', 'RectificationNotice'], // 房屋整改通知
    '02': ['MunNoticManagement', 'RectificationNotice'] // 市政整改通知
  },
  reform_reply: {
    '01': ['NoticManagement', 'RectificationNotice'], // 房屋整改回复
    '02': ['MunNoticManagement', 'RectificationNotice'] // 市政整改回复
  },
  pause_notify: {
    '01': ['NoticManagement', 'ShutdownNotice'], // 房屋停工通知
    '02': ['MunNoticManagement', 'ShutdownNotice'] // 市政停工通知
  },
  resume_application: {
    '01': ['NoticManagement', 'ShutdownNotice'], // 房屋复工申请"
    '02': ['MunNoticManagement', 'ShutdownNotice'] // 市政复工申请"
  },
  resume_notify: {
    '01': ['NoticManagement', 'ArchReworkNotice'], // 房屋复工通知
    '02': ['MunNoticManagement', 'ArchReworkNotice'] // 市政复工通知
  },
  punish_notify: {
    '01': ['NoticManagement', 'ArchPunishRecommendation'], // 房屋行政处罚通知
    '02': ['MunNoticManagement', 'ArchPunishRecommendation'] // 市政行政处罚通知
  },

  standard_execute: 'ArchMandatoryStandards', // 工程建设强制性标准执行情况检查
  corp_behaviour_check: 'ArchQualityBehavior', // 项目质量行为检查
  literature: 'LiteratureManager', // 资料库管理
  prj_plan: {
    '01': 'SupervisionPlan', // 房屋监督计划管理
    '02': 'MuniSupervisionPlan' // 市政监督计划管理
  },
  supervision_report: {
    '01': 'SupervisionReport', // 房屋监督报告管理
    '02': 'MuniSupervisionReport' // 市政监督报告管理
  },
  municipal_acceptance: 'MunicipalSubprojectFinish', // 市政分部竣工验收记录
  project_assign: {
    '01': 'BuildingSupervisionTeam', // 房屋监督小组
    '02': 'MunicipalSupervisionTeam' // 市政监督小组
  }
}
