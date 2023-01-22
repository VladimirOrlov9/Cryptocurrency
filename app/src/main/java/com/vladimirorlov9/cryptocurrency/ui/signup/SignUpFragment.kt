package com.vladimirorlov9.cryptocurrency.ui.signup

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.databinding.FragmentSignUpBinding
import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import com.vladimirorlov9.cryptocurrency.ui.onboarding.SPECIFICATION_ONBOARDING
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

const val TAG = "SignUpFragment"
const val PREF_CURRENT_UID = "current_uid"

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val vm by viewModel<CurrenciesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupButton.setOnClickListener {
            val firstName = binding.firstnameEdittext.text.toString()
            val lastName = binding.lastnameEdittext.text.toString()
            val email = binding.emailEdittext.text.toString()
            val phone = binding.phoneEdittext.text.toString()
            val password = binding.passwordEdittext.text.toString()

            if (validateInputs(
                    firstName,
                    lastName,
                    email,
                    phone,
                    password
                )
            ) {
                lockAllFields()
                vm.signUp(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    phone = phone,
                    password = password
                )
            }
        }

        binding.logInButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        vm.signUpResultLD.observe(viewLifecycleOwner) {
            if (it != null) {
                saveUidToSharedPrefs(it)
                finishOnboard()
                findNavController().navigate(R.id.action_signUpFragment_to_CurrenciesFragment)
            } else {
                unlockAllFields()
                Log.e(TAG, "Such user already exist.")
                // TODO add output of this error to user
            }
        }
    }

    private fun finishOnboard() {
        val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        with (pref.edit()) {
            putBoolean(SPECIFICATION_ONBOARDING, true)
            apply()
        }
    }

    private fun saveUidToSharedPrefs(uid: Long) {
        val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        with (pref.edit()) {
            putLong(PREF_CURRENT_UID, uid)
            apply()
        }
    }

    private fun validateInputs(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String
    ): Boolean {
        return firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() &&
                phone.isNotBlank() && password.isNotBlank()
        // TODO more detail validation
    }

    private fun lockAllFields() {
        binding.firstnameEdittext.isEnabled = false
        binding.lastnameEdittext.isEnabled = false
        binding.emailEdittext.isEnabled = false
        binding.phoneEdittext.isEnabled = false
        binding.passwordEdittext.isEnabled = false
        binding.logInButton.isEnabled = false
        binding.signupButton.isEnabled = false

        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun unlockAllFields() {
        binding.firstnameEdittext.isEnabled = true
        binding.lastnameEdittext.isEnabled = true
        binding.emailEdittext.isEnabled = true
        binding.phoneEdittext.isEnabled = true
        binding.passwordEdittext.isEnabled = true
        binding.logInButton.isEnabled = true
        binding.signupButton.isEnabled = true

        binding.progressCircular.visibility = View.INVISIBLE
    }

}