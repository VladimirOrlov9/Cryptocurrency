package com.vladimirorlov9.cryptocurrency.ui.user.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentProfileBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.ui.signup.PREF_CURRENT_UID
import com.vladimirorlov9.cryptocurrency.utils.convertMillisToDate
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

    private lateinit var resultLauncherPickPhoto: ActivityResultLauncher<PickVisualMediaRequest>

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
                    val bitmap = getBitmapFromUri(uri)
                    val fileName = "image_${System.currentTimeMillis()}.jpg"
                    saveImage(requireContext(), bitmap, fileName)
                    registerNewImage(fileName)
                } else {
                    Toast.makeText(context, "Cannot update image", Toast.LENGTH_SHORT).show()
                }
            }

        return binding.root
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, uri))
    } else {
        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
    }

    private fun saveImage(context: Context, bitmap: Bitmap, name: String) {
        context.openFileOutput(name, Context.MODE_PRIVATE).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }

    private fun readImage(context: Context, name: String?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            context.openFileInput(name).use {
                bitmap = BitmapFactory.decodeStream(it)
            }
        } catch (ex: java.lang.Exception) {
            println(ex)
        }
        return bitmap
    }

    private fun registerNewImage(localFileName: String) {
        val bitmap = readImage(requireContext(), localFileName)
        Glide.with(this)
            .load(bitmap)
            .into(binding.image)
        vm.updateUserImage(uid.toInt(), localFileName)
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
                .load(readImage(requireContext(), it.image))
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
                inflate(R.menu.menu_user_image)
                show()
            }
        }
    }
}