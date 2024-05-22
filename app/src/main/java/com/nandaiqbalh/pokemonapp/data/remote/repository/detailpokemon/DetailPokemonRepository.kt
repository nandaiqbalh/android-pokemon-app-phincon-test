package com.nandaiqbalh.pokemonapp.data.remote.repository.detailpokemon

import com.nandaiqbalh.pokemonapp.data.remote.datasource.detailpokemon.DetailPokemonDataSource
import com.nandaiqbalh.pokemonapp.data.remote.model.pokemondetail.response.DetailPokemonResponse
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import retrofit2.http.Path
import javax.inject.Inject

interface DetailPokemonRepository {

	suspend fun getDetailPokemon(
		@Path("name") name : String
	): Resource<DetailPokemonResponse>

}

class DetailPokemonRepositoryImpl @Inject constructor(
	private val dataSource: DetailPokemonDataSource,
) : DetailPokemonRepository {

	override suspend fun getDetailPokemon(name: String): Resource<DetailPokemonResponse> {
		return proceed {
			dataSource.getDetailPokemon(name)
		}
	}

	private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
		return try {
			Resource.Success(coroutines.invoke())
		} catch (e: Exception) {
			Resource.Error(e)
		}
	}
}