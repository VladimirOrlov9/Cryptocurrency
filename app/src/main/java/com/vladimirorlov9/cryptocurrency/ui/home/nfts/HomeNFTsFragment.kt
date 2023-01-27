package com.vladimirorlov9.cryptocurrency.ui.home.nfts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladimirorlov9.cryptocurrency.R

/**
 * A simple [Fragment] subclass.
 * Use the [HomeNFTsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeNFTsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_n_f_ts, container, false)
    }
}