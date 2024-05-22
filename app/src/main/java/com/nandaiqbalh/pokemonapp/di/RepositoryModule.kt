package com.nandaiqbalh.pokemonapp.di

import com.nandaiqbalh.pokemonapp.data.remote.repository.detailpokemon.DetailPokemonRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.detailpokemon.DetailPokemonRepositoryImpl
import com.nandaiqbalh.pokemonapp.data.remote.repository.pokemonlist.PokemonListRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.pokemonlist.PokemonListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

	@Binds
	abstract fun bindsPokemonListRepository(pokemonListRepositoryImpl: PokemonListRepositoryImpl): PokemonListRepository
	@Binds
	abstract fun bindsDetailPokemonRepository(detailPokemonRepositoryImpl: DetailPokemonRepositoryImpl): DetailPokemonRepository


}