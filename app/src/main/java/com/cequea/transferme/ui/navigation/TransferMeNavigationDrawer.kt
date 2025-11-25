package com.cequea.transferme.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cequea.transferme.R
import com.cequea.transferme.ui.components.TransferMeButton
import com.cequea.transferme.ui.theme.TransferMeTheme
import com.cequea.transferme.utils.Constants
import kotlinx.coroutines.launch

@Composable
fun TransferMeNavigationDrawer(
    navController: NavController,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val enableGestures: Boolean = remember(currentRoute) {
        AppScreens.enableDrawerGestures(currentRoute ?: "")
    }

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = enableGestures,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(32.dp))
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User",
                    modifier = Modifier
                        .padding(start = Constants.HORIZONTAL_PADDING.dp)
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray.copy(0.5f), CircleShape)
                        .padding(5.dp),
                    tint = Color.Gray
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Jon Doe",
                    modifier = Modifier.padding(horizontal = Constants.HORIZONTAL_PADDING.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "jon.doe@gmail.com",
                    modifier = Modifier.padding(horizontal = Constants.HORIZONTAL_PADDING.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 32.dp))
                NavigationDrawerItem(
                    modifier = Modifier.padding(horizontal = Constants.HORIZONTAL_PADDING.dp),
                    label = {
                        Text(
                            text = "My Wallet",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = false,
                    onClick = {
                        navController.navigate(AppScreens.CardDetails)
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.wallet),
                            contentDescription = "Drawer Item Icon",
                            tint = Color(0XFF8EDFEB)
                        )
                    }
                )
                NavigationDrawerItem(
                    modifier = Modifier.padding(horizontal = Constants.HORIZONTAL_PADDING.dp),
                    label = {
                        Text(
                            text = "Profile",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = false,
                    onClick = {
                        navController.navigate(AppScreens.ProfileScreen)
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.user_2),
                            contentDescription = "Drawer Item Icon",
                            tint = Color(0XFF8EDFEB)
                        )
                    }
                )
                NavigationDrawerItem(
                    modifier = Modifier.padding(horizontal = Constants.HORIZONTAL_PADDING.dp),
                    label = {
                        Text(
                            text = "Statistics",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = false,
                    onClick = {
                        navController.navigate(AppScreens.StaticsScreen)
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.chart),
                            contentDescription = "Drawer Item Icon",
                            tint = Color(0XFF8EDFEB)
                        )
                    }
                )
                NavigationDrawerItem(
                    modifier = Modifier.padding(horizontal = Constants.HORIZONTAL_PADDING.dp),
                    label = {
                        Text(
                            text = "Transfer",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = false,
                    onClick = {
                        navController.navigate(AppScreens.TransferScreen)
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.transfer),
                            contentDescription = "Drawer Item Icon",
                            tint = Color(0XFF8EDFEB)
                        )
                    }
                )
                NavigationDrawerItem(
                    modifier = Modifier.padding(horizontal = Constants.HORIZONTAL_PADDING.dp),
                    label = {
                        Text(
                            text = "Settings",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = false,
                    onClick = {
                        navController.navigate(AppScreens.ProfileScreen)
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.settings),
                            contentDescription = "Drawer Item Icon",
                            tint = Color(0XFF8EDFEB)
                        )
                    }
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                        .weight(1f),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    TransferMeButton(
                        text = "Log Out",
                        onClick = {
                            navController.navigate(AppScreens.LoginScreen)
                            scope.launch { drawerState.close() }
                        }
                    )
                }
            }
        }
    ) {
        content()
    }
}

@Composable
@Preview(showBackground = true)
private fun TransferMeNavigationDrawerPreview() {
    TransferMeTheme {
        TransferMeNavigationDrawer(
            content = { /* Empty content for preview */ },
            navController = rememberNavController(),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        )
    }
}