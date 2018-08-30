package lht.wangtong.gowin120.patient.db;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * user表新增列名
 * @author luoyc
 */
@Migration(version = 2, database = AppDatabase.class)
public class Migration3 extends AlterTableMigration<User>{
    public Migration3(Class<User> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        addColumn(SQLiteType.TEXT,"signinTimes");
        addColumn(SQLiteType.TEXT,"isLogined");
    }
}
