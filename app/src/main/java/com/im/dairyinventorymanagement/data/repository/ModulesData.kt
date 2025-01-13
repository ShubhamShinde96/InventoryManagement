package com.im.dairyinventorymanagement.data.repository

import com.im.dairyinventorymanagement.R

object ModulesData {

    private val modulesList = listOf(
        Module(R.id.action_dashboardFragment_to_salesFragment, "SwamiDairy/SubModules"),
        Module(R.id.action_dashboardFragment_to_salesFragment, "SwamiDairy/MenuList"),
        Module(R.id.action_dashboardFragment_to_orderPlacementFragment, "SwamiDairy/SubModuleList/Order Form"),
        Module(R.id.action_subModulesFragment_to_orderPlacementFragment, "SwamiDairy/MenuList/Order Form"),
        Module(R.id.action_menuListFragment_to_orderPlacementFragment, "SwamiDairy/Order Form"),
        Module(R.id.action_dashboardFragment_to_salesFragment, "SwamiDairy/gdfgd"),
        Module(R.id.action_dashboardFragment_to_salesFragment, "SwamiDairy/fdgdfg"),
        Module(R.id.action_dashboardFragment_to_salesFragment, "SwamiDairy/zcvc"),
        Module(R.id.action_dashboardFragment_to_salesFragment, "SwamiDairy/bfbb"),
        Module(R.id.action_dashboardFragment_to_salesFragment, "SwamiDairy/fdbdbffb"),
    )

    fun getAction(actionName: String) = modulesList.find { it.actionName == actionName }?.action
}

data class Module(
    val action: Int,
    val actionName: String
)