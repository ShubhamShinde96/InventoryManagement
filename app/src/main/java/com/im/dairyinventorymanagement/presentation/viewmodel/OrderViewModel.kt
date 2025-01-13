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
import com.im.dairyinventorymanagement.data.model.request.SaveOrderRequestData
import com.im.dairyinventorymanagement.data.model.response.SaveOrderResponseData
import com.im.dairyinventorymanagement.domain.usecase.OrderManagementUseCase
import com.shubham.newsapiclientproject.data.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(
    private val application: Application, private val orderManagementUseCase: OrderManagementUseCase
) : AndroidViewModel(application) {

    private val _saveOrder = MutableLiveData<Resource<List<SaveOrderResponseData>>>()
    val saveOrder: LiveData<Resource<List<SaveOrderResponseData>>> = _saveOrder

    fun saveOrder(saveOrderRequestData: SaveOrderRequestData) =
        viewModelScope.launch(Dispatchers.IO) {
            _saveOrder.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(application)) {
                    val saveOrderResult = orderManagementUseCase.saveOrder(saveOrderRequestData)
                    _saveOrder.postValue(saveOrderResult)
                } else {
                    _saveOrder.postValue(Resource.Error("Internet Unavailable"))
                }
            } catch (e: Exception) {
                _saveOrder.postValue(Resource.Error(e.message.toString()))
            }
        }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
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
