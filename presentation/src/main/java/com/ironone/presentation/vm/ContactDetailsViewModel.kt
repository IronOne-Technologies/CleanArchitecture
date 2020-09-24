package com.ironone.presentation.vm


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ironone.domain.repositories.ContactsRepository
import com.ironone.domain.usecases.ContactsUseCase
import com.ironone.presentation.common.mapToContact
import com.ironone.presentation.entities.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactDetailsViewModel(repository: ContactsRepository) : BaseViewModel(repository) {

    private val contactUseCase = ContactsUseCase(repository)

    private var contactLD = MutableLiveData<Contact>()


    fun getContactForId(id: Int) {
        viewModelScope.launch {
            val contact = contactUseCase.getContactForId(id).mapToContact()
            withContext(Dispatchers.Main) {
                contactLD.postValue(contact)
            }
        }
    }

    fun getContactLiveData() = contactLD

}