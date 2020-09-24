package com.ironone.presentation.frameworks

import android.content.Context
import com.ironone.data.db.ContactsDB
import com.ironone.data.repositories.ContactsRepositoryImpl
import com.ironone.domain.repositories.ContactsRepository

object DBFramework {
    fun getContactRepository(context: Context): ContactsRepository {
        val db = ContactsDB.getInstance(context)
        return ContactsRepositoryImpl(db.getContactsDAO())
    }
}