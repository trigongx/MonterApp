package com.example.monterapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.monterapp.data.models.CompletedApplication


@Dao
interface CompletedApplicationDao {

    @Query("SELECT * FROM completedApplications")
    suspend fun getCompletedApplication(): List<CompletedApplication>

    @Insert
    suspend fun insertCompletedApplication(completedApplication: CompletedApplication)

    @Delete
    suspend fun deleteCompletedApplication(completedApplication: CompletedApplication)

    @Update
    suspend fun updateCompletedApplication(completedApplication: CompletedApplication)
}