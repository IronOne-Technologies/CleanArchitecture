package com.ironone.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class ContactData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int,

    @ColumnInfo(name = "name")
    var name : String,

    @ColumnInfo(name = "address")
    var address : String,

    @ColumnInfo(name = "email")
    var email : String,

    @ColumnInfo(name = "phone")
    var phone : String

) {
    override fun toString(): String {
        return "\n id='$id'" +
                "\n name='$name'\n" +
                "\n address='$address'\n" +
                "\n email='$email'\n"+
                "\n phone='$phone'\n"
    }
}

