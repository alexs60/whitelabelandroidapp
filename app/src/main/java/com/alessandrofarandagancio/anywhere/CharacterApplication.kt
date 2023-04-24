package com.alessandrofarandagancio.anywhere

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CharacterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}