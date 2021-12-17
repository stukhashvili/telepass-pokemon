package com.example.telepasspokemon.presentation.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.RequestManager
import com.example.telepasspokemon.presentation.R
import com.example.telepasspokemon.presentation.databinding.FragmentDetailsBinding
import com.example.telepasspokemon.presentation.ui.detail.DetailsViewModel.Companion.POKEMON_NAME
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.livedata.onStates
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    companion object {
        const val IMAGE_TRANSITION_NAME_PREFIX = "imageTransitionName_"
        const val TITLE_TRANSITION_NAME_PREFIX = "titleTransitionName_"
        private const val POKEMON_IMAGE = "POKEMON_IMAGE"
        fun newInstance(
            pokemonName: String,
            image: String,
        ) = DetailsFragment().apply {
            arguments = bundleOf(
                POKEMON_NAME to pokemonName,
                POKEMON_IMAGE to image,
            )
        }
    }

    private val viewModel: DetailsViewModel by viewModels()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var requestManager: RequestManager

    @Inject
    lateinit var imageListAdapter: ImageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        initTitleTransition()
        initImageTransition()
        initImageViewPager()
        onStates(viewModel) { state ->
            when (state) {
                is DetailsState.PokemonUi -> onPokemonReceived(state)
            }
        }
    }

    private fun initTitleTransition() {
        binding.tvTitle.transitionName = TITLE_TRANSITION_NAME_PREFIX +
                requireArguments().getString(POKEMON_NAME)
    }

    private fun initImageTransition() {
        binding.ivPokemonImage.transitionName = IMAGE_TRANSITION_NAME_PREFIX +
                requireArguments().getString(POKEMON_NAME)
        requestManager.load(requireArguments().getString(POKEMON_IMAGE))
            .into(binding.ivPokemonImage)
    }

    private fun initImageViewPager() = with(binding.viewPagerImages) {
        adapter = imageListAdapter
        imageListAdapter.imageLoadedListener = {
            binding.ivPokemonImage.isVisible = false
        }
        TabLayoutMediator(binding.tabLayoutIndicators, this) { _, _ ->

        }.attach()
    }

    private fun onPokemonReceived(pokemon: DetailsState.PokemonUi) {
        binding.tvTitle.text = pokemon.name
        imageListAdapter.images = pokemon.images
        binding.tvStats.text = pokemon.stats.map(PokemonStatUi::name).joinToString()
        binding.tvCategory.text = pokemon.types.joinToString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}