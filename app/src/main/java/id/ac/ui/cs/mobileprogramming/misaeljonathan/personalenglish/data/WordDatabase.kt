package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Word::class], version = 2, exportSchema = false)
abstract class WordDatabase: RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {

        @Volatile
        private var instance: WordDatabase? = null

        fun getInstance(context: Context) =
            instance
                ?: synchronized(this) {
                instance
                    ?: Room.databaseBuilder(
                    context.getApplicationContext(),
                    WordDatabase::class.java,
                    "word_database"
                )
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                .build()
            }
    }
}