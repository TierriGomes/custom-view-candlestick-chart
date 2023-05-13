package com.tierriapps.tradestrategytester.di

import com.tierriapps.tradestrategytester.data.apirest.RetrofitService
import com.tierriapps.tradestrategytester.data.localstorage.MyRoomDataBase
import com.tierriapps.tradestrategytester.viewmodels.MainViewModel
import com.tierriapps.tradestrategytester.viewmodels.Repository
import com.tierriapps.tradestrategytester.viewmodels.RepositoryBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single {
        RetrofitService().retrofitService
    }

    single {
        MyRoomDataBase.getDatabase(get()).myDao()
    }

    // a viewmodel vai pedir um reposytori entao vc tem que falar que é esse single sera uma inplementação
    single <Repository>{
        RepositoryBuilder(
            apiMethods = get(),
            chartDAO = get()
        )
    }

    viewModel {
        MainViewModel(
            repository = get()
        )
    }
}