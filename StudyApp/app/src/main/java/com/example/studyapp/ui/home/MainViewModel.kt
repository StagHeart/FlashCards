package com.example.studyapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _language = MutableLiveData("English")
    val language: LiveData<String> = _language

    fun onLanguageChange(newLanguage: String) {
        _language.value = newLanguage
    }
}