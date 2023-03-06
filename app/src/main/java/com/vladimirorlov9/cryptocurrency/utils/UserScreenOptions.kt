package com.vladimirorlov9.cryptocurrency.utils

import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.utils.models.UserOption

val userOptions = listOf(
    UserOption(
        iconResId = R.drawable.referral,
        labelResId = R.string.referral,
        navigationAction = 0
    ),
    UserOption(
        iconResId = R.drawable.reward,
        labelResId = R.string.reward_center,
        navigationAction = 0
    ),
    UserOption(
        iconResId = R.drawable.baseline_business_center_24,
        labelResId = R.string.NFT_centre,
        navigationAction = 0
    ),
    UserOption(
        iconResId = R.drawable.payment,
        labelResId = R.string.payment_methods,
        navigationAction = 0
    ),
    UserOption(
        iconResId = R.drawable.mail_outline,
        labelResId = R.string.contact_us,
        navigationAction = 0
    ),
    UserOption(
        iconResId = R.drawable.settings,
        labelResId = R.string.settings,
        navigationAction = 0
    ),
    UserOption(
        iconResId = R.drawable.security,
        labelResId = R.string.security,
        navigationAction = 0
    ),
    UserOption(
        iconResId = R.drawable.help_outlined,
        labelResId = R.string.help_and_support,
        navigationAction = 0
    ),
    UserOption(
        iconResId = R.drawable.share,
        labelResId = R.string.share_app,
        navigationAction = 0
    ),
    UserOption(
        iconResId = R.drawable.logout,
        labelResId = R.string.log_out,
        navigationAction = 0
    )
)