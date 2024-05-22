package com.nandaiqbalh.pokemonapp.presentation.ui.home.mypokemonlist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.response.DataX
import com.nandaiqbalh.pokemonapp.databinding.ItemMyPokemonBinding
import com.nandaiqbalh.pokemonapp.util.GlideApp

class MyPokemonListAdapter : RecyclerView.Adapter<MyPokemonListAdapter.pokemonViewHolder>() {

	private lateinit var onItemClickCallBack: OnItemClickCallBack

	fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
		this.onItemClickCallBack = onItemClickCallBack
	}

	private val diffCallback = object : DiffUtil.ItemCallback<DataX>() {
		override fun areItemsTheSame(
			oldItem: DataX,
			newItem: DataX,
		): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(
			oldItem: DataX,
			newItem: DataX,
		): Boolean {
			return oldItem.hashCode() == newItem.hashCode()
		}
	}

	private val differ = AsyncListDiffer(this, diffCallback)

	fun setList(pokemons: List<DataX>?) {
		differ.submitList(pokemons)
	}

	inner class pokemonViewHolder(private val binding: ItemMyPokemonBinding) :
		RecyclerView.ViewHolder(binding.root) {
		@SuppressLint("SetTextI18n")
		fun bind(pokemon: DataX) {
			binding.apply {

				tvItemPokemonTitle.text = "(${pokemon.nickname}) ${pokemon.name}"

				GlideApp.with(itemView.context)
					.asBitmap()
					.load(pokemon.getImageUrl())
					.into(ivItemPokemon)

			}

			binding.root.setOnClickListener {
				onItemClickCallBack.onItemClicked(pokemon.name, pokemon.nickname)
			}

			binding.tvRelease.setOnClickListener {
				onItemClickCallBack.onReleaseClicked(pokemon.id)
			}
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): pokemonViewHolder {
		val binding = ItemMyPokemonBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)
		return pokemonViewHolder(binding)
	}

	override fun onBindViewHolder(holder: pokemonViewHolder, position: Int) {
		holder.bind(differ.currentList[position])
	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}

	interface OnItemClickCallBack {
		fun onItemClicked(pokemonName: String, pokemonNickname: String?)
		fun onReleaseClicked(pokemonId: Int)

	}
}