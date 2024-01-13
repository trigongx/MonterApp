package com.example.monterapp.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monterapp.data.models.Application
import kotlinx.coroutines.launch

class ApplicationViewModel(private val applicationRepository: ApplicationRepository):ViewModel() {
    private val _applications = MutableLiveData<List<Application>>()
    val applications: LiveData<List<Application>> get() = _applications

    fun loadApplicationsForMonter(regionName:String){
        viewModelScope.launch {
            _applications.value = applicationRepository.getApplicationForMonter(regionName)
        }
    }
}