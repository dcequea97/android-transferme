package com.cequea.transferme.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cequea.transferme.R
import com.cequea.transferme.ui.components.TransferMeSimpleHeader
import com.cequea.transferme.ui.theme.TransferMeTheme
import com.cequea.transferme.utils.Constants
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileRoot(
    navController: NavController,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.onNavigate.collectLatest{ screen ->
            navController.navigate(screen)
        }
    }

    ProfileScreen(
        state = state,
        onAction = viewModel::onAction,
        navController = navController
    )
}

@Composable
private fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Constants.HORIZONTAL_PADDING.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TransferMeSimpleHeader(
            modifier = Modifier.padding(vertical = Constants.HORIZONTAL_PADDING.dp),
            title = "Profile Settings",
            onBackClick = { navController.popBackStack() }
        )

        Text(
            text = "Your Profile Information",
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(Modifier.height(24.dp))
        Box {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User",
                modifier = Modifier
                    .padding(start = Constants.HORIZONTAL_PADDING.dp)
                    .size(140.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray.copy(0.5f), CircleShape)
                    .padding(5.dp),
                tint = Color.Gray
            )
            IconButton(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0XFF5164BF))
                    .padding(5.dp)
                    .align(Alignment.BottomEnd),
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(R.drawable.tag_square),
                    contentDescription = "User",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    tint = Color.White
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        SettingsItems(onAction)
    }
}

enum class SettingsSectionType(val title: String) {
    PERSONAL_INFORMATION("Personal Information"),
    SECURITY("Security Settings")
}

enum class SettingsItemType(
    val sectionType: SettingsSectionType,
    val title: String,
    val data: String?,
    val icon: Int?
) {
    ACCOUNT_NUMBER(
        sectionType = SettingsSectionType.PERSONAL_INFORMATION,
        title = "Account Number",
        data = "01023049543",
        icon = null
    ),
    USERNAME(
        sectionType = SettingsSectionType.PERSONAL_INFORMATION,
        title = "Username",
        data = "Jon Doe",
        icon = null
    ),
    EMAIL(
        sectionType = SettingsSectionType.PERSONAL_INFORMATION,
        title = "Email",
        data = "jon.doe@gmail.com",
        icon = null
    ),
    PHONE_NUMBER(
        sectionType = SettingsSectionType.PERSONAL_INFORMATION,
        title = "Phone Number",
        data = "+1 234 567 890",
        icon = null
    ),
    ADDRESS(
        sectionType = SettingsSectionType.PERSONAL_INFORMATION,
        title = "Address",
        data = "123 Main St, City, Country",
        icon = null
    ),
    CHANGE_PIN(
        sectionType = SettingsSectionType.SECURITY,
        title = "Change PIN",
        data = null,
        icon = R.drawable.chevron_rigth
    ),
    CHANGE_PASSWORD(
        sectionType = SettingsSectionType.SECURITY,
        title = "Change Password",
        data = null,
        icon = R.drawable.chevron_rigth
    ),
    FINGERPRINT(
        sectionType = SettingsSectionType.SECURITY,
        title = "Fingerprint",
        data = null,
        icon = R.drawable.togle
    )
}

@Composable
private fun SettingsItems(
    onAction: (ProfileAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SettingsSectionType.entries.forEach { sectionType ->
            Text(
                modifier = Modifier.align(Alignment.Start).padding(top = 12.dp),
                text = sectionType.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0XFF5164BF)
            )

            SettingsItemType.entries.filter { it.sectionType == sectionType }.forEach { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(color = Color(0XFFF4F4F4), shape = MaterialTheme.shapes.medium)
                        .padding(horizontal = 12.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.title,
                            color = Color(0XFF5164BF)
                        )

                        Box {
                            item.data?.let { text ->
                                Text(
                                    text = text,
                                    color = Color.Gray
                                )
                            }

                            item.icon?.let { icon ->
                                IconButton(
//                                    modifier = Modifier.size(24.dp),
                                    onClick = {
                                        when (item) {
                                            SettingsItemType.CHANGE_PIN -> onAction(ProfileAction.ChangePin)
                                            SettingsItemType.CHANGE_PASSWORD -> onAction(ProfileAction.ChangePassword)
                                            SettingsItemType.FINGERPRINT -> onAction(ProfileAction.EnableFingerprint)
                                            else -> {}
                                        }
                                    }
                                ) {
                                    val modifier = if (item == SettingsItemType.FINGERPRINT) {
                                        Modifier.size(40.dp)
                                    } else {
                                        Modifier.size(20.dp)
                                    }
                                    Icon(
                                        modifier = modifier,
                                        painter = painterResource(icon),
                                        contentDescription = item.title,
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, heightDp = 1000)
private fun ProfileScreenPreview() {
    TransferMeTheme {
        ProfileScreen(
            state = ProfileState(),
            onAction = {},
            navController = rememberNavController()
        )
    }
}