package com.vladimirorlov9.cryptocurrency.ui.user

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentUserBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.ui.signup.PREF_CURRENT_UID
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
            val matDiv = MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL).apply {
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
            val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // TODO change label to application name
            val clip = ClipData.newPlainText("My application", id)
            clipboard.setPrimaryClip(clip)
        }

        vm.userOverviewLD.observe(viewLifecycleOwner) { user ->
            val nameString = "${user.firstName} ${user.lastName}"
            binding.userName.text = nameString
            val emailString = "${resources.getString(R.string.email_concat)} ${user.email}"
            binding.profileEmail.text = emailString
            binding.userId.text = user.uid.toString()
        }
    }
}