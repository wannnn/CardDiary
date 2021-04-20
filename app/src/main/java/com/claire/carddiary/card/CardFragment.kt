package com.claire.carddiary.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.claire.carddiary.CardApplication
import com.claire.carddiary.R
import com.claire.carddiary.ViewModelFactory
import com.claire.carddiary.base.FragmentBindingProvider
import com.claire.carddiary.card.decoration.GridItemDecoration
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.databinding.FragCardBinding
import com.claire.carddiary.utils.hideKeyboard
import com.claire.carddiary.utils.px


class CardFragment : Fragment() {

    private val binding: FragCardBinding by FragmentBindingProvider(R.layout.frag_card)
    private val vm: CardViewModel by viewModels { ViewModelFactory() }
    private val postAdapter: PostAdapter by lazy { PostAdapter() }
    private val cardAdapter: CardAdapter by lazy { CardAdapter() }
    private val concatAdapter by lazy { ConcatAdapter(
        postAdapter,
        cardAdapter.withLoadStateFooter(CardLoadStateAdapter(cardAdapter::retry)))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm

        observeData()

        with(binding.toolbar) {
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.more -> findNavController().navigate(CardFragmentDirections.toNavigateSheetDialog())
                }
                true
            }
        }

        binding.scrollTop.bindRecyclerView(binding.rvCard)

        with(binding.rvCard) {
            adapter = concatAdapter
            addItemDecoration(GridItemDecoration(15.px))
        }

        with(cardAdapter) {

            clickListener = {
                findNavController().navigate(CardFragmentDirections.toDetailFragment(it))
            }

            longClickListener = {
                findNavController().navigate(CardFragmentDirections.toNavigateEditDialog(it))
            }

            addLoadStateListener { loadState ->

                binding.progressEnable = loadState.refresh is LoadState.Loading

                // show or hide empty view
                if (loadState.append.endOfPaginationReached) {
                    binding.showDefault = cardAdapter.itemCount < 1
                }

                if (loadState.refresh !is LoadState.Loading){
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

    private fun observeData() {

        vm.cardList.observeSingle(viewLifecycleOwner) {
            cardAdapter.submitData(lifecycle, it)
            hideKeyboard()
        }

        vm.errorMsg.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        vm.clearSearch.observeSingle(viewLifecycleOwner) {
            binding.laySearch.searchView.setText("")
            binding.laySearch.searchView.clearFocus()
            vm.getCards()
            hideKeyboard()
        }

        vm.clearEnable.observe(viewLifecycleOwner) {
            binding.clearEnable = it
        }

        vm.listTypeChange.observe(viewLifecycleOwner) {
            binding.rvCard.apply {
                binding.listType = CardApplication.rvListType

                val viewState = layoutManager?.onSaveInstanceState()

                cardAdapter.submitData(lifecycle, vm.cardList.value ?: PagingData.empty())

                layoutManager = GridLayoutManager(context, CardApplication.rvListType)
                layoutManager?.onRestoreInstanceState(viewState)
            }
        }

        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<Card>(getString(R.string.nav_key_card))?.observe(viewLifecycleOwner) { card ->

            Toast.makeText(context, getString(R.string.uploading), Toast.LENGTH_SHORT).show()
            binding.rvCard.scrollToPosition(0)
            vm.upload(card)

            savedStateHandle.remove<Card>(getString(R.string.nav_key_card))
        }

        vm.progress.observeSingle(viewLifecycleOwner) {
            postAdapter.submitList(it)
        }

    }

}