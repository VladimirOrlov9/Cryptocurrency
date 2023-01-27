package com.vladimirorlov9.cryptocurrency.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vladimirorlov9.cryptocurrency.ui.home.nfts.HomeNFTsFragment
import com.vladimirorlov9.cryptocurrency.ui.home.tokens.HomeTokensFragment

class HomePagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) HomeTokensFragment() else HomeNFTsFragment()
    }

}