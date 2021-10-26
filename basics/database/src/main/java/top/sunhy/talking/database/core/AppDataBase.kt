package top.sunhy.talking.database.core

import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import top.sunhy.base.application.BaseApplication
import top.sunhy.talking.database.Configs
import top.sunhy.talking.database.dao.UserDao
import top.sunhy.talking.entity.UserBean

@Database(
    entities = [
        UserBean::class,
    ],
    version = Configs.DB_VERSION
)
@TypeConverters(
    value = []
)
abstract class AppDataBase : RoomDatabase() {


    abstract fun userDao(): UserDao

    companion object {
        private const val TAG = "AppDataBase"
        private var appDatabase: AppDataBase? = null
        val instance = get()

        fun get(): AppDataBase {
            if (appDatabase == null) {
                synchronized(AppDataBase::class.java) {
                    var builder = Room.databaseBuilder(
                        BaseApplication.instance(),
                        AppDataBase::class.java, Configs.DB_NAME
                    )
                    builder.allowMainThreadQueries()
                    builder.addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.e(TAG, "xmx数据库version:${db.version}, onCreate")
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            Log.e(TAG, "xmx数据库version:${db.version}, onOpen")
                        }

                        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                            super.onDestructiveMigration(db)
                            Log.e(TAG, "xmx数据库version:${db.version}, onDestructiveMigration")
                        }
                    })
                    /*if (BaseApplication.isDebug()) {
                        // 迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
                        builder.fallbackToDestructiveMigration()
                    }
                    builder.addMigrations(object : Migration(1, 2) {
                        override fun migrate(database: SupportSQLiteDatabase) {
                            Log.e(TAG, "xmx数据库升级：1 --> 2")
                            database.execSQL("DROP TABLE IF EXISTS ${Tables.APP_CONFIG} ;")
                        }
                    })
                    appDatabase = builder.build()*/
                }
            }
            return appDatabase!!
        }
    }

}
