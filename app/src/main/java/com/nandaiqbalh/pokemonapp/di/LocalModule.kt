package com.nandaiqbalh.pokemonapp.di

import android.content.Context
import com.nandaiqbalh.pokemonapp.data.local.datastore.auth.AuthDataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

	@Provides
	fun provideAuthDataStoreManager(@ApplicationContext context: Context): AuthDataStoreManager =
		AuthDataStoreManager(context)

}