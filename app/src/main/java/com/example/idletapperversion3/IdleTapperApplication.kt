package com.example.idletapperversion3

import android.app.Application
import com.example.idletapperversion3.savedata.AppDatabase

class IdleTapperApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this)}
}