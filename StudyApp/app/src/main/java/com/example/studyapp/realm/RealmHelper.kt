package com.example.studyapp.realm

import android.content.Context
import android.widget.Toast
import com.example.studyapp.realm.models.FlashCard
import com.example.studyapp.realm.models.Trivia
import io.realm.RealmList
import io.realm.RealmResults
import java.lang.Exception

object RealmHelper {

    fun getFlashCards(): RealmResults<FlashCard> {

        var flashCards = realm.where(FlashCard::class.java).findAll()

        if (flashCards.isEmpty()) {

            // create dummy data
            val triviaListOne = RealmList<Trivia>()
            triviaListOne.add(Trivia("What is the color of the Sky", "Blue", "English"))
            triviaListOne.add(Trivia("Cual es el color del cielo", "Azul", "Español"))

            val flashCardOne = FlashCard(triviaList = triviaListOne)

            val triviaListTwo = RealmList<Trivia>()
            triviaListTwo.add(Trivia("What is 2 + 2", "Four", "English"))

            val flashCardTwo = FlashCard(triviaList = triviaListTwo)

            realm.executeTransaction { transactionRealm ->
                transactionRealm.insert(flashCardOne)
                transactionRealm.insert(flashCardTwo)
            }

            flashCards = realm.where(FlashCard::class.java).findAll()
        }

        return flashCards
    }

    fun getLanguages(): MutableList<String> {

        val languageList = mutableListOf<String>()

        var flashCards = realm.where(FlashCard::class.java).findAll()

        flashCards.forEach { flashCard ->
            flashCard.triviaList.forEach { trivia ->

                if (!trivia.language.isNullOrEmpty()) {
                    if (!languageList.contains(trivia.language!!)) {
                        languageList.add(trivia.language!!)
                    }
                }
            }
        }

        return languageList
    }

    fun addCardToRealm(triviaList: RealmList<Trivia>): Boolean {

        return try {
            realm.executeTransaction { transactionRealm ->
                transactionRealm.insert(FlashCard(triviaList))
            }
            true

        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }
}