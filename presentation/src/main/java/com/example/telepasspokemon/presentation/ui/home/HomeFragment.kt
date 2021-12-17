package com.example.telepasspokemon.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.telepasspokemon.presentation.databinding.FragmentHomeBinding
import com.example.telepasspokemon.presentation.navigation.navigate
import com.example.telepasspokemon.presentation.ui.detail.DetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.livedata.onStates
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var pokemonListAdapter: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        onStates(viewModel) { state ->
            binding.progressBar.isVisible = state is HomeState.Initial
            if (state !is HomeState.Error) binding.tvError.isVisible = false
            when (state) {
                is HomeState.Page -> onPageUpdated(state)
                is HomeState.Error -> onErrorPage(state.page)
            }
        }
        binding.tvError.setOnClickListener {
            viewModel.loadNext()
        }
    }

    private fun initRecyclerView() = with(binding.recyclerViewPokemonList) {
        adapter = pokemonListAdapter
        addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
                    val lastVisiblePos = layoutManager?.findLastVisibleItemPosition()
                    if (lastVisiblePos != null &&
                        lastVisiblePos >= pokemonListAdapter.itemCount - PRELOAD_OFFSET
                    ) {
                        viewModel.loadNext()
                    }
                }
            }
        )
        pokemonListAdapter.itemClickListener = { pokemon, binding ->
            binding.tvPokemonName.transitionName =
                DetailsFragment.TITLE_TRANSITION_NAME_PREFIX + pokemon.name
            binding.ivPokemonImage.transitionName =
                DetailsFragment.IMAGE_TRANSITION_NAME_PREFIX + pokemon.name
            navigate(
                DetailsFragment.newInstance(pokemon.name, pokemon.image)
            ) {
                setReorderingAllowed(true)
                addSharedElement(
                    binding.tvPokemonName,
                    binding.tvPokemonName.transitionName
                )
                addSharedElement(
                    binding.ivPokemonImage,
                    binding.ivPokemonImage.transitionName
                )
            }
        }
    }

    private fun onPageUpdated(state: HomeState.Page) {
        pokemonListAdapter.submitState(state)
    }

    private fun onErrorPage(state: HomeState.Page) {
        binding.tvError.isVisible = state.pokemonList.isEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PRELOAD_OFFSET = 3
    }
}