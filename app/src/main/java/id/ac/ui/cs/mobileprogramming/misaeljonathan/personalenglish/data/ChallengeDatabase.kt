package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Challenge::class], version = 1, exportSchema = false)
abstract class ChallengeDatabase: RoomDatabase() {

    abstract fun challengeDao(): ChallengeDao

    companion object {

        @Volatile
        private var instance: ChallengeDatabase? = null

        fun getInstance(context: Context) =
            instance
                ?: synchronized(this) {
                    instance
                        ?: Room.databaseBuilder(
                            context.getApplicationContext(),
                            ChallengeDatabase::class.java,
                            "challenge_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                }
    }
}