package com.vladimirorlov9.cryptocurrency.ui.user.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentProfileBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.ui.signup.PREF_CURRENT_UID
import com.vladimirorlov9.cryptocurrency.utils.convertMillisToDate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.time.Instant
import java.time.ZoneId
import java.util.Date

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val vm by sharedViewModel<CurrenciesViewModel>()

    private var uid: Long = -1L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        uid = getUserId()
        if (uid != -1L) {
            vm.loadUserFullInfo(uid.toInt())
        } else
            findNavController().popBackStack()

        return binding.root
    }

    private fun getUserId(): Long = requireActivity().getPreferences(Context.MODE_PRIVATE)
        .getLong(PREF_CURRENT_UID, -1L)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setupWithNavController(findNavController())
        binding.userId.text = uid.toString()
        setupUpdateProfilePictureButton()
        setupObservers()
    }

    private fun setupObservers() {
        vm.userFullInfoLD.observe(viewLifecycleOwner) {
            binding.firstnameEditText.setText(it.firstName)
            binding.lastnameEditText.setText(it.lastName)
            binding.emailEditText.setText(it.email)
            binding.tradeNameEditText.setText(it.tradeName)
            binding.mobileNumberEditText.setText(it.phoneNumber)
            binding.birthdateEditText.setText(convertMillisToDate(it.birthday))

            Glide.with(this)
                .load(it.image)
                .error(ResourcesCompat.getDrawable(resources, R.drawable.baseline_person_24, null))
                .into(binding.image)
        }
    }

    private fun setupUpdateProfilePictureButton() {
        binding.image.setOnClickListener {
            PopupMenu(it.context, it).apply {
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.open_image -> {
                            // TODO open fullscreen profile picture
                            true
                        }
                        R.id.update_image -> {
                            // TODO open gallery for choose the new profile picture
                            true
                        }
                        else -> false
                    }
                }
                inflate(R.menu.menu_user_image)
                show()
            }
        }
    }
}