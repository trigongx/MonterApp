package com.example.monterapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.monterapp.data.models.Application

@Dao
interface ApplicationDao {

    @Query("DELETE FROM applications WHERE id = :applicationId")
    suspend fun deleteApplicationById(applicationId:Int)

    @Query("SELECT * FROM applications")
    suspend fun getAllApplications(): List<Application>

    @Query("SELECT * FROM applications WHERE regionName = :regionName")
    suspend fun getApplicationsForMonter(regionName: String): List<Application>

    @Insert
    suspend fun insertApplication(application: Application)

    @Delete
    suspend fun deleteApplication(application: Application)

    @Update
    suspend fun updateApplication(application: Application)

    @Query("SELECT COUNT(*) FROM applications")
    suspend fun isTableNotEmpty(): Boolean

}