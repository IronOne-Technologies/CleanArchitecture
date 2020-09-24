package com.ironone.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ironone.presentation.R
import com.ironone.presentation.entities.Contact
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactListAdapter(private var listener: ContactListAdapterListener) :
    RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    private var contacts = mutableListOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_list_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position], listener)
    }

    fun updateList(list: List<Contact>) {
        if (list.isNotEmpty()) {
            contacts.clear()
            contacts.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun getContacts(): List<Contact> {
        return contacts
    }

    fun removeItem(position: Int) {
        contacts.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: Contact, position: Int) {
        contacts.add(position, item)
        notifyItemInserted(position)
    }

    interface ContactListAdapterListener {
        fun edit(contact: Contact)
        fun delete(contact: Contact)
        fun loadDetailView(id: Int)
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(contact: Contact, listener: ContactListAdapterListener) {
            with(itemView) {
                nameTv.text = contact.name
                phoneTv.text = contact.phone

                editButton.setOnClickListener {
                    listener.edit(contact)
                }

                setOnClickListener {
                    listener.loadDetailView(contact.id)
                }
            }
        }
    }

}