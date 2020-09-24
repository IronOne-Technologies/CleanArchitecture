package com.ironone.presentation.entities

data class Contact(
    var id: Int = 0,
    var name: String = "",
    var address: String = "",
    var phone: String = "",
    var email:String = ""
){
    override fun toString(): String {
        return "\n Name: '$name'\n" +
                "\n Address: '$address'\n" +
                "\n Email: '$email'\n"+
                "\n Tel: '$phone'\n"
    }
}