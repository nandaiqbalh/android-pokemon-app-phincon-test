package com.nandaiqbalh.pokemonapp.di

import com.nandaiqbalh.pokemonapp.data.remote.repository.auth.login.AuthLoginRemoteRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.auth.login.AuthLoginRemoteRepositoryImpl
import com.nandaiqbalh.pokemonapp.data.remote.repository.auth.register.AuthRegisterRemoteRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.auth.register.AuthRegisterRemoteRepositoryImpl
import com.nandaiqbalh.pokemonapp.data.remote.repository.catchpokemon.CatchPokemonRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.catchpokemon.CatchPokemonRepositoryImpl
import com.nandaiqbalh.pokemonapp.data.remote.repository.deletepokemon.DeletePokemonRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.deletepokemon.DeletePokemonRepositoryImpl
import com.nandaiqbalh.pokemonapp.data.remote.repository.detailpokemon.DetailPokemonRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.detailpokemon.DetailPokemonRepositoryImpl
import com.nandaiqbalh.pokemonapp.data.remote.repository.mypokemon.MyPokemonRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.mypokemon.MyPokemonRepositoryImpl
import com.nandaiqbalh.pokemonapp.data.remote.repository.pokemonlist.PokemonListRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.pokemonlist.PokemonListRepositoryImpl
import com.nandaiqbalh.pokemonapp.data.remote.repository.releasepokemon.ReleasePokemonRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.releasepokemon.ReleasePokemonRepositoryImpl
import com.nandaiqbalh.pokemonapp.data.remote.repository.storepokemon.StorePokemonRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.storepokemon.StorePokemonRepositoryImpl
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
	@Binds
	abstract fun bindsAuthLoginRepository(authLoginRemoteRepositoryImpl: AuthLoginRemoteRepositoryImpl): AuthLoginRemoteRepository
	@Binds
	abstract fun bindsAuthRegisterRepository(authRegisterRemoteRepositoryImpl: AuthRegisterRemoteRepositoryImpl): AuthRegisterRemoteRepository
	@Binds
	abstract fun bindsCatchPokemonRepository(catchPokemonRepositoryImpl: CatchPokemonRepositoryImpl): CatchPokemonRepository
	@Binds
	abstract fun bindsStorePokemonRepository(storePokemonRepositoryImpl: StorePokemonRepositoryImpl): StorePokemonRepository
	@Binds
	abstract fun bindsMyPokemonRepository(myPokemonRepositoryImpl: MyPokemonRepositoryImpl): MyPokemonRepository
	@Binds
	abstract fun bindsReleasePokemonRepository(releasePokemonRepositoryImpl: ReleasePokemonRepositoryImpl): ReleasePokemonRepository
	@Binds
	abstract fun bindsDeletePokemonRepository(deletePokemonRepositoryImpl: DeletePokemonRepositoryImpl): DeletePokemonRepository

}