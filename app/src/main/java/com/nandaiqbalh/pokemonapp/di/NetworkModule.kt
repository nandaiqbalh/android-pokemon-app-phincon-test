package com.nandaiqbalh.pokemonapp.di

import com.nandaiqbalh.pokemonapp.data.remote.service.BackendApiService
import com.nandaiqbalh.pokemonapp.data.remote.service.PokemonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	private const val baseUrlApi = "https://pokeapi.co/api/v2/"
	private const val baseUrlBackend = "http://192.168.1.5/backend-pokemon-app-phincon-test/public/api/v1/"

	@Qualifier
	@Retention(AnnotationRetention.BINARY)
	annotation class ApiRetrofit

	@Qualifier
	@Retention(AnnotationRetention.BINARY)
	annotation class BackendRetrofit

	@Singleton
	@Provides
	fun provideOkHttpClient(): OkHttpClient {
		val loggingInterceptor = HttpLoggingInterceptor()
			.setLevel(HttpLoggingInterceptor.Level.BODY)
		return OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.build()
	}

	@Singleton
	@ApiRetrofit
	@Provides
	fun provideApiRetrofit(client: OkHttpClient): Retrofit =
		Retrofit.Builder()
			.baseUrl(baseUrlApi)
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.build()

	@Singleton
	@BackendRetrofit
	@Provides
	fun provideBackendRetrofit(client: OkHttpClient): Retrofit =
		Retrofit.Builder()
			.baseUrl(baseUrlBackend)
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.build()

	@Singleton
	@Provides
	fun provideApiService(@ApiRetrofit retrofit: Retrofit): PokemonApiService =
		retrofit.create(PokemonApiService::class.java)

	@Singleton
	@Provides
	fun provideBackendService(@BackendRetrofit retrofit: Retrofit): BackendApiService =
		retrofit.create(BackendApiService::class.java)
}
