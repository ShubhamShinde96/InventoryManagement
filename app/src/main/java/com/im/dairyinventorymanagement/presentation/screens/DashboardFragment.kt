package com.im.dairyinventorymanagement.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.im.dairyinventorymanagement.R
import com.im.dairyinventorymanagement.data.model.ModuleData
import com.im.dairyinventorymanagement.databinding.FragmentDashboardBinding
import com.im.dairyinventorymanagement.presentation.adapter.ModulesListAdapter
import com.im.dairyinventorymanagement.presentation.utils.GridSpacingItemDecoration

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDashboardBinding.bind(view)

        setupClickListeners()
        setupAdapter()
    }

    private fun setupClickListeners() {
        binding.backImgBtn.setOnClickListener {
            activity?.finish()
        }

        binding.logoutImgBtn.setOnClickListener {
            activity?.finish()
        }
    }

    private fun setupAdapter() {
        modulesListAdapter = ModulesListAdapter() {

        }

        val list = listOf(
            ModuleData(
                id = 1,
                title = "Admin & Legal",
                description = "Manage your admin & legal operations",
                image = R.drawable.ic_admin,
                backgroundColor = R.color.item_yellow
            ),
            ModuleData(
                id = 1,
                title = "Master",
                description = "Maintain your master",
                image = R.drawable.ic_master,
                backgroundColor = R.color.ripple
            ),
            ModuleData(
                id = 1,
                title = "Sales & Distribution",
                description = "Manage Sales & Distributions across your business",
                image = R.drawable.ic_sales_and_distribution,
                backgroundColor = R.color.blue
            ),
            ModuleData(
                id = 1,
                title = "Purchase Procurement",
                description = "Manage Purchase & Procurements.",
                image = R.drawable.ic_purchase_procurement,
                backgroundColor = R.color.green
            ),
            ModuleData(
                id = 1,
                title = "Production & Planning",
                description = "Maintain your Production and Planning",
                image = R.drawable.ic_production_and_planning,
                backgroundColor = R.color.purple
            ),
            ModuleData(
                id = 1,
                title = "Quality Management",
                description = "Check your Quality Management practices",
                image = R.drawable.ic_quality,
                backgroundColor = R.color.color1
            ),
            ModuleData(
                id = 1,
                title = "Material Management",
                description = "Manage your material",
                image = R.drawable.ic_material_management,
                backgroundColor = R.color.orange_shade
            ),
            ModuleData(
                id = 1,
                title = "Maintenance",
                description = "Manage Maintenance",
                image = R.drawable.ic_maintenance,
                backgroundColor = R.color.blue2
            ),
            ModuleData(
                id = 1,
                title = "HR Management",
                description = "Manage your Human Resources",
                image = R.drawable.ic_hr_management,
                backgroundColor = R.color.primary
            )
        )

        modulesListAdapter?.let {
            it.differ.submitList(list)
        }

        binding.recyclerView.apply {
           adapter = modulesListAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(GridSpacingItemDecoration(23, 23))
        }
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