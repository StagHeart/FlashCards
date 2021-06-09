package com.example.studyapp.realm

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.log.LogLevel
import io.realm.log.RealmLog

lateinit var realm: Realm

class RealmApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        // set this config as the default realm
        val config = RealmConfiguration.Builder()
            .name("default-realm")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(config)

        RealmLog.setLevel(LogLevel.TRACE)
        
        realm = Realm.getDefaultInstance()
    }
}