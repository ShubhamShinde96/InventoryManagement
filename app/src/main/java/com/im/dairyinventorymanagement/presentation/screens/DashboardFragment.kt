package com.im.dairyinventorymanagement.presentation.screens

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.im.dairyinventorymanagement.HostActivity
import com.im.dairyinventorymanagement.R
import com.im.dairyinventorymanagement.data.model.response.LoginResponseData
import com.im.dairyinventorymanagement.data.model.response.Module
import com.im.dairyinventorymanagement.data.repository.ModulesData
import com.im.dairyinventorymanagement.databinding.FragmentDashboardBinding
import com.im.dairyinventorymanagement.presentation.adapter.ModulesListAdapter
import com.im.dairyinventorymanagement.presentation.utils.GridSpacingItemDecoration
import com.im.dairyinventorymanagement.presentation.viewmodel.HostViewModel
import com.im.dairyinventorymanagement.utils.SharedPreferencesHandler
import com.im.dairyinventorymanagement.utils.SharedPreferencesHandler.Companion.LOGIN_DETAILS
import com.saadahmedev.popupdialog.PopupDialog
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener
import com.shubham.newsapiclientproject.data.util.Resource

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var modulesListAdapter: ModulesListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentDashboardBinding

    lateinit var sharedPrefsHandler: SharedPreferencesHandler

    private val viewModel: HostViewModel by activityViewModels()

    private lateinit var dialog: PopupDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDashboardBinding.bind(view)

        sharedPrefsHandler = (activity as HostActivity).sharedPrefsHandler
        dialog = PopupDialog.getInstance(context)

        setupClickListeners()
        fetchModulesList()
    }

    private fun setupClickListeners() {
        binding.backImgBtn.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.logoutImgBtn.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun fetchModulesList() {
        (Gson().fromJson(sharedPrefsHandler.getString(LOGIN_DETAILS, ""), LoginResponseData::class.java))?.let {
            viewModel.getModulesList(it.data.token, it.data.user.id)
        }

        viewModel.modulesList.distinctUntilChanged().observe(viewLifecycleOwner) {
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
                    if (it.data?.first()?.status?.lowercase() != "success") {
                        showErrorLayout()
                    } else {
                        it.data.first().data
                    }
                }
            }
        }
    }

    private fun setupAdapter(list: List<Module>) {
        modulesListAdapter = ModulesListAdapter()

        modulesListAdapter?.apply {
            differ.submitList(list)
            setItemClickCallback { moduleData ->
                // findNavController().navigate(moduleData.navigationActionRouteName)
                ModulesData.getAction(moduleData.navigationActionRouteName)?.let { findNavController().navigate(it) } ?: run {
                    PopupDialog.getInstance(context)
                        .statusDialogBuilder()
                        .createWarningDialog()
                        .setHeading("Ops!")
                        .setDescription("Seems like this feature is not available. Please contact your service provider.")
                        .build(Dialog::dismiss)
                        .show()
                }
            }
        }

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
                fetchModulesList()
            }
        }
    }

    fun showLogoutDialog() {
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
                        it.finish()
                    }
                }
            })
            .show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}