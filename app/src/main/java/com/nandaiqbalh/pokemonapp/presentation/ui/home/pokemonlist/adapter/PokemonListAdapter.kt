package com.nandaiqbalh.pokemonapp.presentation.ui.home.pokemonlist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nandaiqbalh.pokemonapp.data.remote.model.pokemonlist.response.Result
import com.nandaiqbalh.pokemonapp.databinding.ItemPokemonBinding
import com.nandaiqbalh.pokemonapp.util.GlideApp

class PokemonListAdapter : RecyclerView.Adapter<PokemonListAdapter.pokemonViewHolder>() {
	private var pokemonList: List<Result> = emptyList()

	var itemClickListener: ((item: Result) -> Unit)? = null

	private lateinit var onItemClickCallBack: OnItemClickCallBack

	fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
		this.onItemClickCallBack = onItemClickCallBack
	}

	private val diffCallback = object : DiffUtil.ItemCallback<Result>() {
		override fun areItemsTheSame(
			oldItem: Result,
			newItem: Result,
		): Boolean {
			return oldItem.url == newItem.url
		}

		override fun areContentsTheSame(
			oldItem: Result,
			newItem: Result,
		): Boolean {
			return oldItem.hashCode() == newItem.hashCode()
		}
	}

	private val differ = AsyncListDiffer(this, diffCallback)

	fun setList(pokemons: List<Result>?) {
		differ.submitList(pokemons)
	}

	inner class pokemonViewHolder(private val binding: ItemPokemonBinding) :
		RecyclerView.ViewHolder(binding.root) {
		@SuppressLint("SetTextI18n")
		fun bind(pokemon: Result) {
			binding.apply {

				tvItemPokemonTitle.text = pokemon.name


				GlideApp.with(itemView.context)
					.asBitmap()
					.load(pokemon.getImageUrl())
					.into(ivItemPokemon)

			}

			binding.root.setOnClickListener {
				onItemClickCallBack.onItemClicked(pokemon.name)
			}
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): pokemonViewHolder {
		val binding = ItemPokemonBinding.inflate(
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
		fun onItemClicked(pokemonName: String)
	}
}