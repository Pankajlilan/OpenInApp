package com.pankaj.inopenapp.presentation.onboarding

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pankaj.inopenapp.common.Constants
import com.pankaj.inopenapp.databinding.FragmentOnboardingBinding
import com.pankaj.inopenapp.preferences.EncryptedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

@AndroidEntryPoint
class OnboardingFragment : Fragment() {
  
  private val TAG: String = javaClass.name.toString()
  private val viewModel: OnboardingViewModel by viewModels()
  private lateinit var bearerToken: String
  
  private var _binding: FragmentOnboardingBinding? = null
  private val binding: FragmentOnboardingBinding
    get() = _binding!!
  private val PICK_FILE_REQUEST_CODE = 1001
  lateinit var encryptedPreferenceManager: EncryptedPreferenceManager
  
  private val activityResultLauncher =
    registerForActivityResult(
      ActivityResultContracts.RequestMultiplePermissions()
    )
    { permissions ->
      // Handle Permission granted/rejected
      permissions.entries.forEach {
        Log.d(TAG, "${it.key} = ${it.value}")
      }
    }
  
  
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
    return _binding?.root
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    requestForStoragePermissions()
    encryptedPreferenceManager = EncryptedPreferenceManager(requireContext())
    binding.btnContinue.setOnClickListener {
      pickFile()
    }
    val token = encryptedPreferenceManager.getString(Constants.TOKEN, "")
    if (token.isNotEmpty()) {
      navigateToDashboard(token)
    }
  }
  
  private fun pickFile() {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
      addCategory(Intent.CATEGORY_OPENABLE)
      type = "text/plain" // Specify the MIME type of the file you want to read
    }
    startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
  }
  
  @Deprecated("Deprecated in Java")
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      data?.data?.let { uri ->
        // Read the contents of the selected file
        readTextFile(uri)
      }
    }
  }
  
  private fun readTextFile(uri: Uri) {
    val inputStream = requireContext().contentResolver.openInputStream(uri)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val stringBuilder = StringBuilder()
    var line: String? = reader.readLine()
    while (line != null) {
      stringBuilder.append(line).append("\n")
      line = reader.readLine()
    }
    reader.close()
    val token = stringBuilder.toString()
    encryptedPreferenceManager.saveString(Constants.TOKEN, token)
    navigateToDashboard(token)
  }
  
  private fun navigateToDashboard(token: String?) {
    findNavController().navigate(
      OnboardingFragmentDirections.navigateToDashboardFragment(
        bearerToken = token
      )
    )
  }
  
  private fun requestForStoragePermissions() {
    CoroutineScope(Dispatchers.IO).launch {
      delay(1000)
      activityResultLauncher.launch(
        arrayOf(
          Manifest.permission.READ_EXTERNAL_STORAGE,
          Manifest.permission.MANAGE_EXTERNAL_STORAGE,
        )
      )
    }
  }
}