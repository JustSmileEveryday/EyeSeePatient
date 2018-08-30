package lht.wangtong.gowin120.patient.bean;

/**
 * 筛查点
 * Created by luoyc on 2017/6/20.
 */

public class AgentInfo {
    /**
     * agentName : 非测试02
     * agentId : 3181
     */

    private String agentName;
    private String agentId;
    private boolean isCheck = false;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

}
