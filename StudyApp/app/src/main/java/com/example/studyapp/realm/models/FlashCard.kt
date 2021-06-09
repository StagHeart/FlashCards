package com.example.studyapp.realm.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

open class FlashCard(
    var triviaList: RealmList<Trivia> = RealmList()

) : RealmObject()

@RealmClass(embedded = true)
open class Trivia(
    @Required
    var question: String? = null,
    @Required
    var answer: String? = null,
    @Required
    var language: String? = null
): RealmObject() {}

