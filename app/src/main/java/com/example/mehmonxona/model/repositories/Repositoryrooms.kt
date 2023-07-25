package com.example.mehmonxona.model.repositories

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.mehmonxona.model.BookingModel

import com.example.mehmonxona.model.RoomsModel
import com.example.mehmonxona.utilits.Constants.ADMINBOOKING
import com.example.mehmonxona.utilits.Constants.HISTORY
import com.example.mehmonxona.utilits.Constants.ROOMS
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.EventListener


class Repositoryrooms constructor(
    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child(ROOMS),
    val storageReference: StorageReference = FirebaseStorage.getInstance().getReference().child(ROOMS),
    val databaseReferenceadminbooking: DatabaseReference = FirebaseDatabase.getInstance().getReference().child(ADMINBOOKING),
    var databaseReferencehistory: DatabaseReference=FirebaseDatabase.getInstance().getReference().child(HISTORY)
) {

    var livedatasucces = MutableLiveData<Boolean>()
    var livedataprogress = MutableLiveData<Double>()
    var arraList = ArrayList<RoomsModel>()
    var livedataallrooms = MutableLiveData<ArrayList<RoomsModel>>()
    // room booking
    var livedatabooking=MutableLiveData<Boolean>()

    fun uploadrooms(roomname:String,roomdescription:String,uri:Uri){ // bu yerdagi uri telefondagi jaylashgan manzili
        if (uri != null){
            succes(true)
            val filereference: StorageReference = storageReference.child(
                System.currentTimeMillis().toString() + "." + System.currentTimeMillis().toString()
            )
            filereference.putFile(uri)
                .addOnSuccessListener { filereference.downloadUrl.addOnSuccessListener { // bu yerda esa firebasedagi joylashgan manzili
                    var pushkey = databaseReference.push().key.toString()

                    // RoomsModel classdan obyekt olindi
                    val roomsModel = RoomsModel(roomname,roomdescription,it.toString(),pushkey)

                    databaseReference.child(pushkey).setValue(roomsModel).addOnCompleteListener {
                        if (it.isSuccessful){
                            succes(false)
                        }
                    }
                } }
                .addOnProgressListener {
                    val progress: Double = 100.0 * it.getBytesTransferred() / it.getTotalByteCount()
                    livedataprogress.value = progress
                }
        }
    }

    fun readalldata():MutableLiveData<ArrayList<RoomsModel>>{
        databaseReference.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                arraList.clear()
                for (datasnapshot:DataSnapshot in snapshot.children){
                    val rooms = datasnapshot.getValue(RoomsModel::class.java)
                    arraList.add(rooms!!)
                }

                livedataallrooms.value=arraList
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return livedataallrooms
    }

    fun succes(boolean: Boolean){
        livedatasucces.value=boolean
    }

    fun roombooking(roomname:String,username:String, surname:String, phone:String, datatime:String){
        livedatabooking.value = false

        val bookingModel = BookingModel(
            roomname = roomname,
            username = username,
            surname = surname,
            phone = phone,
            datatime = datatime
        )
        databaseReferenceadminbooking.push().setValue(bookingModel)
        databaseReferencehistory.child(phone).push().setValue(bookingModel).addOnCompleteListener {
            if (it.isSuccessful){
                livedatabooking.value = true
            }
        }
    }
}