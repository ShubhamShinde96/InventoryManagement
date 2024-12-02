package com.im.dairyinventorymanagement

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.im.dairyinventorymanagement.databinding.ActivityHostBinding
import com.im.dairyinventorymanagement.presentation.viewmodel.HostViewModel
import com.im.dairyinventorymanagement.presentation.viewmodel.HostViewModelFactory
import com.im.dairyinventorymanagement.utils.SharedPreferencesHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding

    @Inject
    lateinit var factory: HostViewModelFactory

    @Inject
    lateinit var sharedPrefsHandler: SharedPreferencesHandler

    lateinit var viewModel: HostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[HostViewModel::class.java]

        onBackPressedDispatcher.apply {
            addCallback {
                if (Navigation.findNavController(this@HostActivity, R.id.hostContainer)
                        .navigateUp()
                ) return@addCallback else finish()
            }
        }
    }
}
