package com.im.dairyinventorymanagement

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.distinctUntilChanged
import com.google.gson.Gson
import com.im.dairyinventorymanagement.data.model.response.LoginResponseData
import com.im.dairyinventorymanagement.databinding.ActivityLoginBinding
import com.im.dairyinventorymanagement.presentation.viewmodel.HostViewModel
import com.im.dairyinventorymanagement.presentation.viewmodel.HostViewModelFactory
import com.im.dairyinventorymanagement.utils.EMPTY_STRING
import com.im.dairyinventorymanagement.utils.SharedPreferencesHandler
import com.im.dairyinventorymanagement.utils.SharedPreferencesHandler.Companion.LOGIN_DETAILS
import com.saadahmedev.popupdialog.PopupDialog
import com.shubham.newsapiclientproject.data.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var factory: HostViewModelFactory

    @Inject
    lateinit var sharedPrefsHandler: SharedPreferencesHandler

    lateinit var viewModel: HostViewModel

    private lateinit var dialog: PopupDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory).get(HostViewModel::class.java)

        binding.loginBtn.setOnClickListener {
            viewModel.loginUser(binding.username.text.toString(), binding.password.text.toString())
        }

        dialog = PopupDialog.getInstance(this)

        (Gson().fromJson(sharedPrefsHandler.getString(LOGIN_DETAILS, EMPTY_STRING), LoginResponseData::class.java))?.let {
            startActivity(Intent(this, HostActivity::class.java))
        }

        viewModel.loginDetails.distinctUntilChanged().observe(this) {
            when (it) {
                is Resource.Error -> {
                    binding.dimmingOverlay.visibility = View.GONE
                    dialog.statusDialogBuilder()
                        .createErrorDialog()
                        .setHeading("Error!")
                        .setDescription("Something went wrong, please try again.")
                        .build(Dialog::dismiss)
                        .show()
                }

                is Resource.Loading -> {
                    binding.dimmingOverlay.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.dimmingOverlay.visibility = View.GONE
                    if (it.data?.first()?.status?.lowercase() == "success") {
                        sharedPrefsHandler.putString(LOGIN_DETAILS, Gson().toJson(it.data.first()))
                        startActivity(Intent(this, HostActivity::class.java))
                    } else {
                        dialog.statusDialogBuilder()
                            .createErrorDialog()
                            .setHeading("Login Failed!")
                            .setDescription("Please check your credentials and try again.")
                            .build(Dialog::dismiss)
                            .show()
                    }
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}