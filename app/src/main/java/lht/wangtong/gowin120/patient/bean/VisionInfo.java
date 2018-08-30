package lht.wangtong.gowin120.patient.bean;


/**
 *
 * 视力测试
 * @author luoyc
 * @date 2018/3/19
 */

public class VisionInfo {
    private String name;
    private float scale;
    private int drawableId;
    private int drawableId2;
    private int direction; //0上 1下 2左 3右
    private int direction2; //0上 1下 2左 3右

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDrawableId2() {
        return drawableId2;
    }

    public void setDrawableId2(int drawableId2) {
        this.drawableId2 = drawableId2;
    }

    public int getDirection2() {
        return direction2;
    }

    public void setDirection2(int direction2) {
        this.direction2 = direction2;
    }
}
