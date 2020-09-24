package com.ironone.domain.entities

data class ContactEntity(
    val id : Int,
    var name : String,
    var address : String,
    var email : String,
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

