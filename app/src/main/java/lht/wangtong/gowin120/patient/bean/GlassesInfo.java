package lht.wangtong.gowin120.patient.bean;

import android.graphics.drawable.Drawable;

/**
 * 眼镜info
 *
 * @author luoyc
 */
public class GlassesInfo {

    private String picUrl;
    private String taobaoId;
    private Drawable drawable;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTaobaoId() {
        return taobaoId;
    }

    public void setTaobaoId(String taobaoId) {
        this.taobaoId = taobaoId;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
