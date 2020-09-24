package com.ironone.presentation.ui

import android.util.Log
import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import com.ironone.domain.repositories.BaseRepository
import com.ironone.presentation.R
import com.ironone.presentation.entities.Contact
import com.ironone.presentation.frameworks.DBFramework
import com.ironone.presentation.ui.adapters.ContactListAdapter
import com.ironone.presentation.vm.ContactDetailsViewModel
import kotlinx.android.synthetic.main.activity_contact_details.*

class ContactDetailsActivity : BaseActivity<ContactDetailsViewModel>() {

    private var contactId:Int = -1
    private lateinit var contactListAdapter: ContactListAdapter

    override fun getViewModelClass(): Class<ContactDetailsViewModel> {
        return ContactDetailsViewModel::class.java
    }

    override fun onBindViewModel(viewModel: ContactDetailsViewModel) {
        viewModel.getContactLiveData().observe(this, Observer {
            Log.v("MY_TAG", "contact: $it")
            showContactDetails(it)
        })

        if (contactId > -1) {
            viewModel.getContactForId(contactId)
        }
    }

    @LayoutRes
    override fun getContentView(): Int? {
        return R.layout.activity_contact_details
    }

    override fun getRepository(): BaseRepository {
        return DBFramework.getContactRepository(application)
    }

    override fun initUI() {
        super.initUI()
        title = "Contact Details"

        contactId = intent.getIntExtra(KEY_CONTACT_ID, 0)

    }

    private fun showContactDetails(contact: Contact) {
        contactDetailsTv.text = contact.toString()
    }

    companion object {
        const val KEY_CONTACT_ID = "com.ironone.presentation.ui.KEY_CONTACT_ID"
    }

}