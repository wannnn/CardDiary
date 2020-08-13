package com.claire.carddiary.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.claire.carddiary.databinding.FragCardBinding
import com.claire.carddiary.decoration.GridItemDecoration
import com.claire.carddiary.utils.px

class CardFragment : Fragment() {

    private val vm: CardViewModel by viewModels()
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

        with(binding.rvCard) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@CardFragment.adapter
            addItemDecoration(GridItemDecoration(16.px, 16.px, 2))
        }

        with(adapter) {
            listener = {
                Toast.makeText(context, "click$it", Toast.LENGTH_SHORT).show()
            }
        }

        vm.cardList.observe(viewLifecycleOwner) {
            it?.let(adapter::submitList)
        }
    }
}