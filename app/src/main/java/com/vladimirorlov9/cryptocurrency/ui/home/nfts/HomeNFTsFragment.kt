package com.vladimirorlov9.cryptocurrency.ui.home.nfts

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vladimirorlov9.cryptocurrency.databinding.FragmentHomeNftsBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.ui.signup.PREF_CURRENT_UID
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HomeNFTsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeNFTsFragment : Fragment() {

    private var _binding: FragmentHomeNftsBinding? = null
    private val binding get() = _binding!!

    private val vm by viewModel<CurrenciesViewModel>()
    private lateinit var recyclerAdapter: HomeNFTsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = getUserId()
        vm.getStockNFTsStatus(userId)
    }

    private fun getUserId(): Long = requireActivity().getPreferences(Context.MODE_PRIVATE)
        .getLong(PREF_CURRENT_UID, -1L)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeNftsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter = HomeNFTsAdapter {
            // TODO add click event
        }
        binding.nftsRecycler.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        binding.nftsRecycler.adapter = recyclerAdapter

        vm.stockNFTsLD.observe(viewLifecycleOwner) {
            recyclerAdapter.submitList(it)
        }
    }
}