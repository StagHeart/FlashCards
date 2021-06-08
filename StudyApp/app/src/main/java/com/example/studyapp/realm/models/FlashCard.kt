package com.example.studyapp.realm.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import org.bson.types.ObjectId

//open class FlashCard(
//    @PrimaryKey
//    @Required
//    var _id: ObjectId = ObjectId(),
//    @Required
//    var title: String? = null,
//
//    var triviaList: RealmList<Trivia> = RealmList()
//) : RealmObject()

open class FlashCard(
    @PrimaryKey
    @Required
    var _id: ObjectId = ObjectId(),
    var _partition: String = "partition_string",
    @Required
    var title: String? = null,

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

