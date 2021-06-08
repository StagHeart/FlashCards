package com.example.studyapp.realm

import com.example.studyapp.realm.models.FlashCard
import com.example.studyapp.realm.models.Trivia
import io.realm.RealmList
import io.realm.RealmResults

object RealmHelper {

    fun getFlashCards(): RealmResults<FlashCard> {

        var flashCards = realm.where(FlashCard::class.java).findAll()

        if (flashCards.isEmpty()) {

            // create dummy data
            val triviaListOne = RealmList<Trivia>()
            triviaListOne.add(Trivia("What is the color of the Sky", "Blue", "English"))
            triviaListOne.add(Trivia("Cual es el color del cielo", "Azul", "Spanish"))

            val flashCardOne = FlashCard(title = "Sky Color", triviaList = triviaListOne)

            val triviaListTwo = RealmList<Trivia>()
            triviaListTwo.add(Trivia("What is 2 + 2", "Four", "English"))
            triviaListTwo.add(Trivia("¿Qué es 2 + 2?", "Cuatro", "Spanish"))

            val flashCardTwo = FlashCard(title = "Math", triviaList = triviaListTwo)

            // create dummy data
            val triviaListThree = RealmList<Trivia>()
            triviaListOne.add(Trivia("What is the color of the Sky", "Blue", "English"))
            triviaListOne.add(Trivia("Cual es el color del cielo", "Azul", "Spanish"))

            val flashCardThree = FlashCard(title = "Sky Color", triviaList = triviaListOne)

            val triviaListFour = RealmList<Trivia>()
            triviaListTwo.add(Trivia("What is 2 + 2", "Four", "English"))
            triviaListTwo.add(Trivia("¿Qué es 2 + 2?", "Cuatro", "Spanish"))

            val flashCardFour = FlashCard(title = "Math", triviaList = triviaListTwo)

            // create dummy data
            val triviaListFive = RealmList<Trivia>()
            triviaListOne.add(Trivia("What is the color of the Sky", "Blue", "English"))
            triviaListOne.add(Trivia("Cual es el color del cielo", "Azul", "Spanish"))

            val flashCardFive = FlashCard(title = "Sky Color", triviaList = triviaListOne)

            val triviaListSix = RealmList<Trivia>()
            triviaListTwo.add(Trivia("What is 2 + 2", "Four", "English"))
            triviaListTwo.add(Trivia("¿Qué es 2 + 2?", "Cuatro", "Spanish"))

            val flashCardSix = FlashCard(title = "Math", triviaList = triviaListTwo)



            realm.executeTransaction { transactionRealm ->
                transactionRealm.insert(flashCardOne)
                transactionRealm.insert(flashCardTwo)
                transactionRealm.insert(flashCardThree)
                transactionRealm.insert(flashCardFour)
                transactionRealm.insert(flashCardFive)
                transactionRealm.insert(flashCardSix)
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

                if(!trivia.language.isNullOrEmpty()) {
                    if (!languageList.contains(trivia.language!!)) {
                        languageList.add(trivia.language!!)
                    }
                }
            }
        }

        return languageList
    }
}