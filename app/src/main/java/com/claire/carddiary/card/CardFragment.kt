package com.claire.carddiary.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.claire.carddiary.NavGraphDirections
import com.claire.carddiary.R
import com.claire.carddiary.ViewModelFactory
import com.claire.carddiary.card.decoration.GridItemDecoration
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.databinding.FragCardBinding
import com.claire.carddiary.utils.*


class CardFragment : Fragment() {

    private val vm: CardViewModel by viewModels { ViewModelFactory() }
    private val postAdapter: PostAdapter by lazy { PostAdapter() }
    private val cardAdapter: CardAdapter by lazy { CardAdapter() }
    private val concatAdapter = ConcatAdapter(postAdapter, cardAdapter)
    private lateinit var binding: FragCardBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        binding.vm = vm

        with(binding.toolbar) {
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.arrow -> vm.setExpand()
                }
                true
            }
        }

        with(binding.rvCard) {
            layoutManager = GridLayoutManager(context, 1)
            adapter = concatAdapter
            addItemDecoration(GridItemDecoration(16.px, 16.px, 1))
        }

        with(cardAdapter) {

            withLoadStateFooter(CardLoadStateAdapter(this::retry))

            clickListener = {
                findNavController().navigate(CardFragmentDirections.toDetailFragment(it))
            }
            longClickListener = {
                Toast.makeText(context, "long clickListener!$it", Toast.LENGTH_SHORT).show()
            }

            addLoadStateListener { loadState ->

                if (loadState.refresh is LoadState.Loading){
                    // progress bar visible
                }
                else{
                    // progress bar gone

                    // getting the error
                    val error = when {
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }
                    error?.let {
                        Toast.makeText(context, it.error.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }

    }

    private fun observeViewModel() {

        vm.cardList.observeSingle(viewLifecycleOwner) {
            cardAdapter.submitData(lifecycle, it)

            if (it == null) {
                binding.txtDefault.visible()
            } else {
                binding.txtDefault.gone()
            }
        }

        vm.errorMsg.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        vm.navigateToEdit.observeSingle(viewLifecycleOwner) {
            findNavController().navigate(CardFragmentDirections.toEditFragment(null))
        }

        vm.isExpand.observe(viewLifecycleOwner) {
            if (it) {
                binding.fabEdit.expandFab()
                binding.fabProfile.expandFab()
            } else {
                binding.fabEdit.collapseFab()
                binding.fabProfile.collapseFab()
            }
        }

        vm.fabClick.observeSingle(viewLifecycleOwner) {
            vm.setExpand()
            when(it) {
                0 -> findNavController().navigate(CardFragmentDirections.toEditFragment(null))
                1 -> findNavController().navigate(NavGraphDirections.actionGlobalProfileFragment())
                else -> {}
            }
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

        vm.errorMsg.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

    }

}