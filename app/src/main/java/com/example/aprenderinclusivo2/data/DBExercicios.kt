package com.example.aprenderinclusivo2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Exercicios_Animais::class, Exercicios_Bandeiras::class, Exercicios_Cores::class, Exercicios_Vegetais::class],
    version = 1,
    exportSchema = false
)
abstract class DBExercicios: RoomDatabase() {
    abstract fun ExerciciosDao(): ExerciciosDao

    companion object{
        @Volatile
        private var INSTANCE: DBExercicios? = null

        fun getDatabase(context: Context): DBExercicios{
            val tempInstance = INSTANCE
            if(tempInstance!= null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DBExercicios::class.java,
                    "db_exercicios"
                ).createFromAsset("database/Exercicios.db").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}