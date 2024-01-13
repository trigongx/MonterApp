package com.example.monterapp.app

import android.app.Application
import androidx.room.Room
import com.example.monterapp.data.local.db.NeskDatabase

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            context = applicationContext,
            klass = NeskDatabase::class.java,
            name = "dbo.nesk_top"
        ).build()
    }

    companion object{
        lateinit var database:NeskDatabase
    }
}