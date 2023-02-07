package com.vladimirorlov9.cryptocurrency.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentHomeBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.ui.MainActivity
import com.vladimirorlov9.cryptocurrency.ui.signup.PREF_CURRENT_UID
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val vm by viewModel<CurrenciesViewModel>()

    private lateinit var pagerAdapter: HomePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.loadBalanceInfo(getUserId())
    }

    private fun getUserId(): Long = requireActivity().getPreferences(Context.MODE_PRIVATE)
        .getLong(PREF_CURRENT_UID, -1L)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).setBottomNavVisibility(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.balanceInfoLD.observe(viewLifecycleOwner){
            val balanceStr = "$${it.balance}"
            val increaseStr = "${it.percentIncrease}% " + resources.getString(R.string.all_time_increase)
            binding.balanceEdittext.text = balanceStr
            binding.increaseStatusText.text = increaseStr
        }

        pagerAdapter = HomePagerAdapter(this)
        binding.pager.adapter = pagerAdapter

        TabLayoutMediator(binding.homeTabs, binding.pager) { tab, position ->
            if (position == 0)
                tab.text = resources.getString(R.string.tokens)
            else
                tab.text = resources.getString(R.string.nfts)
        }.attach()

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_search -> {
                    findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                    true
                }
                R.id.action_qr -> {

                    true
                }
                else ->
                    false
            }
        }
    }

}