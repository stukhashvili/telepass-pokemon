package com.example.telepasspokemon.presentation.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.telepasspokemon.presentation.R
import com.example.telepasspokemon.presentation.databinding.ItemPokemonBinding
import javax.inject.Inject

class PokemonListAdapter @Inject constructor(
    private val glideRequest: RequestManager
) : ListAdapter<PokemonItemUi, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    var itemClickListener: (PokemonItemUi, ItemPokemonBinding) -> Unit = { _, _ -> }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isLoadingPlaceHolder) LOADER else ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LOADER -> LoadingViewHolder(parent)
            else -> ViewHolder(glideRequest, parent).apply {
                itemView.setOnClickListener {
                    val item = runCatching { getItem(adapterPosition) }
                    item.getOrNull()?.let { pokemon ->
                        itemClickListener(pokemon, binding)
                    }
                }
            }
        }
    }

    fun submitState(state: HomeState.Page) {
        submitList(
            if (state.hasNext) {
                state.pokemonList + PokemonItemUi.loadingPlaceholder()
            } else {
                state.pokemonList
            }
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ViewHolder -> holder.bind(item)
        }
    }

    class ViewHolder(
        private val glide: RequestManager,
        parent: ViewGroup,
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
    ) {
        val binding = ItemPokemonBinding.bind(itemView)

        fun bind(item: PokemonItemUi) = with(binding) {
            glide.load(item.image).into(ivPokemonImage)
            tvPokemonName.text = item.name
        }
    }

    class LoadingViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
    ) {
        init {
            Log.d("Mylog", "created")
        }
    }

    companion object {
        private const val ITEM = 1
        private const val LOADER = 2

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PokemonItemUi>() {

            override fun areContentsTheSame(
                oldItem: PokemonItemUi,
                newItem: PokemonItemUi
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: PokemonItemUi, newItem: PokemonItemUi): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}