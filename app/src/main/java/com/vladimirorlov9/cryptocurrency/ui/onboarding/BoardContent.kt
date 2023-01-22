package com.vladimirorlov9.cryptocurrency.ui.onboarding

import com.vladimirorlov9.cryptocurrency.R

data class BoardContent (
    val label: String,
    val article: String,
    val imageResId: Int
)

val boardsContent = listOf(
    BoardContent(
        label = "Trade Crypto Anywhere and Anytime",
        article = "Buy and sell thousands of crypto tokens and NFTs anywhere and anytime in the world via card and P2P exchange",
        imageResId = R.drawable.pluh
    ),
    BoardContent(
        label = "Manage Your Digital Assets with Ease",
        article = "View, spend and store your digital assets like tokens or NFTs safely via your wallet.",
        imageResId = R.drawable.suicide_frog
    ),
    BoardContent(
        label = "Refer Your Friends and Earn More",
        article = "Refer a friend and earn commision on some selected transactions they complete.",
        imageResId = R.drawable.anime_girl
    )
)
