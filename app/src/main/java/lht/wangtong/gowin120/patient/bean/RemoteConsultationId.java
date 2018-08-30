package lht.wangtong.gowin120.patient.bean;

/**
 * @author luoyc
 */
public class RemoteConsultationId {
    //咨询表主键,非空表示还有未完成的咨询
    private String remoteConsultationId;
    //需要评价的咨询表主键,非空表示可以评价
    private String commentRemoteConsultationId;

    public String getRemoteConsultationId() {
        return remoteConsultationId;
    }

    public void setRemoteConsultationId(String remoteConsultationId) {
        this.remoteConsultationId = remoteConsultationId;
    }

    public String getCommentRemoteConsultationId() {
        return commentRemoteConsultationId;
    }

    public void setCommentRemoteConsultationId(String commentRemoteConsultationId) {
        this.commentRemoteConsultationId = commentRemoteConsultationId;
    }
}
