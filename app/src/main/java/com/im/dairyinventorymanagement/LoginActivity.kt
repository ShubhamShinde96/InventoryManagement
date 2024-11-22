package com.im.dairyinventorymanagement

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.im.dairyinventorymanagement.databinding.ActivityLoginBinding
import com.im.dairyinventorymanagement.presentation.viewmodel.HostViewModel
import com.im.dairyinventorymanagement.presentation.viewmodel.HostViewModelFactory
import com.saadahmedev.popupdialog.PopupDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var factory: HostViewModelFactory

    lateinit var viewModel: HostViewModel

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

        viewModel.loginDetails.observe(this) {
            if (it.data?.first()?.emp_id == "0") {
                PopupDialog.getInstance(this)
                    .statusDialogBuilder()
                    .createErrorDialog()
                    .setHeading("Failed")
                    .setDescription("Login failed! Please check your credentials.")
                    .build(Dialog::dismiss)
                    .show()
            } else {
                PopupDialog.getInstance(this)
                    .statusDialogBuilder()
                    .createSuccessDialog()
                    .setHeading("Success")
                    .setDescription("Logged in")
                    .build(Dialog::dismiss)
                    .show()
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