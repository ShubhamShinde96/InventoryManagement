package com.im.dairyinventorymanagement.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.im.dairyinventorymanagement.data.model.request.LoginRequestData
import com.im.dairyinventorymanagement.data.model.response.LoginResponseData
import com.im.dairyinventorymanagement.domain.usecase.UserManagementUseCase
import com.shubham.newsapiclientproject.data.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HostViewModel(
    private val application: Application,
    private val userManagementUseCase: UserManagementUseCase
): AndroidViewModel(application) {

    private val _loginDetails = MutableLiveData<Resource<List<LoginResponseData>>>()
    val loginDetails: LiveData<Resource<List<LoginResponseData>>> = _loginDetails

    fun loginUser(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _loginDetails.postValue(Resource.Loading())

        try {
            if (isNetworkAvailable(application)) {
                val loginResult = userManagementUseCase.loginUser(LoginRequestData(username, password))
                _loginDetails.postValue(loginResult)
            } else {
                _loginDetails.postValue(Resource.Error("Internet Unavailable"))
            }
        } catch (e: Exception) {
            _loginDetails.postValue(Resource.Error(e.message.toString()))
        }

    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}
