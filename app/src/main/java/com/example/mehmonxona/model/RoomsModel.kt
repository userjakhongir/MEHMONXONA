package com.example.mehmonxona.model

import java.io.Serializable

class RoomsModel constructor(

    val roomname:String? = null,
    val roomdescription:String? = null,
    val roomimageurl:String? = null,
    val pushkey:String? = null
):Serializable