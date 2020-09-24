package com.ironone.presentation.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ironone.domain.repositories.BaseRepository
import com.ironone.presentation.vm.BaseViewModel
import com.ironone.presentation.vm.ViewModelFactory


abstract class BaseActivity<T: BaseViewModel>: AppCompatActivity() {

    protected lateinit var viewModel:T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contentView = getContentView()
        contentView?.let {
            setContentView(contentView)
            initUI()
        }

        val viewModelFactory = ViewModelFactory(getRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(getViewModelClass())
        onBindViewModel(viewModel)
        viewModel.init()

    }

    abstract fun getViewModelClass():Class<T>

    abstract fun getRepository():BaseRepository

    open fun onBindViewModel(viewModel: T) {}

    @LayoutRes
    open fun getContentView(): Int? = null


    open fun initUI() {}

}