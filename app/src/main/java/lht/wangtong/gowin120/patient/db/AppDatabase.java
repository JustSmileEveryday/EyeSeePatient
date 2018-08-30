package lht.wangtong.gowin120.patient.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 *
 * @author luoyc
 * @date 2018/3/8
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME = "EyeSeePatient-db";

    public static final int VERSION = 2;
}
