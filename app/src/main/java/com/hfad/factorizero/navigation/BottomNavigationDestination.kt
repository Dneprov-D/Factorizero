package com.hfad.factorizero.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.People
import androidx.compose.ui.graphics.vector.ImageVector
import com.hfad.navigation.R
import com.hfad.navigation.Screen
import kotlin.reflect.KClass

enum class BottomNavigationDestination(
    val icon: ImageVector,
    val titleResId: Int,
    val route: KClass<*>
) {
    EMPLOYEE_TAB(
        icon = Icons.Outlined.People,
        titleResId = R.string.navigation_item_employee,
        route = Screen.EmployeeTabScreen::class
    ),

    TASKS_TAB(
        icon = Icons.Outlined.LocalOffer,
        titleResId = R.string.navigation_item_tasks,
        route = Screen.TasksTabScreen::class
    )
}