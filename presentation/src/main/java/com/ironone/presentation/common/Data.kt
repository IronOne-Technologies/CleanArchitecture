package com.ironone.presentation.common


/**
 * A generic wrapper class around data request
 */
data class Data<RequestData>(var responseType: Status, var data: RequestData? = null, var error: Error? = null)

enum class Status { SUCCESSFUL, ERROR, LOADING }
data class Error(
    var message:String? = null
)

enum class Operations(value: Int) { CREATE(0), EDIT(1) }


