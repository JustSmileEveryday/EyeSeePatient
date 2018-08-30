package lht.wangtong.gowin120.patient.db;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * token
 * Created by luoyc on 2016/10/28 0028.
 */
@Table(database = AppDatabase.class) //上面自己创建的类（定义表的名称 版本）
public class Token extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)//ID自增
    private long Id;
    @Column(name = "accessToken")
    private String accessToken;//令牌
    @Column(name = "expiresIn")
    private String expiresIn;//过期时间


    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
