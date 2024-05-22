package com.nandaiqbalh.pokemonapp.di

import com.nandaiqbalh.pokemonapp.data.remote.datasource.pokemonlist.PokemonListDataSource
import com.nandaiqbalh.pokemonapp.data.remote.datasource.pokemonlist.PokemonListDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

	@Binds
	abstract fun providePokemonListDataSource(pokemonListDataSourceImpl: PokemonListDataSourceImpl): PokemonListDataSource


}