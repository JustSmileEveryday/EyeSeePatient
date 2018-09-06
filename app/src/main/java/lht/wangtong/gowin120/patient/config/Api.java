package lht.wangtong.gowin120.patient.config;


import lht.wangtong.gowin120.patient.net.http.BaseApi;

/**
 * @author luoyc
 * @date 2016/10/27 0027
 */
public class Api {

    /**
     *     正式环境
     */
    private static final String HOST = "http://wappma.gowin120.com/";
    public static final String HOST_IMG = "http://wappma.gowin120.com/img/";
    public static final String HOST_IMG2 = "http://wappma.gowin120.com/img";

    /**
     * 测试环境
     */
//    private static final String HOST = "http://192.168.10.18:8087/";
//    public static final String HOST_IMG = "http://192.168.10.18:8087/img/";
//    public static final String HOST_IMG2 = "http://192.168.10.18:8087/img";

    //公共接口--------------------------------------------------------------------------
    // 数据字典
    final public static BaseApi COMMONCD = new BaseApi(HOST + "common/commonCd.do",
            "screen", false).addJsonKey("responseStr", "commonCdList");

    //版本检查
    final public static BaseApi GETVERSION = new BaseApi(HOST + "common/getVersion.do",
            "getVersion", false).addJsonKey("responseStr");

    //获取token
    final public static BaseApi APPLYTOKEN = new BaseApi(HOST + "applyToken.do",
            "applyToken", false).addJsonKey("responseStr");

    //登陆
    final public static BaseApi MEMBERLOGIN = new BaseApi(HOST + "mobile/memberLogin.do",
            "memberLogin", true).addJsonKey("responseStr");

    //快捷码检验登陆
    final public static BaseApi checkVlCode = new BaseApi(HOST + "mgr/checkVlCode.do",
            "checkVlCode", true).addJsonKey("responseStr");

    //退出登陆
    final public static BaseApi LOGOUT = new BaseApi(HOST + "account/logout.do",
            "logout", true).addJsonKey("responseStr");

    //注册获取验证码
    final public static BaseApi GETVALCODE = new BaseApi(HOST + "mgr/getValCode.do",
            "getValCode", true).addJsonKey("responseStr");

    //快捷登陆获取手机验证码
    final public static BaseApi verifiValCode = new BaseApi(HOST + "mgr/verifiValCode.do",
            "verifiValCode", true).addJsonKey("responseStr");

    //忘记密码获取验证码
    final public static BaseApi MGRGETVALCODE = new BaseApi(HOST + "mgr/mgrGetValCode.do",
            "mgrGetValCode", true).addJsonKey("responseStr");

    //注册
    final public static BaseApi REGISTER = new BaseApi(HOST + "reg/register.do",
            "register", true).addJsonKey("responseStr");

    //会员信息
    final public static BaseApi MEMBERINFO = new BaseApi(HOST + "account/memberInfo.do",
            "memberInfo", true).addJsonKey("responseStr");

    //公用-获取轮播图
    final public static BaseApi getBannerByType = new BaseApi(HOST + "common/getBannerByType.do",
            "getBannerByType", true).addJsonKey("responseStr", "bannerList");

    //成员管理-成员列表
    final public static BaseApi queryFamilyInfo = new BaseApi(HOST + "family/queryFamilyInfo.do",
            "queryFamilyInfo", true).addJsonKey("responseStr", "familyInfoList");

    //我的报告/报告查询-历史报告
    final public static BaseApi historyRadiographScreen = new BaseApi(HOST + "radiographScreen/historyRadiographScreen.do",
            "historyRadiographScreen", true).addJsonKey("responseStr", "radiographScreenList");

    // 获取预约服务列表
    final public static BaseApi queryReservationServiceList = new BaseApi(HOST + "member/queryReservationServiceList.do",
            "queryReservationServiceList", true).addJsonKey("responseStr", "reservationServiceList");

    // 获取预约服务详细信息
    final public static BaseApi viewReservationService = new BaseApi(HOST + "member/viewReservationService.do",
            "viewReservationService", true).addJsonKey("responseStr", "reservationServiceInfo");

    // 我的收藏
    final public static BaseApi myCollect = new BaseApi(HOST + "article/myCollect.do",
            "myCollect", true).addJsonKey("responseStr", "collectList");

    // 成员管理-新增/修改成员
    final public static BaseApi updateFamilyInfo = new BaseApi(HOST + "family/updateFamilyInfo.do",
            "updateFamilyInfo", true).addJsonKey("responseStr");

    // 成员管理-删除成员
    final public static BaseApi deleteFamilyInfo = new BaseApi(HOST + "family/deleteFamilyInfo.do",
            "deleteFamilyInfo", true).addJsonKey("responseStr");

    //修改头像
    final public static BaseApi UPDATEAVATAR = new BaseApi(HOST + "account/updateAvatar.do",
            "updateAvatar", true).addJsonKey("responseStr");

    //更新会员信息
    final public static BaseApi UPDATEMEMBER = new BaseApi(HOST + "account/updateMember.do",
            "updateMember", true).addJsonKey("responseStr");

    //搜索筛查点
    final public static BaseApi SearchAgent = new BaseApi(HOST + "common/searchAgent.do",
            "searchAgent", true).addJsonKey("responseStr", "agentList");

    //保存预约服务
    final public static BaseApi saveReservationService = new BaseApi(HOST + "member/saveReservationService.do",
            "saveReservationService", true).addJsonKey("responseStr");

    //保存眼健康数据
    final public static BaseApi saveEyeHealthData = new BaseApi(HOST + "member/saveEyeHealthData.do",
            "saveEyeHealthData", true).addJsonKey("responseStr");

    //获取成员数据信息
    final public static BaseApi getDataInfo = new BaseApi(HOST + "member/getDataInfo.do",
            "getDataInfo", true).addJsonKey("responseStr");

    //获取眼保健操打卡次数
    final public static BaseApi getSigninCount = new BaseApi(HOST + "member/getSigninCount.do",
            "getSigninCount", true).addJsonKey("responseStr");

    //获取眼保健操打卡列表
    final public static BaseApi getSigninList = new BaseApi(HOST + "member/getSigninList.do",
            "getSigninList", true).addJsonKey("responseStr", "signinDataList");

    //眼保健操打卡
    final public static BaseApi saveSignin = new BaseApi(HOST + "member/saveSignin.do",
            "saveSignin", true).addJsonKey("responseStr");

    //129 获取护眼计划列表
    final public static BaseApi getPlanScheduleList = new BaseApi(HOST + "member/getPlanScheduleList.do",
            "getPlanScheduleList", true).addJsonKey("responseStr", "planScheduleList");

    //改变护眼计划状态
    final public static BaseApi changePlanScheduleStatus = new BaseApi(HOST + "member/changePlanScheduleStatus.do",
            "changePlanScheduleStatus", true).addJsonKey("responseStr");

    //新增/修改护眼计划
    final public static BaseApi updatePlanSchedule = new BaseApi(HOST + "member/updatePlanSchedule.do",
            "updatePlanSchedule", true).addJsonKey("responseStr");

    //护眼计划详细信息
    final public static BaseApi viewPlanSchedule = new BaseApi(HOST + "member/viewPlanSchedule.do",
            "viewPlanSchedule", true).addJsonKey("responseStr", "planScheduleInfo");

    //最新眼健康数据
    final public static BaseApi getNewestEyeHealthData = new BaseApi(HOST + "member/getNewestEyeHealthData.do",
            "getNewestEyeHealthData", true).addJsonKey("responseStr");

    //获取眼健康数据
    final public static BaseApi getEyeHealthData = new BaseApi(HOST + "member/getEyeHealthData.do",
            "getEyeHealthData", true).addJsonKey("responseStr", "eyeHealthDataList");

    //获取预约服务数据
    final public static BaseApi getReservationServiceData = new BaseApi(HOST + "member/getReservationServiceData.do",
            "getReservationServiceData", true).addJsonKey("responseStr", "reservationServiceDataList");

    //意见反馈
    final public static BaseApi saveAdviceFeedback = new BaseApi(HOST + "member/saveAdviceFeedback.do",
            "saveAdviceFeedback", true).addJsonKey("responseStr");

    //医院详情
    final public static BaseApi GETHOSPITALBYID = new BaseApi(HOST + "account/getHospitalById.do",
            "getHospitalById", true).addJsonKey("responseStr");

    //重置密码
    final public static BaseApi MGREDITPASSWORD = new BaseApi(HOST + "/mgr/mgrEditPassword.do",
            "mgrEditPassword", true).addJsonKey("responseStr");

    //消息中心-消息列表
    final public static BaseApi getMyMessageList = new BaseApi(HOST + "/mobile/getMyMessageList.do",
            "getMyMessageList", true).addJsonKey("responseStr", "msgList");

    //获取健康资讯目录
    final public static BaseApi GetHealthNewsCatalog = new BaseApi(HOST + "main/getHealthNewsCatalog.do",
            "getHealthNewsByCatalog", true).addJsonKey("responseStr", "catalogList");

    //根据目录获取健康资讯
    final public static BaseApi getHealthNewsByCatalog = new BaseApi(HOST + "main/getHealthNewsByCatalog.do",
            "getHealthNewsByCatalog", true).addJsonKey("responseStr", "healthNewsList");

    // 保存呼叫服务
    final public static BaseApi saveServiceRecord = new BaseApi(HOST + "member/saveServiceRecord.do",
            "saveServiceRecord", true).addJsonKey("responseStr");

    //增加资讯分享次数
    final public static BaseApi addArticleShareCount = new BaseApi(HOST + "article/addArticleShareCount.do",
            "addArticleShareCount", true).addJsonKey("responseStr");

    //名词解释详情
    final public static BaseApi VIEWARTICLE = new BaseApi(HOST + "article/viewArticle.do",
            "viewArticle", true).addJsonKey("responseStr", "articleInfo");

    //115 获取文章评论列表
    final public static BaseApi getArticleComments = new BaseApi(HOST + "article/getArticleComments.do",
            "getArticleComments", true).addJsonKey("responseStr", "articleCommentList");

    //111 收藏文章
    final public static BaseApi collectArticle = new BaseApi(HOST + "article/collectArticle.do",
            "collectArticle", true).addJsonKey("responseStr", "favorId");

    //114 评论文章
    final public static BaseApi commentArticle = new BaseApi(HOST + "article/commentArticle.do",
            "commentArticle", true).addJsonKey("responseStr");

    //116 点赞评论
    final public static BaseApi supportComment = new BaseApi(HOST + "article/supportComment.do",
            "supportComment", true).addJsonKey("responseStr");

    //113 取消收藏
    final public static BaseApi deleteCollect = new BaseApi(HOST + "article/deleteCollect.do",
            "deleteCollect", true).addJsonKey("responseStr");

    // 呼叫服务活动列表
    final public static BaseApi getAllService = new BaseApi(HOST + "member/getAllService.do",
            "getAllService", true).addJsonKey("responseStr", "serviceList");

    //获取app对应的支付方式
    final public static BaseApi getAppPaymentType = new BaseApi(HOST + "payment/getAppPaymentType.do",
            "getAppPaymentType", true).addJsonKey("responseStr", "paymentTypeList");

    //获取支付参数
    final public static BaseApi appToPayment = new BaseApi(HOST + "payment/appToPayment.do",
            "appToPayment", true).addJsonKey("responseStr");

    //查看呼叫服务详情
    final public static BaseApi viewServiceRecord = new BaseApi(HOST + "member/viewServiceRecord.do",
            "viewServiceRecord", true).addJsonKey("responseStr", "serviceRecordInfo");

    //服务详情
    final public static BaseApi viewMarketActivity = new BaseApi(HOST + "member/viewMarketActivity.do",
            "viewMarketActivity", true).addJsonKey("responseStr", "marketActivityInfo");

    //54 新-保存呼叫服务
    final public static BaseApi saveServiceRecordNew = new BaseApi(HOST + "member/saveServiceRecordNew.do",
            "saveServiceRecordNew", true).addJsonKey("responseStr");

    //我的服务
    final public static BaseApi queryServiceRecord = new BaseApi(HOST + "member/queryServiceRecord.do",
            "queryServiceRecord", true).addJsonKey("responseStr", "serviceRecordList");

    //获取猜你想问列表
    final public static BaseApi getIllnessQuestionList = new BaseApi(HOST + "consult/getIllnessQuestionList.do",
            "getIllnessQuestionList", true).addJsonKey("responseStr", "illnessQuestionList");

    //获取猜你想问详细信息
    final public static BaseApi viewIllnessQuestion = new BaseApi(HOST + "consult/viewIllnessQuestion.do",
            "viewIllnessQuestion", true).addJsonKey("responseStr", "illnessQuestionInfo");

    //猜你想问是否有用保存
    final public static BaseApi saveViewIllnessQuestion = new BaseApi(HOST + "consult/saveIllnessQuestionHelpfulCount.do",
            "viewIllnessQuestion", true).addJsonKey("responseStr");

    //获取机器人回答信息
    final public static BaseApi getRobotAnswer = new BaseApi(HOST + "consult/getRobotAnswer.do",
            "getRobotAnswer", true).addJsonKey("responseStr");

    //获取咨询运营人员/医生腾讯信息
    final public static BaseApi getConsultEmployeeInfo = new BaseApi(HOST + "consult/getConsultEmployeeInfo.do",
            "getConsultEmployeeInfo", true).addJsonKey("responseStr");

    //患者获取咨询医生/运营人员列表
    final public static BaseApi getMemberConsultList = new BaseApi(HOST + "consult/getMemberConsultList.do",
            "getMemberConsultList", true).addJsonKey("responseStr", "memberConsultList");

    //获取评价标签列表
    final public static BaseApi getCommentLabelList = new BaseApi(HOST + "consult/getCommentLabelList.do",
            "getCommentLabelList", true).addJsonKey("responseStr", "commentLabelList");

    //患者新增咨询记录
    final public static BaseApi saveRemoteConsultation = new BaseApi(HOST + "consult/saveRemoteConsultation.do",
            "saveRemoteConsultation", true).addJsonKey("responseStr");

    //患者获取未完成的咨询记录
    final public static BaseApi getMemberNoCompleteConsult = new BaseApi(HOST + "consult/getMemberNoCompleteConsult.do",
            "getMemberNoCompleteConsult", true).addJsonKey("responseStr");

    //2.根据腾讯id获取用户信息
    final public static BaseApi getUserInfoByTencent = new BaseApi(HOST + "common/getUserInfoByTencent.do",
            "getUserInfoByTencent", true).addJsonKey("responseStr", "userInfo");

    //149 保存咨询记录评论
    final public static BaseApi saveRemoteConsultationComment = new BaseApi(HOST + "consult/saveRemoteConsultationComment.do",
            "saveRemoteConsultationComment", true).addJsonKey("responseStr");

    // 33 医生-患者评价列表
    final public static BaseApi getDoctorInteractComment = new BaseApi(HOST + "doctor/getDoctorInteractComment.do",
            "getDoctorInteractComment", true).addJsonKey("responseStr", "interactCommentList");

    // 32 医生详情
    final public static BaseApi getDoctorInfo = new BaseApi(HOST + "doctor/doctorInfo.do",
            "docInfo", true).addJsonKey("responseStr", "doctorInfo");

    // 152 查询预约类型列表
    final public static BaseApi queryReservationTypeList = new BaseApi(HOST + "reservationType/queryReservationTypeList.do",
            "queryReservationTypeList", true).addJsonKey("responseStr", "reservationTypeList");

    // 150 查询活动列表
    final public static BaseApi queryActivityList = new BaseApi(HOST + "activity/queryActivityList.do",
            "queryActivityList", true).addJsonKey("responseStr", "activityList");

    // 151 活动详情
    final public static BaseApi viewActivityInfo = new BaseApi(HOST + "activity/viewActivityInfo.do",
            "viewActivityInfo", true).addJsonKey("responseStr", "activityInfo");


    //报告查询-最新报告/报告详情
    final public static BaseApi viewRadiographScreen = new BaseApi(HOST + "radiographScreen/viewRadiographScreen.do",
            "docInfo", true).addJsonKey("responseStr", "radiographScreenInfo");

    //余额明细
    final public static BaseApi queryMemberAccountIo = new BaseApi(HOST + "member/queryMemberAccountIo.do",
            "queryMemberAccountIo", true).addJsonKey("responseStr", "memberAccountIoList");

    //余额详情
    final public static BaseApi viewMemberAccountIo = new BaseApi(HOST + "member/viewMemberAccountIo.do",
            "viewMemberAccountIo", true).addJsonKey("responseStr", "memberAccountIoInfo");

    //充值积分列表
    final public static BaseApi queryRechargeIntegral = new BaseApi(HOST + "member/queryRechargeIntegral.do",
            "queryRechargeIntegral", true).addJsonKey("responseStr", "rechargeIntegralList");

    //充值
    final public static BaseApi appToRecharge = new BaseApi(HOST + "payment/appToRecharge.do",
            "appToRecharge", true).addJsonKey("responseStr");

    //153 查询会员积分明细
    final public static BaseApi queryMemberPointIo = new BaseApi(HOST + "member/queryMemberPointIo.do",
            "queryMemberPointIo", true).addJsonKey("responseStr", "memberPointIoList");

    //156 获取签到列表
    final public static BaseApi getMemberSigninList = new BaseApi(HOST + "memberSign/getSigninList.do",
            "getSigninList", true).addJsonKey("responseStr", "signinDataList");

    //157 获取今日签到情况
    final public static BaseApi getTodaySignStatus = new BaseApi(HOST + "memberSign/getTodaySignStatus.do",
            "getTodaySignStatus", true).addJsonKey("responseStr", "isSign");

    // 155 会员签到
    final public static BaseApi signIn = new BaseApi(HOST + "memberSign/signIn.do",
            "signIn", true).addJsonKey("responseStr");

    //158 获取可领取优惠卷列表
    final public static BaseApi getCanGetCouponList = new BaseApi(HOST + "member/getCanGetCouponList.do",
            "getCanGetCouponList", true).addJsonKey("responseStr");

    //159 领取优惠卷
    final public static BaseApi getCoupon = new BaseApi(HOST + "member/getCoupon.do",
            "getCoupon", true).addJsonKey("responseStr");

    //160 获取我的优惠卷列表
    final public static BaseApi getMyCouponList = new BaseApi(HOST + "member/getMyCouponList.do",
            "getMyCouponList", true).addJsonKey("responseStr");

    //161 获取推荐服务列表
    final public static BaseApi getRadiographScreenMarketActivityList = new BaseApi(HOST + "radiographScreen/getRadiographScreenMarketActivityList.do",
            "getRadiographScreenMarketActivityList", true).addJsonKey("responseStr", "marketActivityList");

    //162 获取试镜间眼镜列表
    final public static BaseApi getGlassesList = new BaseApi(HOST + "member/getGlassesList.do",
            "getGlassesList", true).addJsonKey("responseStr", "glassesList");

    //163 获取眼龄问题
    final public static BaseApi getEyeAgeProblem = new BaseApi(HOST + "eyeAgeProblem/getEyeAgeProblem.do",
            "getEyeAgeProblem", true).addJsonKey("responseStr", "eyeAgeProblemList");

    //164 获取眼龄测试结果
    final public static BaseApi getEyeAgeProblemResult = new BaseApi(HOST + "eyeAgeProblem/getEyeAgeProblemResult.do",
            "getEyeAgeProblemResult", true).addJsonKey("responseStr");

    //167 保存地址
    final public static BaseApi addAppPersonalAddr = new BaseApi(HOST + "member/addr/addAppPersonalAddr.do",
            "addAppPersonalAddr", true).addJsonKey("responseStr");

    //170 删除地址
    final public static BaseApi delAppPersonalAddr = new BaseApi(HOST + "member/addr/delAppPersonalAddr.do",
            "delAppPersonalAddr", true).addJsonKey("responseStr");

    //168 地址列表
    final public static BaseApi getAppMemberAddrList = new BaseApi(HOST + "member/addr/getAppMemberAddrList.do",
            "getAppMemberAddrList", true).addJsonKey("responseStr", "memberAddrList");

    //171 查询是否有未处理完的申请报告
    final public static BaseApi queryReportApplyStatus = new BaseApi(HOST + "member/queryReportApplyStatus.do",
            "queryReportApplyStatus", true).addJsonKey("responseStr","isCanApply");

    //172 保存申请报告
    final public static BaseApi saveRadiographScreenReportApply = new BaseApi(HOST + "member/saveRadiographScreenReportApply.do",
            "saveRadiographScreenReportApply", true).addJsonKey("responseStr");


    //1.获取系统编码的值
    final public static BaseApi getSystemParamBykey = new BaseApi(HOST + "common/getSystemParamBykey.do",
            "getSystemParamBykey", true).addJsonKey("responseStr");


}
