package com.example.thindie.kodetrainee.presentation.screens.concreteemployee

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.rememberAsyncImagePainter
import com.example.thindie.kodetrainee.R
import com.example.thindie.kodetrainee.presentation.entity.EmployeeUiModel
import com.example.thindie.kodetrainee.presentation.navigation.concreteRoute
import com.example.thindie.kodetrainee.presentation.theme.KodeTraineeTypography
import com.example.thindie.kodetrainee.presentation.theme.backGroundColor
import okhttp3.internal.format


fun NavGraphBuilder.concreteScreen(
    onAction: (String) -> Unit,
    onClickBack: () -> Unit,
    onError: () -> Unit,
) {
    composable(concreteRoute.route) {
        DetailsScreen(onError = onError, onClickBack = onClickBack, onAction = onAction)
    }
}

@Composable
fun DetailsScreen(
    viewModel: ConcreteEmployeeViewModel = hiltViewModel(),
    onError: () -> Unit,
    onClickBack: () -> Unit,
    onAction: (String) -> Unit,
) {
    viewModel.onClickEmployee()

    val screenState = viewModel.concreteScreen
        .collectAsStateWithLifecycle(minActiveState = Lifecycle.State.RESUMED)

    Column(Modifier.fillMaxSize()) {
        HeaderSection(
            modifier = Modifier
                .height(274.dp),
            employee = screenState.value.employee,
            onClickBack = onClickBack
        )
        InformationSection(modifier = Modifier, screenState.value.employee, onAction)
    }
}

@Composable
fun InformationSection(
    modifier: Modifier,
    employee: EmployeeUiModel?,
    onAction: (String) -> Unit,
) {
    LazyColumn() {
        item {
            InformationSectionUnit(
                information = employee?.ageHub?.formattedBirthDay.orEmpty(),
                icon = R.drawable.icon_favorite,
                hasExtra = true,
                extraInformation = employee?.ageHub?.age.orEmpty(),
            )
        }
        item {
            InformationSectionUnit(
                information = employee?.phone.orEmpty(),
                icon = R.drawable.icon_phone_alt,
                hasExtra = false,
                onAction = onAction
            )
        }
    }


}

@Composable
fun HeaderSection(
    modifier: Modifier,
    employee: EmployeeUiModel?,
    onClickBack: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(backGroundColor)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            Image(
                modifier = Modifier
                    .size(42.dp)
                    .padding(start = 12.dp, top = 14.dp)
                    .clickable { onClickBack() },
                painter = painterResource(id = R.drawable.icon_arrow_back),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(104.dp)
                    .clip(CircleShape),
                painter = rememberAsyncImagePainter(
                    model = employee?.avatarUrl,

                    ),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier.padding(top = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    4.dp, Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    modifier = Modifier.offset(x = 0.dp, y = 3.dp), text = format(
                        NAME_PATTERN, employee?.firstName.orEmpty(), employee?.lastName.orEmpty()
                    ), style = KodeTraineeTypography.headlineMedium
                )

                Text(
                    text = employee?.userTag.orEmpty(), style = KodeTraineeTypography.labelLarge
                )
            }

            Text(
                modifier = Modifier.padding(bottom = 24.dp, top = 12.dp),
                text = employee?.position.orEmpty(),
                style = KodeTraineeTypography.labelSmall
            )
        }
    }
}

private const val NAME_PATTERN = "%s %s"


@Composable
fun InformationSectionUnit(
    information: String,
    @DrawableRes icon: Int,
    extraInformation: String = "null",
    hasExtra: Boolean,
    onAction: (String) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 6.dp)
            .height(60.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(end = 12.dp),
            painter = painterResource(id = icon),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.clickable {
                if (hasExtra == false) {
                    onAction(information)
                }
            },
            text = information, style = KodeTraineeTypography.titleLarge
        )
        Spacer(modifier = Modifier.weight(1f, fill = true))
        if (hasExtra) {
            Text(
                text = extraInformation,
                style = KodeTraineeTypography.labelLarge
            )
        }
    }
}