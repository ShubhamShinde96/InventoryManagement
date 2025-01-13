package com.im.dairyinventorymanagement.presentation.screens

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.im.dairyinventorymanagement.HostActivity
import com.im.dairyinventorymanagement.LoginActivity
import com.im.dairyinventorymanagement.R
import com.im.dairyinventorymanagement.data.api.ApiService
import com.im.dairyinventorymanagement.data.model.request.ModulesRequestData
import com.im.dairyinventorymanagement.data.model.request.OrderItems
import com.im.dairyinventorymanagement.data.model.request.SaveOrderRequestData
import com.im.dairyinventorymanagement.data.model.response.LoginResponseData
import com.im.dairyinventorymanagement.data.model.response.Module
import com.im.dairyinventorymanagement.data.model.response.Product
import com.im.dairyinventorymanagement.databinding.FragmentOrderPlacementBinding
import com.im.dairyinventorymanagement.presentation.adapter.ProductListAdapter
import com.im.dairyinventorymanagement.presentation.utils.GridSpacingItemDecoration
import com.im.dairyinventorymanagement.presentation.viewmodel.OrderViewModel
import com.im.dairyinventorymanagement.presentation.viewmodel.OrderViewModelFactory
import com.im.dairyinventorymanagement.utils.EMPTY_STRING
import com.im.dairyinventorymanagement.utils.SharedPreferencesHandler
import com.im.dairyinventorymanagement.utils.SharedPreferencesHandler.Companion.LOGIN_DETAILS
import com.saadahmedev.popupdialog.PopupDialog
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener
import com.shubham.newsapiclientproject.data.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class OrderPlacementFragment : Fragment() {

    private lateinit var binding: FragmentOrderPlacementBinding
    lateinit var sharedPrefsHandler: SharedPreferencesHandler
    private lateinit var dialog: PopupDialog
    private var modulesListAdapter: ProductListAdapter? = null

    @Inject
    lateinit var factory: OrderViewModelFactory

    lateinit var viewModel: OrderViewModel

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_placement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentOrderPlacementBinding.bind(view)

        sharedPrefsHandler = (activity as HostActivity).sharedPrefsHandler
        dialog = PopupDialog.getInstance(context)

        setupClickListeners()
        fetchItemList()
        observeList()
    }

    private fun setupClickListeners() {
        binding.backImgBtn.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.logoutImgBtn.setOnClickListener {
            showLogoutDialog()
        }

        binding.verifyOrderButton.setOnClickListener {
            (Gson().fromJson(
                sharedPrefsHandler.getString(LOGIN_DETAILS, EMPTY_STRING),
                LoginResponseData::class.java
            ))?.let {
                hideErrorLayout()
                modulesListAdapter?.differ?.currentList?.let { list ->
                    val productList = list.toList().map {
                        OrderItems(
                            id = it.id, orderQuantity = it.orderQty.toString()
                        )
                    }
                    viewModel.saveOrder(
                        SaveOrderRequestData(
                            it.data.token, it.data.user.id, productList
                        )
                    )
                }
            }
        }
    }

    private fun observeList(isRetryAttempt: Boolean = false) {
        viewModel.saveOrder.distinctUntilChanged().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    binding.dimmingOverlay.visibility = View.GONE
                    showErrorLayout()
                }

                is Resource.Loading -> {
                    binding.dimmingOverlay.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.dimmingOverlay.visibility = View.GONE
                    if (it.data?.first()?.status?.lowercase() == "success") {

                    } else {
                        showErrorLayout()
                    }
                }
            }
        }
    }

    private fun fetchItemList() {
        (Gson().fromJson(sharedPrefsHandler.getString(LOGIN_DETAILS, EMPTY_STRING), LoginResponseData::class.java))?.let {
            hideErrorLayout()
            viewLifecycleOwner.lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val result = responseToResource(apiService.getItemList(ModulesRequestData(
                        it.data.token,
                        it.data.user.id
                    )))

                    withContext(Dispatchers.Main) {
                        when (result) {
                            is Resource.Error -> {
                                binding.dimmingOverlay.visibility = View.GONE
                                showErrorLayout()
                            }

                            is Resource.Loading -> {
                                binding.dimmingOverlay.visibility = View.VISIBLE
                            }

                            is Resource.Success -> {
                                binding.dimmingOverlay.visibility = View.GONE
                                if (result.data?.first()?.status?.lowercase() == "success") {
                                    initializeAdapter(mapItemListData(result.data.first().data))
                                } else {
                                    showErrorLayout()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun mapItemListData(list: List<Module>): List<Product> {
        val productList = mutableListOf<Product>()

        list.map {
            productList.add(Product(
                id = it.id,
                title = it.title,
                availableqty = it.availableqty ?: 0
            ))
        }

        return productList
    }

    private fun initializeAdapter(list: List<Product>, isRetryAttempt: Boolean = false) {
        if(isRetryAttempt) {
            modulesListAdapter?.differ?.submitList(list)
            return
        }

        modulesListAdapter = ProductListAdapter()
        modulesListAdapter?.differ?.submitList(list)


        binding.recyclerView.apply {
            adapter = modulesListAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(GridSpacingItemDecoration(23, 23))
        }
    }

    private fun showErrorLayout() {
        binding.errorLayout.apply {
            failedToLoadDataLayout.visibility = View.VISIBLE
            errorTv.text = getString(R.string.something_went_wrong)
            retryButton.setOnClickListener {
                fetchItemList()
            }
        }
    }

    private fun hideErrorLayout() {
        binding.errorLayout.apply {
            if (isVisible) {
                failedToLoadDataLayout.visibility = View.GONE
                errorTv.text = EMPTY_STRING
            }
        }
    }

    private fun showLogoutDialog() {
        PopupDialog.getInstance(context)
            .standardDialogBuilder()
            .createAlertDialog()
            .setHeading(getString(R.string.logout))
            .setDescription(getString(R.string.logout_question))
            .build(object : StandardDialogActionListener {
                override fun onNegativeButtonClicked(dialog: Dialog?) {
                    dialog?.dismiss()
                }

                override fun onPositiveButtonClicked(dialog: Dialog?) {
                    activity?.let {
                        SharedPreferencesHandler(it).clearSharedPreferences()
                        startActivity(Intent(activity, LoginActivity::class.java))
                    }
                }
            })
            .show()
    }

    inline fun <reified T> responseToResource(response: Response<T>): Resource<T> {
        return if (response.isSuccessful) {
            response.body()?.let { Resource.Success(it) } ?: Resource.Error("Response body is null")
        } else {
            try {
                val errorResponse = Gson().fromJson(response.errorBody()?.string(), T::class.java)
                Resource.Error(response.message(), errorResponse)
            } catch (e: Exception) {
                Resource.Error(response.message())
            }
        }
    }
}
