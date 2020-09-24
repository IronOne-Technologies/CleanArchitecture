package com.ironone.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ironone.domain.repositories.BaseRepository
import com.ironone.domain.repositories.ContactsRepository

class ViewModelFactory(private val repository : BaseRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository as ContactsRepository) as T
        } else if (modelClass.isAssignableFrom(ContactDetailsViewModel::class.java)) {
            return ContactDetailsViewModel(repository as ContactsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
