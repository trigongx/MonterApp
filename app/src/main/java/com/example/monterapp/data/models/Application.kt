package com.example.monterapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "applications")
data class Application(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val address:String? = null,
    val regionName: String? = null,
    val phoneNumber: Int? = null,
    val personalAccount: Int? = null,
    val date:String? = null,
    val reason: String? = null,
    var status: Boolean? = null,
):Serializable