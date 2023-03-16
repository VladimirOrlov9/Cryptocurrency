package com.vladimirorlov9.cryptocurrency.ui.user

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentUserBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.ui.signup.PREF_CURRENT_UID
import com.vladimirorlov9.cryptocurrency.utils.readImage
import com.vladimirorlov9.cryptocurrency.utils.userOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private val vm by sharedViewModel<CurrenciesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        vm.loadUserOverview(getUserId().toInt())
    }

    private fun getUserId(): Long = requireActivity().getPreferences(Context.MODE_PRIVATE)
        .getLong(PREF_CURRENT_UID, -1L)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.optionsRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val matDiv = MaterialDividerItemDecoration(
                context,
                MaterialDividerItemDecoration.VERTICAL
            ).apply {
                isLastItemDecorated = false
                dividerInsetStart = 50
                dividerInsetEnd = 50
                dividerThickness = 2
                dividerColor = Color.LTGRAY
            }
            addItemDecoration(matDiv)
            adapter = UserOptionsAdapter(userOptions) { navId ->
                if (navId == 0) {
                    Toast.makeText(context, "No such action yet.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.userIdLinear.setOnClickListener {
            val id = binding.userId.text
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(resources.getString(R.string.app_name), id)
            clipboard.setPrimaryClip(clip)
        }

        binding.userOverviewCard.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_profileFragment)
        }

        vm.userOverviewLD.observe(viewLifecycleOwner) { user ->
            val nameString = "${user.firstName} ${user.lastName}"
            binding.userName.text = nameString
            val emailString = "${resources.getString(R.string.email_concat)} ${user.email}"
            binding.profileEmail.text = emailString
            binding.userId.text = user.uid.toString()

            Glide.with(this)
                .load(readImage(requireContext(), user.image))
                .error(ResourcesCompat.getDrawable(resources, R.drawable.baseline_person_24, null))
                .into(binding.profilePicture)
        }
    }
}