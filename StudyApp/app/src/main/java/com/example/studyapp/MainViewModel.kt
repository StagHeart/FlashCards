package com.example.studyapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studyapp.realm.RealmHelper
import com.example.studyapp.realm.models.FlashCard
import com.example.studyapp.realm.realm
import io.realm.RealmList
import io.realm.RealmResults

class MainViewModel : ViewModel() {

    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    private val _language = MutableLiveData("English")
    val language: LiveData<String> = _language

    fun onLanguageChange(newLanguage: String) {
        _language.value = newLanguage
    }



//    fun onFlashCardChange(flashCard: FlashCard){
//
//        realm.executeTransaction { transactionRealm ->
//            transactionRealm.insert(flashCard)
//        }
//
//        _flashCards.value = realm.where(FlashCard::class.java).findAll()
//    }
}