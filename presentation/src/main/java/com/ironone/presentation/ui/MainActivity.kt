package com.ironone.presentation.ui

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.ironone.domain.repositories.BaseRepository
import com.ironone.presentation.R
import com.ironone.presentation.common.Operations
import com.ironone.presentation.common.SwipeToActionCallback
import com.ironone.presentation.entities.Contact
import com.ironone.presentation.frameworks.DBFramework
import com.ironone.presentation.ui.adapters.ContactListAdapter
import com.ironone.presentation.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.view_add_a_contact_dialog.view.*

class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var contactListAdapter: ContactListAdapter

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun onBindViewModel(viewModel: MainViewModel) {
        viewModel.getContactsListLiveData().observe(this, Observer {
            Log.v("MY_TAG", "contacts: $it")
            contactListAdapter.updateList(it)
        })
    }

    @LayoutRes
    override fun getContentView(): Int? {
        return R.layout.activity_main
    }

    override fun getRepository(): BaseRepository {
        return DBFramework.getContactRepository(application)
    }

    override fun initUI() {
        super.initUI()

        addContactBtn.setOnClickListener {
            createContact()
        }

        val contactListAdapterListener = object: ContactListAdapter.ContactListAdapterListener {
            override fun edit(contact: Contact) {
                editContact(contact)
            }

            override fun delete(contact: Contact) {
                deleteContact(contact)
            }

            override fun loadDetailView(id: Int) {
                val intent = Intent(applicationContext, ContactDetailsActivity::class.java)
                intent.putExtra(ContactDetailsActivity.KEY_CONTACT_ID, id)
                startActivity(intent)
            }

        }

        contactListAdapter = ContactListAdapter(contactListAdapterListener)

        contactsRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        contactsRecyclerView.adapter = contactListAdapter
        enableSwipeToDeleteAndUndo(contactListAdapterListener)

        title = "Contacts"
    }

    private fun createContact() {
        createCustomDialog(Operations.CREATE, Contact())
    }

    private fun editContact(contact: Contact) {
        createCustomDialog(Operations.EDIT, contact)
    }

    private fun addContact(contact: Contact) {
        viewModel.addContact(contact)
    }

    private fun updateContact(contact: Contact) {
        viewModel.updateContact(contact)
    }

    private fun deleteContact(contact: Contact) {
        viewModel.deleteContact(contact.id)
    }

    private fun createCustomDialog(type: Operations, contact: Contact) {
        val dialogView =
            LayoutInflater.from(this).inflate(R.layout.view_add_a_contact_dialog, null)

        var title = R.string.title_add_a_contact
        if (type == Operations.EDIT) {
            title = R.string.title_update_a_contact
            dialogView.dialogAddUpdateBtn.setText(R.string.text_update_button)
            contact.name.let {
                dialogView.dialogNameEt.setText(it)
            }
            contact.address.let {
                dialogView.dialogAddressEt.setText(it)
            }
            contact.phone.let {
                dialogView.dialogPhoneEt.setText(it)
            }
            contact.email.let {
                dialogView.dialogEmailEt.setText(it)
            }
        }

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle(title)

        val alertDialog = builder.show()

        dialogView.dialogAddUpdateBtn.setOnClickListener {
            val name = dialogView.dialogNameEt.text?.toString()
            val address = dialogView.dialogAddressEt.text?.toString()
            val phone = dialogView.dialogPhoneEt.text?.toString()
            val email = dialogView.dialogEmailEt.text?.toString()
            if (!name.isNullOrEmpty() && !address.isNullOrEmpty() && !phone.isNullOrEmpty() && !email.isNullOrEmpty()) {
                contact.name = name
                contact.address = address
                contact.email = email
                contact.phone = phone

                if (type == Operations.EDIT) {
                    updateContact(contact)
                } else {
                    addContact(contact)
                }

                alertDialog.dismiss()
            } else {
                if (name.isNullOrEmpty()) {
                    dialogView.dialogNameEt.error =
                        getString(R.string.error_message_field_can_not_empty)
                }
                if (address.isNullOrEmpty()) {
                    dialogView.dialogAddressEt.error =
                        getString(R.string.error_message_field_can_not_empty)
                }
                if (phone.isNullOrEmpty()) {
                    dialogView.dialogPhoneEt.error =
                        getString(R.string.error_message_field_can_not_empty)
                }
                if (email.isNullOrEmpty()) {
                    dialogView.dialogEmailEt.error =
                        getString(R.string.error_message_field_can_not_empty)
                }
            }
        }

        dialogView.dialogCancelBtn.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    private fun enableSwipeToDeleteAndUndo(listener: ContactListAdapter.ContactListAdapterListener) {
        val swipeToDeleteCallback: SwipeToActionCallback = object : SwipeToActionCallback(this) {
            override fun onSwiped(@NonNull viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
                val item: Contact = contactListAdapter.getContacts()[position]
                contactListAdapter.removeItem(position)
                Snackbar.make(
                    viewHolder.itemView,
                    "Item is going to delete.",
                    Snackbar.LENGTH_LONG
                ).setAction("UNDO") {
                    contactListAdapter.restoreItem(item, position)
                    contactsRecyclerView.scrollToPosition(position)
                }.setActionTextColor(Color.YELLOW)
                    .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            if (event != DISMISS_EVENT_ACTION) {
                                listener.delete(item)
                            }
                        }
                    })
                    .show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(contactsRecyclerView)
    }

}