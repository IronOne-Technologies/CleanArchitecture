package com.ironone.presentation.common

import com.ironone.domain.entities.ContactEntity
import com.ironone.presentation.entities.Contact



class EntitiesToContactsMapper constructor() {

    fun mapToContacts(entities: List<ContactEntity>?) = mapListEntityToContacts(entities)

    private fun mapListEntityToContacts(entities: List<ContactEntity>?)
            : List<Contact> = entities?.map { it.mapToContact() } ?: emptyList()

}

fun Contact.mapToEntity(): ContactEntity = ContactEntity(
    id = this.id,
    name = this.name,
    address = this.address,
    email = this.email,
    phone = this.phone
)

fun ContactEntity.mapToContact(): Contact = Contact(
    id = this.id,
    name = this.name,
    address = this.address,
    email = this.email,
    phone = this.phone
)





