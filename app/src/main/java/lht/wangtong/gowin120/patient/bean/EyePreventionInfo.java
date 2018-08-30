package lht.wangtong.gowin120.patient.bean;

/**
 *
 * @author luoyc
 * @date 2018/3/20
 */

public class EyePreventionInfo {

    private String title;
    private String content;
    private int drawableId1;
    private int drawableId2;

    public EyePreventionInfo(String title, String content, int drawableId1, int drawableId2) {
        this.title = title;
        this.content = content;
        this.drawableId1 = drawableId1;
        this.drawableId2 = drawableId2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDrawableId1() {
        return drawableId1;
    }

    public void setDrawableId1(int drawableId1) {
        this.drawableId1 = drawableId1;
    }

    public int getDrawableId2() {
        return drawableId2;
    }

    public void setDrawableId2(int drawableId2) {
        this.drawableId2 = drawableId2;
    }
}
