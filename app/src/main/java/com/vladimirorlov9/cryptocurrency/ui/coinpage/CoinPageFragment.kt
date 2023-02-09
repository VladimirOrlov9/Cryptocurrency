package com.vladimirorlov9.cryptocurrency.ui.coinpage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentCoinPageBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.ui.search.BUNDLE_COIN_ID
import com.vladimirorlov9.cryptocurrency.ui.search.BUNDLE_COIN_NAME
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

/**
 * A simple [Fragment] subclass.
 * Use the [CoinPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

const val TAG = "CoinPageFragment"

class CoinPageFragment : Fragment() {

    private var _binding: FragmentCoinPageBinding? = null
    private val binding get() = _binding!!

    private val vm by viewModel<CurrenciesViewModel>()

    private lateinit var coinId: String
    private lateinit var coinName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!initParameters()) {
            Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        vm.loadCoinInfo(coinId)
        vm.loadHistoricalCoinData(
            coinId = coinId,
            days = 7
        )
    }

    private fun initParameters(): Boolean {
        val args = arguments
        args?.getString(BUNDLE_COIN_ID)?.let {
            coinId = it
        } ?: return false

        args.getString(BUNDLE_COIN_NAME)?.let {
            coinName = it
        } ?: return false

        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVMObservers()

        binding.toolbar.title = coinName
        binding.toolbar.setupWithNavController(findNavController())

        binding.graphicTabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        vm.loadHistoricalCoinData(
                            coinId = coinId,
                            days = 7
                        )
                    }
                    1 -> {
                        vm.loadHistoricalCoinData(
                            coinId = coinId,
                            days = 30
                        )
                    }
                    2 -> {
                        vm.loadHistoricalCoinData(
                            coinId = coinId,
                            days = 90
                        )
                    }
                    3 -> {
                        vm.loadHistoricalCoinData(
                            coinId = coinId,
                            days = 180
                        )
                    }
                    4 -> {
                        vm.loadHistoricalCoinData(
                            coinId = coinId,
                            days = 360
                        )
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // nothing to do
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // nothing to do
            }
        })

        binding.buyButton.setOnClickListener {
            findNavController().navigate(R.id.action_coinPageFragment_to_buyCryptoFragment)
        }
    }

    private fun setupVMObservers() {

        vm.coinInfoLD.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it.logo)
                .into(binding.coinLogo)

            binding.coinsHave.text = "0.0 ${it.symbol}"
        }

        vm.coinHistoryLD.observe(viewLifecycleOwner) {
            // TODO move calculations to domain layer
            Log.e(TAG, it.toString())

            if (it.isNotEmpty()) {
                val currentPrice = it.last().price
                val beforePrice = it.first().price
                val percentageLastDifference = (currentPrice - beforePrice) * 100 / (beforePrice)
                var percentageString = if (percentageLastDifference > 0) "+" else ""
                percentageString += ((percentageLastDifference * 100).roundToInt() / 100.0)
                percentageString += "%"

                binding.currentPrice.text = "$$currentPrice"
                binding.periodChangePercent.text = percentageString

                binding.graphic.setData(it.map { elem -> elem.price })
            }
        }

        vm.coinInfoForBuyLD.observe(viewLifecycleOwner) {
//            binding.sendButton.isClickable = true
            binding.buyButton.isClickable = true
//            binding.receiveButton.isClickable = true
        }
    }

}