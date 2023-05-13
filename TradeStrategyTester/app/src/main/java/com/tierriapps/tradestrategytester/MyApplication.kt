package com.tierriapps.tradestrategytester

import android.app.Application
import com.tierriapps.tradestrategytester.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // SEMPRE LEMBRE-SE DE DECLARAR A MYAPPLICATION NO NAME DO MANIFESTS

        startKoin {
            // gera logcats do que esta sendo criado
            androidLogger()

            // define os contextos para as instancias que necessitem deles
            androidContext(this@MyApplication)

            // define quais variaveis de modulos seram usadas
            modules(mainModule)
        }
    }
}