package com.example.notesapp.model

import android.content.Context
import androidx.room.*
import com.example.notesapp.view.utils.AnnotatedStringConverter


@Database(entities = [Note::class], version = 1)
@TypeConverters(AnnotatedStringConverter::class)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDao(): NoteDao
    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}