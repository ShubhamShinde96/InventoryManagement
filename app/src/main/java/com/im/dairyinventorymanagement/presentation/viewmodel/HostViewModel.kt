package com.im.dairyinventorymanagement.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.im.dairyinventorymanagement.data.model.response.LoginResponseData

class HostViewModel(application: Application): AndroidViewModel(application) {

    private val _loginDetails = MutableLiveData<LoginResponseData>()
    val loginDetails: MutableLiveData<LoginResponseData> = _loginDetails

}