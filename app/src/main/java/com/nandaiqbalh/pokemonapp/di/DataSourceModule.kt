package com.nandaiqbalh.pokemonapp.di

import com.nandaiqbalh.pokemonapp.data.remote.datasource.auth.login.AuthLoginRemoteDataSource
import com.nandaiqbalh.pokemonapp.data.remote.datasource.auth.login.AuthLoginRemoteDataSourceImpl
import com.nandaiqbalh.pokemonapp.data.remote.datasource.auth.register.AuthRegisterRemoteDataSource
import com.nandaiqbalh.pokemonapp.data.remote.datasource.auth.register.AuthRegisterRemoteDataSourceImpl
import com.nandaiqbalh.pokemonapp.data.remote.datasource.catchpokemon.CatchPokemonDataSource
import com.nandaiqbalh.pokemonapp.data.remote.datasource.catchpokemon.CatchPokemonDataSourceImpl
import com.nandaiqbalh.pokemonapp.data.remote.datasource.detailpokemon.DetailPokemonDataSource
import com.nandaiqbalh.pokemonapp.data.remote.datasource.detailpokemon.DetailPokemonDataSourceImpl
import com.nandaiqbalh.pokemonapp.data.remote.datasource.mypokemon.MyPokemonDataSource
import com.nandaiqbalh.pokemonapp.data.remote.datasource.mypokemon.MyPokemonDataSourceImpl
import com.nandaiqbalh.pokemonapp.data.remote.datasource.pokemonlist.PokemonListDataSource
import com.nandaiqbalh.pokemonapp.data.remote.datasource.pokemonlist.PokemonListDataSourceImpl
import com.nandaiqbalh.pokemonapp.data.remote.datasource.storepokemon.StorePokemonDataSource
import com.nandaiqbalh.pokemonapp.data.remote.datasource.storepokemon.StorePokemonDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

	@Binds
	abstract fun providePokemonListDataSource(pokemonListDataSourceImpl: PokemonListDataSourceImpl): PokemonListDataSource

	@Binds
	abstract fun provideDetailPokemonDataSource(detailPokemonDataSourceImpl: DetailPokemonDataSourceImpl): DetailPokemonDataSource

	@Binds
	abstract fun provideAuthLoginDataSource(authLoginRemoteDataSourceImpl: AuthLoginRemoteDataSourceImpl): AuthLoginRemoteDataSource

	@Binds
	abstract fun provideAuthRegisterDataSource(authRegisterRemoteDataSourceImpl: AuthRegisterRemoteDataSourceImpl): AuthRegisterRemoteDataSource

	@Binds
	abstract fun provideCatchPokemonDataSource(catchPokemonDataSourceImpl: CatchPokemonDataSourceImpl): CatchPokemonDataSource

	@Binds
	abstract fun provideStorePokemonDataSource(storePokemonDataSourceImpl: StorePokemonDataSourceImpl): StorePokemonDataSource

	@Binds
	abstract fun provideMyPokemonDataSource(myPokemonDataSourceImpl: MyPokemonDataSourceImpl): MyPokemonDataSource


}