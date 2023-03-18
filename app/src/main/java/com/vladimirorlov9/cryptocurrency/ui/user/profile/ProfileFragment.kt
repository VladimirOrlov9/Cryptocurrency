package com.vladimirorlov9.cryptocurrency.ui.user.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentProfileBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.ui.signup.PREF_CURRENT_UID
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.stfalcon.imageviewer.StfalconImageViewer
import com.vladimirorlov9.cryptocurrency.utils.*

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

    private lateinit var resultLauncherPickPhoto: ActivityResultLauncher<PickVisualMediaRequest>
    private var profileImageBitmap: Bitmap? = null

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

        resultLauncherPickPhoto =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    val bitmap = getBitmapFromUri(requireContext(), uri)
                    val fileName = "image_${System.currentTimeMillis()}.jpg"
                    saveImage(requireContext(), bitmap, fileName)
                    registerNewImage(fileName)
                } else {
                    Toast.makeText(context, "Cannot update image", Toast.LENGTH_SHORT).show()
                }
            }

        return binding.root
    }

    private fun registerNewImage(localFileName: String) {
        profileImageBitmap = readImage(requireContext(), localFileName)
        Glide.with(this)
            .load(profileImageBitmap)
            .into(binding.image)
        vm.updateUserImage(uid.toInt(), localFileName)

        setupUpdateProfilePictureButton()
    }

    private fun getUserId(): Long = requireActivity().getPreferences(Context.MODE_PRIVATE)
        .getLong(PREF_CURRENT_UID, -1L)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setupWithNavController(findNavController())
        binding.userId.text = uid.toString()
        setupObservers()
        setupListeners()
    }

    private fun ifInfoTheSame(
        firstName: String,
        lastName: String,
        email: String,
        tradeName: String,
        phoneNumber: String,
        birthday: Long
    ): Boolean {
        val prevData = vm.userFullInfoLD.value
        return prevData?.firstName == firstName &&
                prevData.lastName == lastName &&
                prevData.email == email &&
                prevData.tradeName == tradeName &&
                prevData.phoneNumber == phoneNumber &&
                prevData.birthday == birthday
    }

    private fun setupListeners() {
        binding.uidLinear.setOnClickListener {
            val id = binding.userId.text
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(resources.getString(R.string.app_name), id)
            clipboard.setPrimaryClip(clip)
        }

        binding.updateButton.setOnClickListener {
            val firstName = binding.firstnameEditText.text.toString()
            val lastName = binding.lastnameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val tradeName = binding.tradeNameEditText.text.toString()
            val mobileNumber = binding.mobileNumberEditText.text.toString()
            val birthday = convertDateToMillis(binding.birthdate.text.toString())

            if (ifInfoTheSame(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    tradeName = tradeName,
                    phoneNumber = mobileNumber,
                    birthday = birthday
                )
            )
                Toast.makeText(context, "Data the same!", Toast.LENGTH_SHORT).show()
            else {
                vm.updateUserInfo(
                    uid = uid.toInt(),
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    tradeName = tradeName,
                    phoneNumber = mobileNumber,
                    birthday = birthday
                )
                findNavController().popBackStack()
            }

        }

        binding.calendarButton.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select your birth date")
                .setSelection(convertDateToMillis(binding.birthdate.text.toString()) + 1)
                .build()

            datePicker.addOnPositiveButtonClickListener {
                binding.birthdate.text = convertMillisToDate(it)
            }

            datePicker.show(childFragmentManager, "Pick new birth date")
        }
    }

    private fun setupObservers() {
        vm.userFullInfoLD.observe(viewLifecycleOwner) {
            binding.firstnameEditText.setText(it.firstName)
            binding.lastnameEditText.setText(it.lastName)
            binding.emailEditText.setText(it.email)
            binding.tradeNameEditText.setText(it.tradeName)
            binding.mobileNumberEditText.setText(it.phoneNumber)
            binding.birthdate.text = convertMillisToDate(it.birthday)

            profileImageBitmap = readImage(requireContext(), it.image)

            setupUpdateProfilePictureButton()
        }
    }

    private fun setupUpdateProfilePictureButton() {
        Glide.with(this)
            .load(profileImageBitmap)
            .error(ResourcesCompat.getDrawable(resources, R.drawable.baseline_person_24, null))
            .into(binding.image)

        binding.image.setOnClickListener {
            if (profileImageBitmap != null) {
                PopupMenu(it.context, it).apply {
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.open_image -> {
                                StfalconImageViewer.Builder(
                                    requireContext(),
                                    arrayListOf(profileImageBitmap)
                                ) { view, image ->
                                    Glide.with(this@ProfileFragment)
                                        .load(image)
                                        .into(view)
                                }
                                    .show()
                                true
                            }
                            R.id.update_image -> {
                                resultLauncherPickPhoto.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                                true
                            }
                            R.id.remove_image -> {
                                removeProfilePicture()
                                true
                            }
                            else -> false
                        }
                    }
                    inflate(R.menu.menu_user_image)
                    show()
                }
            } else {
                PopupMenu(it.context, it).apply {
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.update_image -> {
                                resultLauncherPickPhoto.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                                true
                            }
                            else -> false
                        }
                    }
                    inflate(R.menu.menu_user_update_image)
                    show()
                }
            }
        }
    }

    private fun removeProfilePicture() {
        vm.deleteProfilePicture(uid.toInt())

        profileImageBitmap = null
        setupUpdateProfilePictureButton()
    }
}