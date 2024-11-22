package com.im.dairyinventorymanagement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.im.dairyinventorymanagement.databinding.ActivityHostBinding
import com.im.dairyinventorymanagement.presentation.viewmodel.HostViewModelFactory
import javax.inject.Inject

class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding

    @Inject
    lateinit var factory: HostViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}