package com.im.dairyinventorymanagement.presentation.screens

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.im.dairyinventorymanagement.databinding.FragmentMenuListBinding
import com.im.dairyinventorymanagement.presentation.adapter.ModulesListAdapter
import com.im.dairyinventorymanagement.presentation.utils.GridSpacingItemDecoration
import com.im.dairyinventorymanagement.presentation.viewmodel.HostViewModel
import com.im.dairyinventorymanagement.utils.EMPTY_STRING
import com.im.dairyinventorymanagement.utils.SharedPreferencesHandler
import com.im.dairyinventorymanagement.utils.SharedPreferencesHandler.Companion.LOGIN_DETAILS
import com.saadahmedev.popupdialog.PopupDialog
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener
import com.shubham.newsapiclientproject.data.util.Resource

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MODULE_ID = "moduleId"
private const val SUB_MODULE_ID = "subModuleId"
private const val MENU_LIST_SCREEN_TITLE = "screenTitle"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var subModulesListAdapter: ModulesListAdapter? = null

    private lateinit var binding: FragmentMenuListBinding

    lateinit var sharedPrefsHandler: SharedPreferencesHandler

    private val viewModel: HostViewModel by activityViewModels()

    private lateinit var dialog: PopupDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(MODULE_ID)
            param2 = it.getString(SUB_MODULE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMenuListBinding.bind(view)

        sharedPrefsHandler = (activity as HostActivity).sharedPrefsHandler
        dialog = PopupDialog.getInstance(context)

        initViews()
        setupClickListeners()
        fetchMenuList()
    }

    private fun initViews() {
        binding.menuListToolbarTitle.text = arguments?.getString(MENU_LIST_SCREEN_TITLE).orEmpty()
    }

    private fun setupClickListeners() {
        binding.backImgBtn.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.logoutImgBtn.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun fetchMenuList() {
        (Gson().fromJson(sharedPrefsHandler.getString(LOGIN_DETAILS, EMPTY_STRING), LoginResponseData::class.java))?.let {
            arguments?.apply {
                hideErrorLayout()
                viewModel.getMenuList(it.data.token, it.data.user.id, getString(
                    MODULE_ID).orEmpty(), getString(SUB_MODULE_ID).orEmpty())
            }
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
                    if (it.data?.first()?.status?.lowercase() == "success") {
                        initializeAdapter(it.data.first().data)
                    } else {
                        showErrorLayout()
                    }
                }
            }
        }
    }

    private fun initializeAdapter(list: List<Module>) {
        subModulesListAdapter = ModulesListAdapter()

        subModulesListAdapter?.apply {
            differ.submitList(list)
            setItemClickCallback { moduleData ->
                // findNavController().navigate(moduleData.navigationActionRouteName)
                ModulesData.getAction(moduleData.navigationActionRouteName)?.let { findNavController().navigate(it) } ?: run {
                    PopupDialog.getInstance(context)
                        .statusDialogBuilder()
                        .createWarningDialog()
                        .setHeading(resources.getString(R.string.ops))
                        .setDescription(resources.getString(R.string.feature_not_available_at_this_moment))
                        .build(Dialog::dismiss)
                        .show()
                }
            }
        }

        binding.recyclerView.apply {
            adapter = subModulesListAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(GridSpacingItemDecoration(23, 23))
        }
    }

    private fun showErrorLayout() {
        binding.errorLayout.apply {
            failedToLoadDataLayout.visibility = View.VISIBLE
            errorTv.text = getString(R.string.something_went_wrong)
            retryButton.setOnClickListener {
                fetchMenuList()
            }
        }
    }

    private fun hideErrorLayout() {
        binding.errorLayout.apply {
            if (isVisible) {
                failedToLoadDataLayout.visibility = View.GONE
                errorTv.text = EMPTY_STRING
                retryButton.setOnClickListener {
                    fetchMenuList()
                }
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
         * @return A new instance of fragment MenuListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuListFragment().apply {
                arguments = Bundle().apply {
                    putString(MODULE_ID, param1)
                    putString(SUB_MODULE_ID, param2)
                }
            }
    }
}