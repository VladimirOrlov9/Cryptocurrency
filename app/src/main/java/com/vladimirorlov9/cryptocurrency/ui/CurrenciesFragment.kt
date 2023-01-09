package com.vladimirorlov9.cryptocurrency.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladimirorlov9.cryptocurrency.data.api.CoinMarketCapApi
import com.vladimirorlov9.cryptocurrency.data.repository.CurrenciesRepositoryImpl
import com.vladimirorlov9.cryptocurrency.databinding.FragmentCurrenciesBinding
import com.vladimirorlov9.cryptocurrency.domain.usecase.GetLatestCryptoStatusUseCase
import com.vladimirorlov9.data.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
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

        binding.saveButton.setOnClickListener {
            val name = binding.userNameEditText.text.toString()
            vm.saveText(name)
        }

        binding.readButton.setOnClickListener {
            vm.loadText()
        }

        vm.resultLiveData.observe(viewLifecycleOwner) {
            binding.userNameTextView.text = it
        }

        vm.latestCryptoLD.observe(viewLifecycleOwner) {
            binding.userNameTextView.text = it.serverCode.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}