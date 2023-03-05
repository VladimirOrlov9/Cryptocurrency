package com.vladimirorlov9.cryptocurrency.ui.home.tokens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentHomeTokensBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.ui.search.BUNDLE_COIN_ID
import com.vladimirorlov9.cryptocurrency.ui.search.BUNDLE_COIN_NAME
import com.vladimirorlov9.cryptocurrency.ui.signup.PREF_CURRENT_UID
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HomeTokensFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeTokensFragment : Fragment() {

    private var _binding: FragmentHomeTokensBinding? = null
    private val binding get() = _binding!!

    private val vm by sharedViewModel<CurrenciesViewModel>()
    private lateinit var recyclerAdapter: HomeTokensAdapter

    private fun getUserId(): Long = requireActivity().getPreferences(Context.MODE_PRIVATE)
        .getLong(PREF_CURRENT_UID, -1L)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeTokensBinding.inflate(inflater, container, false)

        val userId = getUserId()
        vm.getStockCoinsStatus(userId.toInt())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter = HomeTokensAdapter { id, name ->
            val bundle = Bundle().apply {
                putString(BUNDLE_COIN_ID, id)
                putString(BUNDLE_COIN_NAME, name)
            }
            findNavController().navigate(R.id.action_homeFragment_to_coinPageFragment, bundle)
        }
        binding.tokensRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.tokensRecycler.adapter = recyclerAdapter

        vm.stockTokensLD.observe(viewLifecycleOwner) {
            recyclerAdapter.submitList(it)
        }
    }
}