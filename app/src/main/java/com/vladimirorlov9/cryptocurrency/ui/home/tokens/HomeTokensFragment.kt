package com.vladimirorlov9.cryptocurrency.ui.home.tokens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentHomeTokensBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.ui.signup.PREF_CURRENT_UID

/**
 * A simple [Fragment] subclass.
 * Use the [HomeTokensFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeTokensFragment : Fragment() {

    private var _binding: FragmentHomeTokensBinding? = null
    private val binding get() = _binding!!

    private val vm by viewModels<CurrenciesViewModel>()
    private lateinit var recyclerAdapter: HomeTokensAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = getUserId()
        vm.getStockCoinsStatus(userId)
    }

    private fun getUserId(): Long = requireActivity().getPreferences(Context.MODE_PRIVATE)
        .getLong(PREF_CURRENT_UID, -1L)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeTokensBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter = HomeTokensAdapter {
            // TODO add click event
        }
        binding.tokensRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.tokensRecycler.adapter = recyclerAdapter

        vm.stockTokensLD.observe(viewLifecycleOwner) {
            recyclerAdapter.submitList(it)
        }
    }
}