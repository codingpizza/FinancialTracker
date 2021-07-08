package com.codingpizza.financialtracker.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingpizza.financialtracker.data.remote.Client
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _liveData = MutableLiveData<String>()

    val liveData :LiveData<String> = _liveData

    fun getData() {
        viewModelScope.launch {
            val data = Client().executeRequest()
            _liveData.value = data
        }
    }
}