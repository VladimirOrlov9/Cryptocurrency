package com.vladimirorlov9.cryptocurrency.ui.paymentsuccessful

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentPaymentSuccessfulBinding
import com.vladimirorlov9.cryptocurrency.ui.paymentmethod.COIN_AMOUNT_SYMBOL_BUNDLE

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentSuccessfulFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentSuccessfulFragment : Fragment() {

    private var _binding: FragmentPaymentSuccessfulBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentSuccessfulBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coinText = arguments?.getString(COIN_AMOUNT_SYMBOL_BUNDLE)
        if (coinText != null) {
            initUI(coinText)
        } else {
            findNavController().popBackStack()
        }
    }

    private fun initUI(coinText: String) {
        binding.coins.text = coinText

        binding.doneButton.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}