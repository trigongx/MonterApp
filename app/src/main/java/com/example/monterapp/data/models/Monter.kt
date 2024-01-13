package com.example.monterapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monters")
data class Monter(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val login: String? = null,
    val password: String? = null,
    val fullName: String? = null,
    val regionName: String? = null
)
