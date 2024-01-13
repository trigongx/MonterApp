package com.example.monterapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.monterapp.data.local.dao.ApplicationDao
import com.example.monterapp.data.local.dao.CompletedApplicationDao
import com.example.monterapp.data.local.dao.MonterDao
import com.example.monterapp.data.models.Application
import com.example.monterapp.data.models.CompletedApplication
import com.example.monterapp.data.models.Monter

@Database(entities = [Monter::class,Application::class,CompletedApplication::class], version = 2, exportSchema = true)
abstract class NeskDatabase:RoomDatabase() {

    abstract fun getMonterDao(): MonterDao
    abstract fun getApplicationDao(): ApplicationDao
    abstract fun getCompletedApplicationDao(): CompletedApplicationDao


}