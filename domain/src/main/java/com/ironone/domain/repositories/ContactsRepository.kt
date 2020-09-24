package com.ironone.domain.repositories

import com.ironone.domain.entities.ContactEntity

interface ContactsRepository: BaseRepository {
    suspend fun getAllContacts():List<ContactEntity>
    suspend fun getContactForId(id: Int): ContactEntity
    suspend fun deleteContactWithId(id: Int)
    suspend fun insertContact(contact:ContactEntity)
    suspend fun updateContact(contact:ContactEntity)

}