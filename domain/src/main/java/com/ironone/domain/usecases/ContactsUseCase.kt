package com.ironone.domain.usecases

import com.ironone.domain.entities.ContactEntity
import com.ironone.domain.repositories.ContactsRepository

class ContactsUseCase(private val repository: ContactsRepository) {

    suspend fun getAllContacts():List<ContactEntity> {
        return repository.getAllContacts()
    }

    suspend fun getContactForId(id: Int): ContactEntity {
        return repository.getContactForId(id)
    }

    suspend fun deleteContactWithId(id: Int) {
        repository.deleteContactWithId(id)
    }

    suspend fun insertContact(contact: ContactEntity) {
        repository.insertContact(contact)
    }

    suspend fun updateContact(contact: ContactEntity) {
        repository.updateContact(contact)
    }
}