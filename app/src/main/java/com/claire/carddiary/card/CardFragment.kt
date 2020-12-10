package com.claire.carddiary.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.claire.carddiary.R
import com.claire.carddiary.ViewModelFactory
import com.claire.carddiary.card.decoration.GridItemDecoration
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.databinding.FragCardBinding
import com.claire.carddiary.utils.*
import kotlinx.coroutines.launch


class CardFragment : Fragment() {

    private val vm: CardViewModel by viewModels { ViewModelFactory() }
    private val postAdapter: PostAdapter by lazy { PostAdapter() }
    private val cardAdapter: CardAdapter by lazy { CardAdapter() }
    private val concatAdapter by lazy { ConcatAdapter(
        postAdapter,
        cardAdapter.withLoadStateFooter(CardLoadStateAdapter(cardAdapter::retry)))
    }
    private lateinit var binding: FragCardBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        binding.appbar.outlineProvider = null
        binding.vm = vm

        with(binding.rvCard) {
            layoutManager = GridLayoutManager(context, 1)
            adapter = concatAdapter
            addItemDecoration(GridItemDecoration(16.px, 16.px, 1))
        }

        with(binding.searchView) {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    lifecycleScope.launch {
                        vm.searchQueryChannel.send(newText.toString())
                    }
                    return true
                }
            })
        }

        with(cardAdapter) {

            clickListener = {
                findNavController().navigate(CardFragmentDirections.toDetailFragment(it))
            }

            longClickListener = {
                Toast.makeText(context, "long clickListener!$it", Toast.LENGTH_SHORT).show()
            }

            addLoadStateListener { loadState ->

                if (loadState.refresh is LoadState.Loading){
                    binding.progress.visible()
                }
                else {
                    binding.progress.gone()

                    // getting the error
                    val error = when {
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }
                    error?.let {
                        if (loadState.refresh is LoadState.Error) {
                            AlertDialog.Builder(requireContext())
                                .setMessage(getString(R.string.network_error))
                                .setPositiveButton(getString(R.string.try_again)) { _, _ ->
                                    retry()
                                }
                                .show()
                        }
                        Toast.makeText(context, it.error.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }

    }

    private fun observeViewModel() {

        vm.cardList.observeSingle(viewLifecycleOwner) {
            cardAdapter.submitData(lifecycle, it)

            binding.txtDefault.apply {
                if (it == null) visible() else gone()
            }
        }

        vm.errorMsg.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        vm.navigateToEdit.observeSingle(viewLifecycleOwner) {
            findNavController().navigate(CardFragmentDirections.toEditFragment(null))
        }

        vm.optionClick.observeSingle(viewLifecycleOwner) {
            findNavController().navigate(CardFragmentDirections.toNavigateSheetDialog())
        }

        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<Card>(getString(R.string.nav_key_card))?.observe(viewLifecycleOwner) { card ->

            binding.rvCard.smoothScrollToPosition(0)
            vm.upload(card)

            savedStateHandle.remove<Card>(getString(R.string.nav_key_card))
        }

        vm.progress.observeSingle(viewLifecycleOwner) {
            postAdapter.submitList(it)
        }

        vm.searchResult.observe(viewLifecycleOwner) { data ->
            data?.let { cardAdapter.submitData(lifecycle, it) }
        }

    }

}