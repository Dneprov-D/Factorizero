package com.hfad.factorizero.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.PermContactCalendar
import androidx.compose.ui.graphics.vector.ImageVector
import com.hfad.navigation.R
import com.hfad.navigation.Screen
import kotlin.reflect.KClass

enum class EmployeeBottomNavigationDestination(
    val icon: ImageVector,
    val titleResId: Int,
    val route: KClass<*>
) {
    EMPLOYEE_PROFILE_TAB(
        icon = Icons.Outlined.PermContactCalendar,
        titleResId = R.string.navigation_item_profile,
        route = Screen.EmployeeProfileScreen::class
    ),

    EMPLOYEE_MAIN_TAB(
        icon = Icons.Outlined.LocalOffer,
        titleResId = R.string.navigation_item_tasks,
        route = Screen.EmployeeMainTabScreen::class
    )
}