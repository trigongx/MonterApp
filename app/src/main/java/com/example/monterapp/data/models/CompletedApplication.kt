package com.example.monterapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "completedApplications")
data class CompletedApplication(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val address:String? = null,
    val regionName: String? = null,
    val phoneNumber: Int? = null,
    val personalAccount: Int? = null,
    val date:String? = null,
    val reason: String? = null,
    var status: Boolean? = null,
    val monterFullName:String? = null,
    val monterPhoto:String? = null,
    val monterComment:String? = null,
    val completedDate:String? = null,


)
