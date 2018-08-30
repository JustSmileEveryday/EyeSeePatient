package lht.wangtong.gowin120.patient.bean;

import java.util.List;

/**
 * @author luoyc
 * @date 2018/3/13
 */

public class ReportInfo {

    /**
     * memberPhone : 13700000004
     * doctorAdvice : null
     * doctorQM : /user_head/18300/original/ZYZ_18300_1121145708195427.png
     * leftImg : /radiograph_screen/11062/2960/reportImgs/reportImgs_admin_1122111813346627.jpg
     * activityId : 0
     * relationshipType : 0
     * leftPictureQuality :
     * diagnoseDate : 2016-11-29 15:47
     * isFundusProblemR : N
     * diabeticRetinopathyR : null
     * memberName : 四四
     * diabeticRetinopathyL : Y
     * isFundusProblemL : N
     * glaucomaConcernsL : Y
     * formStatus : 2
     * checkInDate : 2016-11-22 11:18
     * status : 2
     * execStatus : 3
     * glaucomaConcernsR : null
     * rightImg : /radiograph_screen/11062/2960/reportImgs/reportImgs_admin_1122111813570120.jpg
     * maculopathyR : null
     * memberAge : 42
     * maculopathyL : Y
     * pdfUrl : /print/radiographScreen_print/2_pc_161129154753393_RS-161122-2390.pdf
     * rightFocusMarkList : []
     * applyDate : 2016-11-22
     * leftFocusMarkList : []
     * pdfImgUrl : /pdfImg/rspdfimg/20170310/2_pc_170310144929546_RS-161122-2390_1.jpg
     * radiographScreenId : 2960
     * memberSex : 1
     * agentName : 测试筛查点01
     * hospitalName : 卫视
     * rightPictureQuality :
     * memberId : 11062
     * comprehensiveReportPdfImgUrl : null
     */

    private String memberPhone;
    private String doctorAdvice;
    private String doctorQM;
    private String leftImg;
    private String activityId;
    private String relationshipType;
    private String leftPictureQuality;
    private String diagnoseDate;
    private String isFundusProblemR;
    private String diabeticRetinopathyR;
    private String memberName;
    private String diabeticRetinopathyL;
    private String isFundusProblemL;
    private String glaucomaConcernsL;
    private String formStatus;
    private String checkInDate;
    private String status;
    private String execStatus;
    private String glaucomaConcernsR;
    private String rightImg;
    private String maculopathyR;
    private int memberAge;
    private String maculopathyL;
    private String pdfUrl;
    private String applyDate;
    private String pdfImgUrl;
    private int radiographScreenId;
    private String memberSex;
    private String agentName;
    private String hospitalName;
    private String rightPictureQuality;
    private int memberId;
    private String comprehensiveReportPdfImgUrl;
    private List<?> rightFocusMarkList;
    private List<?> leftFocusMarkList;
    private int isRead;
    private String positiveResultText;
    private String comprehensiveResultText;
    private String eyecareAdviceText;
    private String eyeseeSceneImgUrl;
    private List<LeftAndRightCheckBean> leftAndRightCheck;
    private List<NoLeftAndRightCheckBean> noLeftAndRightCheck;
    private String familyId;
    private String reportTemplateType;

    public String getReportTemplateType() {
        return reportTemplateType;
    }

    public void setReportTemplateType(String reportTemplateType) {
        this.reportTemplateType = reportTemplateType;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getPositiveResultText() {
        return positiveResultText;
    }

    public void setPositiveResultText(String positiveResultText) {
        this.positiveResultText = positiveResultText;
    }

    public String getComprehensiveResultText() {
        return comprehensiveResultText;
    }

    public void setComprehensiveResultText(String comprehensiveResultText) {
        this.comprehensiveResultText = comprehensiveResultText;
    }

    public String getEyecareAdviceText() {
        return eyecareAdviceText;
    }

    public void setEyecareAdviceText(String eyecareAdviceText) {
        this.eyecareAdviceText = eyecareAdviceText;
    }

    public String getEyeseeSceneImgUrl() {
        return eyeseeSceneImgUrl;
    }

    public void setEyeseeSceneImgUrl(String eyeseeSceneImgUrl) {
        this.eyeseeSceneImgUrl = eyeseeSceneImgUrl;
    }

    public List<LeftAndRightCheckBean> getLeftAndRightCheck() {
        return leftAndRightCheck;
    }

    public void setLeftAndRightCheck(List<LeftAndRightCheckBean> leftAndRightCheck) {
        this.leftAndRightCheck = leftAndRightCheck;
    }

    public List<NoLeftAndRightCheckBean> getNoLeftAndRightCheck() {
        return noLeftAndRightCheck;
    }

    public void setNoLeftAndRightCheck(List<NoLeftAndRightCheckBean> noLeftAndRightCheck) {
        this.noLeftAndRightCheck = noLeftAndRightCheck;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getDoctorAdvice() {
        return doctorAdvice;
    }

    public void setDoctorAdvice(String doctorAdvice) {
        this.doctorAdvice = doctorAdvice;
    }

    public String getDoctorQM() {
        return doctorQM;
    }

    public void setDoctorQM(String doctorQM) {
        this.doctorQM = doctorQM;
    }

    public String getLeftImg() {
        return leftImg;
    }

    public void setLeftImg(String leftImg) {
        this.leftImg = leftImg;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getLeftPictureQuality() {
        return leftPictureQuality;
    }

    public void setLeftPictureQuality(String leftPictureQuality) {
        this.leftPictureQuality = leftPictureQuality;
    }

    public String getDiagnoseDate() {
        return diagnoseDate;
    }

    public void setDiagnoseDate(String diagnoseDate) {
        this.diagnoseDate = diagnoseDate;
    }

    public String getIsFundusProblemR() {
        return isFundusProblemR;
    }

    public void setIsFundusProblemR(String isFundusProblemR) {
        this.isFundusProblemR = isFundusProblemR;
    }

    public String getDiabeticRetinopathyR() {
        return diabeticRetinopathyR;
    }

    public void setDiabeticRetinopathyR(String diabeticRetinopathyR) {
        this.diabeticRetinopathyR = diabeticRetinopathyR;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getDiabeticRetinopathyL() {
        return diabeticRetinopathyL;
    }

    public void setDiabeticRetinopathyL(String diabeticRetinopathyL) {
        this.diabeticRetinopathyL = diabeticRetinopathyL;
    }

    public String getIsFundusProblemL() {
        return isFundusProblemL;
    }

    public void setIsFundusProblemL(String isFundusProblemL) {
        this.isFundusProblemL = isFundusProblemL;
    }

    public String getGlaucomaConcernsL() {
        return glaucomaConcernsL;
    }

    public void setGlaucomaConcernsL(String glaucomaConcernsL) {
        this.glaucomaConcernsL = glaucomaConcernsL;
    }

    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(String execStatus) {
        this.execStatus = execStatus;
    }

    public String getGlaucomaConcernsR() {
        return glaucomaConcernsR;
    }

    public void setGlaucomaConcernsR(String glaucomaConcernsR) {
        this.glaucomaConcernsR = glaucomaConcernsR;
    }

    public String getRightImg() {
        return rightImg;
    }

    public void setRightImg(String rightImg) {
        this.rightImg = rightImg;
    }

    public String getMaculopathyR() {
        return maculopathyR;
    }

    public void setMaculopathyR(String maculopathyR) {
        this.maculopathyR = maculopathyR;
    }

    public int getMemberAge() {
        return memberAge;
    }

    public void setMemberAge(int memberAge) {
        this.memberAge = memberAge;
    }

    public String getMaculopathyL() {
        return maculopathyL;
    }

    public void setMaculopathyL(String maculopathyL) {
        this.maculopathyL = maculopathyL;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getPdfImgUrl() {
        return pdfImgUrl;
    }

    public void setPdfImgUrl(String pdfImgUrl) {
        this.pdfImgUrl = pdfImgUrl;
    }

    public int getRadiographScreenId() {
        return radiographScreenId;
    }

    public void setRadiographScreenId(int radiographScreenId) {
        this.radiographScreenId = radiographScreenId;
    }

    public String getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(String memberSex) {
        this.memberSex = memberSex;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getRightPictureQuality() {
        return rightPictureQuality;
    }

    public void setRightPictureQuality(String rightPictureQuality) {
        this.rightPictureQuality = rightPictureQuality;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getComprehensiveReportPdfImgUrl() {
        return comprehensiveReportPdfImgUrl;
    }

    public void setComprehensiveReportPdfImgUrl(String comprehensiveReportPdfImgUrl) {
        this.comprehensiveReportPdfImgUrl = comprehensiveReportPdfImgUrl;
    }

    public List<?> getRightFocusMarkList() {
        return rightFocusMarkList;
    }

    public void setRightFocusMarkList(List<?> rightFocusMarkList) {
        this.rightFocusMarkList = rightFocusMarkList;
    }

    public List<?> getLeftFocusMarkList() {
        return leftFocusMarkList;
    }

    public void setLeftFocusMarkList(List<?> leftFocusMarkList) {
        this.leftFocusMarkList = leftFocusMarkList;
    }

    public static class LeftAndRightCheckBean {
        private String name;
        private String rightValue;
        private String leftValue;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRightValue() {
            return rightValue;
        }

        public void setRightValue(String rightValue) {
            this.rightValue = rightValue;
        }

        public String getLeftValue() {
            return leftValue;
        }

        public void setLeftValue(String leftValue) {
            this.leftValue = leftValue;
        }
    }

    public static class NoLeftAndRightCheckBean {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
