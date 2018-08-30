package lht.wangtong.gowin120.patient.db;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * @author luoyc
 */
@Table(database = AppDatabase.class)
public class HistoryInfo extends BaseModel{
    //ID自增
    @Column
    @PrimaryKey(autoincrement = true)
    private long Id;
    @Column(name = "history")
    private String history;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
