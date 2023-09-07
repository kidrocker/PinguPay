package com.penguin.pay.di

import com.penguin.pay.network.NetworkDataSource
import com.penguin.pay.repository.ProdTransactionRepository
import com.penguin.pay.repository.TransactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTransactRepository(networkDataSource: NetworkDataSource): TransactRepository {
        return ProdTransactionRepository(networkDataSource)
    }
}