package com.claire.carddiary.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.claire.carddiary.ViewModelFactory
import com.claire.carddiary.card.decoration.GridItemDecoration
import com.claire.carddiary.databinding.FragCardBinding
import com.claire.carddiary.utils.gone
import com.claire.carddiary.utils.px
import com.claire.carddiary.utils.visible


class CardFragment : Fragment() {

    private val vm: CardViewModel by viewModels { ViewModelFactory() }
    private val adapter: CardAdapter by lazy { CardAdapter() }
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
        vm.getCards()

        with(binding.rvCard) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@CardFragment.adapter
            addItemDecoration(GridItemDecoration(16.px, 16.px, 2))
        }

        with(adapter) {
            listener = {
                findNavController().navigate(CardFragmentDirections.toEditFragment(vm.getCard(it)))
            }
        }
    }

    private fun observeViewModel() {

        vm.cardList.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.imgDefault.visible()
            } else {
                binding.imgDefault.gone()
                adapter.submitList(it)
            }
        }

        vm.errorMsg.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        vm.navigateToEdit.observeSingle(viewLifecycleOwner, Observer {
            findNavController().navigate(CardFragmentDirections.toEditFragment(null))
        })
    }

}