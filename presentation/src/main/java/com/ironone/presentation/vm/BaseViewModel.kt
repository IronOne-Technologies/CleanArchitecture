package com.ironone.presentation.vm

import androidx.lifecycle.ViewModel
import com.ironone.domain.repositories.BaseRepository

abstract class BaseViewModel(repository: BaseRepository): ViewModel() {

    //protected val context = application.applicationContext

    open fun init() {}


}