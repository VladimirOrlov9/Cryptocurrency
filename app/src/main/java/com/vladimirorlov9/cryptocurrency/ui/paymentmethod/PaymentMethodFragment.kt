package com.vladimirorlov9.cryptocurrency.ui.paymentmethod

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentPaymentMethodBinding
import com.vladimirorlov9.cryptocurrency.domain.models.BuyCoin
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.ui.buycrypto.COIN_AMOUNT_BUNDLE
import com.vladimirorlov9.cryptocurrency.ui.signup.PREF_CURRENT_UID
import com.vladimirorlov9.cryptocurrency.utils.models.CoinInfoForBuy
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentMethodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

const val COIN_AMOUNT_SYMBOL_BUNDLE = "coin_amount_symbol"

class PaymentMethodFragment : Fragment() {

    private var _binding: FragmentPaymentMethodBinding? = null
    private val binding get() = _binding!!

    private val vm by sharedViewModel<CurrenciesViewModel>()
    private lateinit var coinInfo: CoinInfoForBuy
    private var coinAmount: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentMethodBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(findNavController())

        coinInfo = vm.coinInfoForBuyLD.value ?: CoinInfoForBuy()
        coinAmount = arguments?.getDouble(COIN_AMOUNT_BUNDLE, -1.0) ?: -1.0
        if (coinAmount != -1.0) {
            binding.coinAmount.text = coinAmount.toString()
        } else {
            findNavController().popBackStack()
        }

        if (coinInfo.isInitialized()) {
            initUI(coinInfo, coinAmount)
        } else {
            findNavController().popBackStack()
        }

        val balance = vm.balanceInfoLD.value
        if (balance != null) {
            val availableBalanceString =
                "${resources.getString(R.string.available_balance)} ${balance.balance} USD"
            binding.availableWalletBalance.text = availableBalanceString
        } else {
            findNavController().popBackStack()
        }

        initCardClicks()

        binding.confirmButton.setOnClickListener {
            if (binding.p2pTradingRadio.isChecked) {

            } else if (binding.creditCardRadio.isChecked) {

            } else if (binding.walletRadio.isChecked) {
                vm.buyCryptoByWallet(
                    // TODO fix uid type for the whole application (to Int)
                    userId = getUserId().toInt(),
                    coin = BuyCoin(
                        logo = coinInfo.logo!!,
                        name = coinInfo.name!!,
                        symbol = coinInfo.symbol!!,
                        amount = coinAmount,
                        serverId = coinInfo.id!!
                    ),
                    price = coinInfo.price!! * coinAmount
                )

            }
        }

        vm.paymentSuccessfulLD.observe(viewLifecycleOwner) {
            if (it) {
                val bundle = Bundle().apply {
                    putString(COIN_AMOUNT_SYMBOL_BUNDLE, "$coinAmount ${coinInfo.symbol!!}")
                }
                findNavController().navigate(
                    R.id.action_paymentMethodFragment_to_paymentSuccessfulFragment,
                    bundle
                )
            }
        }
    }

    private fun getUserId(): Long = requireActivity().getPreferences(Context.MODE_PRIVATE)
        .getLong(PREF_CURRENT_UID, -1L)

    private fun initCardClicks() {
        binding.p2pTradingCard.setOnClickListener {
            binding.p2pTradingRadio.isChecked = true
            binding.creditCardRadio.isChecked = false
            binding.walletRadio.isChecked = false
        }
        binding.walletCard.setOnClickListener {
            binding.p2pTradingRadio.isChecked = false
            binding.creditCardRadio.isChecked = false
            binding.walletRadio.isChecked = true
        }
        binding.creditCardCard.setOnClickListener {
            binding.p2pTradingRadio.isChecked = false
            binding.creditCardRadio.isChecked = true
            binding.walletRadio.isChecked = false
        }
    }


    private fun initUI(coinInfo: CoinInfoForBuy, coins: Double) {
        val price = coins * coinInfo.price!!
        val priceUSDString = "$price USD"

        binding.p2pTradingPriceUsd.text = priceUSDString
        binding.creditCardPriceUsd.text = priceUSDString
        binding.walletPriceUsd.text = priceUSDString

        binding.coinSymbol.text = coinInfo.symbol
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}