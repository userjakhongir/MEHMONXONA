package com.example.mehmonxona.viewmodel

import android.net.Uri
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mehmonxona.model.RoomsModel
import com.example.mehmonxona.model.repositories.Repositoryrooms

class RoomsViewmodel constructor(
    val repositoryrooms: Repositoryrooms = Repositoryrooms()
): ViewModel() {

    fun uploadnewrooms(roomname:String,roomdescription:String,uri: Uri) {
        repositoryrooms.uploadrooms(roomname,roomdescription,uri)
    }
    fun uploadsucces():MutableLiveData<Boolean>{
        return repositoryrooms.livedatasucces
    }
    fun uploadprogress():MutableLiveData<Double>{
        return repositoryrooms.livedataprogress
    }
    fun readallrooms(): MutableLiveData<ArrayList<RoomsModel>> {
        return repositoryrooms.readalldata()
    }

    fun booking(roomname:String,username:String, surname:String, phone:String, datatime:String){

        repositoryrooms.roombooking(roomname,username,surname,phone,datatime)
    }
    fun bookingsucces():MutableLiveData<Boolean>{
        return repositoryrooms.livedatabooking
    }
}