package com.example.telepasspokemon.presentation.ui.detail

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.telepasspokemon.presentation.R
import com.example.telepasspokemon.presentation.databinding.ItemPokemonImageBinding
import javax.inject.Inject

class ImageListAdapter @Inject constructor(
    private val imageRequestManager: RequestManager,
) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    var images: List<String> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var imageLoadedListener: () -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(imageRequestManager, LoadListener(imageLoadedListener), parent)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    class ViewHolder(
        private val imageRequestManager: RequestManager,
        private val imageLoadedListener: RequestListener<Drawable?>,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_image, parent, false)
    ) {

        private val binding = ItemPokemonImageBinding.bind(itemView)

        fun bind(imageUrl: String) {
            imageRequestManager
                .load(imageUrl)
                .addListener(imageLoadedListener)
                .into(binding.ivPokemonLargeImage)
        }
    }

    class LoadListener(
        private val listener: () -> Unit
    ) : RequestListener<Drawable?> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable?>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable?>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            listener()
            return false
        }
    }
}