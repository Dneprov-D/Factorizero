package com.hfad.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.People

sealed class BottomNavItem(
    val titleResId: Int,
    val icon: ImageVector
) {
    object EmployeeTab: BottomNavItem(
        titleResId = R.string.navigation_item_employee,
        icon = Icons.Outlined.People
    )

    object TasksTab: BottomNavItem(
        titleResId = R.string.navigation_item_tasks,
        icon = Icons.Outlined.LocalOffer
    )
}
