package com.vladimirorlov9.cryptocurrency.ui.user.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentProfileBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.ui.signup.PREF_CURRENT_UID
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

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
            val nameStr = "${it.firstName} ${it.lastName}"
            binding.nameEditText.setText(nameStr)
            binding.emailEditText.setText(it.email)
            binding.tradeNameEditText.setText("")
            binding.mobileNumberEditText.setText(it.phoneNumber)
            binding.birthdateEditText.setText("")
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