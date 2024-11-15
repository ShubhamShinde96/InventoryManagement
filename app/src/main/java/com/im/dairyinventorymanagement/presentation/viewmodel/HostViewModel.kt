package com.im.dairyinventorymanagement.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.im.dairyinventorymanagement.data.model.response.LoginResponseData
import com.im.dairyinventorymanagement.domain.usecase.UserManagementUseCase
import com.shubham.newsapiclientproject.data.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HostViewModel(
    private val application: Application,
    private val userManagementUseCase: UserManagementUseCase
): AndroidViewModel(application) {

    private val _loginDetails = MutableLiveData<Resource<LoginResponseData>>()
    val loginDetails: LiveData<Resource<LoginResponseData>> = _loginDetails

    fun loginUser(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _loginDetails.postValue(Resource.Loading())
    }

}