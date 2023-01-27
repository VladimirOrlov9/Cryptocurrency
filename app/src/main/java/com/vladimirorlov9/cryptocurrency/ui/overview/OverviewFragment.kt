package com.vladimirorlov9.cryptocurrency.ui.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentOverviewBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * [OverviewFragment] displays list of crypto-currencies. It's the start fragment of nav_graph.
 */
class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    private val vm by viewModel<CurrenciesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_overview)
//        binding.bottomNav.setupWithNavController(navController)




//        val adapter = OverviewAdapter {
//            findNavController().navigate(R.id.action_CurrenciesFragment_to_cryptoDetailsFragment)
//        }
//        binding.overviewRecycler.layoutManager = LinearLayoutManager(requireContext())
//        binding.overviewRecycler.adapter = adapter
//        binding.overviewRecycler.addItemDecoration(
//            DividerItemDecoration(
//                requireContext(),
//                DividerItemDecoration.VERTICAL
//            )
//        )

//        vm.latestCryptoLD.observe(viewLifecycleOwner) {
//            it.prices?.let { prices ->
//                adapter.submitList(prices)
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}