package lht.wangtong.gowin120.patient.bean;

import java.util.Date;

/**
 * 自动回复消息展示
 *
 * @author luoyc
 */
public class AutoMessage {
    private String answer;
    private String question;
    private boolean isSelf = false;
    private String time;
    private Date date;
    private boolean hasTime = false;

    public boolean isHasTime() {
        return hasTime;
    }

    public void setHasTime(boolean hasTime) {
        this.hasTime = hasTime;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AutoMessage{" +
                "answer='" + answer + '\'' +
                ", question='" + question + '\'' +
                ", isSelf=" + isSelf +
                ", time='" + time + '\'' +
                ", date=" + date +
                ", hasTime=" + hasTime +
                '}';
    }
}
