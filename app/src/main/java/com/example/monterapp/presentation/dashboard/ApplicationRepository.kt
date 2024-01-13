package com.example.monterapp.presentation.dashboard

import com.example.monterapp.data.local.dao.ApplicationDao
import com.example.monterapp.data.models.Application

class ApplicationRepository(private val applicationDao: ApplicationDao) {
    suspend fun getApplicationForMonter(regionName:String):List<Application>{
        return applicationDao.getApplicationsForMonter(regionName)
    }
}