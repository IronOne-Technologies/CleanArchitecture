package com.ironone.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ironone.data.entities.ContactData

@Database(entities = [ContactData::class], version = 1, exportSchema = false)
abstract class ContactsDB : RoomDatabase() {
    abstract fun getContactsDAO(): ContactsDAO

    companion object {
        @Volatile
        private var INSTANCE: ContactsDB? = null

        fun getInstance(context: Context): ContactsDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactsDB::class.java,
                        "contacts_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }

        }

    }
}
