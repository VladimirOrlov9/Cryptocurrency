package com.vladimirorlov9.cryptocurrency.ui.buycrypto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentBuyCryptoBinding

/**
 * A simple [Fragment] subclass.
 * Use the [BuyCryptoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BuyCryptoFragment : Fragment() {

    private var _binding: FragmentBuyCryptoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyCryptoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}