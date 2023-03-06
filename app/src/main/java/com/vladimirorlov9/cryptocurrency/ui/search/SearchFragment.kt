package com.vladimirorlov9.cryptocurrency.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentSearchBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

const val BUNDLE_COIN_ID = "coin_id"
const val BUNDLE_COIN_NAME = "coin_name"

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val vm by sharedViewModel<CurrenciesViewModel>()
    private lateinit var recyclerAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.loadAllCoins()

        recyclerAdapter = SearchAdapter { id, name ->
            // navigate
            val bundle = Bundle().apply {
                putString(BUNDLE_COIN_ID, id)
                putString(BUNDLE_COIN_NAME, name)
            }
            findNavController().navigate(R.id.action_searchFragment_to_coinPageFragment, bundle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setupWithNavController(findNavController())

        binding.searchResultsRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchResultsRecycler.adapter = recyclerAdapter

        vm.allCoinsLD.observe(viewLifecycleOwner) {
            recyclerAdapter.submitList(it)
        }

        binding.searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                recyclerAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty())
                    recyclerAdapter.filter.filter("")
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}