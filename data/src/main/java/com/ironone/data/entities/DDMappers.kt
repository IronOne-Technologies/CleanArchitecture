package com.ironone.data.entities

import com.ironone.domain.entities.ContactEntity

class ContactDataEntitiesMapper constructor() {

    fun mapToEntity(contacts: List<ContactData>?) = mapListContactsToEntities(contacts)

    private fun mapListContactsToEntities(contacts: List<ContactData>?)
            : List<ContactEntity> = contacts?.map { it.mapToEntity() } ?: emptyList()

}

fun ContactData.mapToEntity(): ContactEntity = ContactEntity(
    id = this.id,
    name = this.name,
    address = this.address,
    email = this.email,
    phone = this.phone
)

class ContactEntityDataMapper constructor() {
    fun mapToData(entity: ContactEntity): ContactData = ContactData(
        id = entity.id,
        name = entity.name,
        address = entity.address,
        email = entity.email,
        phone = entity.phone
    )
}

