package com.bravepaw.myapplication.ui.addcard

import androidx.lifecycle.ViewModel
import com.example.studyapp.realm.models.Trivia
import io.realm.RealmList

class AddCardViewModel : ViewModel() {

    var languageList = RealmList<Trivia>()
}