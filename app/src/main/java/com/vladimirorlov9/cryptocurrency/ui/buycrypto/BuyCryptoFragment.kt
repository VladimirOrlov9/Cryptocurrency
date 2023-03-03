package com.vladimirorlov9.cryptocurrency.ui.buycrypto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentBuyCryptoBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.utils.models.CoinInfoForBuy
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [BuyCryptoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BuyCryptoFragment : Fragment() {

    private var _binding: FragmentBuyCryptoBinding? = null
    private val binding get() = _binding!!

    private val vm by sharedViewModel<CurrenciesViewModel>()

    private var coinPriceInUSD: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyCryptoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(findNavController())

        val coinInfo = vm.coinInfoForBuyLD.value ?: CoinInfoForBuy()
        if (coinInfo.isInitialized()) {
            binding.toolbar.title = "${resources.getString(R.string.buy)} ${coinInfo.name}"
            binding.coinSymbolText.text = coinInfo.symbol
            coinPriceInUSD = coinInfo.price!!
        } else {
            findNavController().popBackStack()
        }

        binding.numpad.textLD.observe(viewLifecycleOwner) { coinsForBuy ->
            binding.enterPriceText.text = coinsForBuy
            val price = ((coinsForBuy.toDouble() * coinPriceInUSD) * 100).toInt() / 100.0
            binding.priceInUsd.text = "$$price"
        }

        binding.enterPriceText.doOnTextChanged { text, _, _, _ ->
            binding.buyButton.isEnabled = text.toString() != "0.0"
        }
    }
}