package com.im.dairyinventorymanagement

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.distinctUntilChanged
import com.im.dairyinventorymanagement.databinding.ActivityLoginBinding
import com.im.dairyinventorymanagement.presentation.viewmodel.HostViewModel
import com.im.dairyinventorymanagement.presentation.viewmodel.HostViewModelFactory
import com.saadahmedev.popupdialog.PopupDialog
import com.shubham.newsapiclientproject.data.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var factory: HostViewModelFactory

    lateinit var viewModel: HostViewModel

    private lateinit var dialog: PopupDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory).get(HostViewModel::class.java)

        binding.loginBtn.setOnClickListener {
//            startActivity(Intent(this, HostActivity::class.java))
            viewModel.loginUser(binding.username.text.toString(), binding.password.text.toString())
        }

        dialog = PopupDialog.getInstance(this)

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
                    if (it.data?.first()?.emp_id == "0") {
                        dialog.statusDialogBuilder()
                            .createErrorDialog()
                            .setHeading("Login Failed!")
                            .setDescription("Please check your credentials and try again.")
                            .build(Dialog::dismiss)
                            .show()
                    } else {
                        startActivity(Intent(this, HostActivity::class.java))
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