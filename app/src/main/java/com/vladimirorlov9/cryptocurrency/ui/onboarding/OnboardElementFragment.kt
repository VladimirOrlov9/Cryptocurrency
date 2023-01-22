package com.vladimirorlov9.cryptocurrency.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import com.vladimirorlov9.cryptocurrency.R

/**
 * A simple [Fragment] subclass.
 * Use the [OnboardElementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

const val ONBOARD_ARG_LABEL = "onboard_label"
const val ONBOARD_ARG_ARTICLE = "onboard_article"
const val ONBOARD_ARG_IMAGE = "onboard_image"

class OnboardElementFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboard_element, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(ONBOARD_ARG_LABEL) }?.apply {
            val labelTextView = view.findViewById<TextView>(R.id.label)
            labelTextView.text = getString(ONBOARD_ARG_LABEL)
        }
        arguments?.takeIf { it.containsKey(ONBOARD_ARG_ARTICLE) }?.apply {
            val articleTextView = view.findViewById<TextView>(R.id.article)
            articleTextView.text = getString(ONBOARD_ARG_ARTICLE)
        }
        arguments?.takeIf { it.containsKey(ONBOARD_ARG_IMAGE) }?.apply {
            val image = view.findViewById<ImageView>(R.id.image)
            image.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    getInt(ONBOARD_ARG_IMAGE),
                    null
                )
            )
        }
    }

}