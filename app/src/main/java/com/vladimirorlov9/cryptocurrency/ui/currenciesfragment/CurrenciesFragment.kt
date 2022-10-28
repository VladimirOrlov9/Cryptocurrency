package com.vladimirorlov9.cryptocurrency.ui.currenciesfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vladimirorlov9.cryptocurrency.databinding.FragmentCurrenciesBinding
import com.vladimirorlov9.cryptocurrency.ui.app.App
import com.vladimirorlov9.cryptocurrency.ui.currenciesfragment.recycler.CurrenciesListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * [CurrenciesFragment] displays list of crypto-currencies. It's the start fragment of nav_graph.
 */
class CurrenciesFragment : Fragment() {

    private var _binding: FragmentCurrenciesBinding? = null
    private val binding get() = _binding!!

    private val vm by viewModel<CurrenciesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrenciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.latestCryptoLD.observe(viewLifecycleOwner) {
            val layoutManager = LinearLayoutManager(
                requireActivity().applicationContext
            )
            binding.currenciesRecycler.layoutManager = layoutManager

            val adapter = CurrenciesListAdapter()
            adapter.submitList(it.prices)
            binding.currenciesRecycler.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}