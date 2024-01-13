package com.example.monterapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.monterapp.data.models.Monter

@Dao
interface MonterDao {

    @Query("SELECT * FROM monters WHERE login = :login AND password=:password")
    suspend fun getMonterByLoginAndPassword(login:String,password:String):Monter?

    @Query("SELECT * FROM monters")
    suspend fun getAllMonters(): List<Monter>

    @Query("DELETE FROM monters WHERE id = :monterId")
    suspend fun deleteMonterById(monterId:Int)

    @Insert
    suspend fun insertMonter(monter: Monter)

    @Delete
    suspend fun deleteMonter(monter: Monter)

    @Update
    suspend fun updateMonter(monter: Monter)

    @Query("SELECT COUNT(*) FROM monters")
    suspend fun isTableNotEmpty(): Boolean
}