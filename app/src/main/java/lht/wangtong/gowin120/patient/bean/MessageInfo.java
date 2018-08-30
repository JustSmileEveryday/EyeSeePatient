package lht.wangtong.gowin120.patient.bean;

/**
 *
 * @author luoyc
 * @date 2018/3/27
 */

public class MessageInfo {

    /**
     * busiId : 0
     * busiType : 3
     * busiTypeName : 引流消息
     * messageContent : 您的最新眼底拍片筛查报告已生成，请进入“查报告”页查看报告详情。
     * messageDate : 2017-05-19 11:24
     * messageId : 17045
     * messageRead : false
     * messageType :
     */

    private String busiId;
    private int busiType;
    private String busiTypeName;
    private String messageContent;
    private String messageDate;
    private String messageId;
    private boolean messageRead;
    private String messageType;

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }

    public int getBusiType() {
        return busiType;
    }

    public void setBusiType(int busiType) {
        this.busiType = busiType;
    }

    public String getBusiTypeName() {
        return busiTypeName;
    }

    public void setBusiTypeName(String busiTypeName) {
        this.busiTypeName = busiTypeName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public boolean isMessageRead() {
        return messageRead;
    }

    public void setMessageRead(boolean messageRead) {
        this.messageRead = messageRead;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
