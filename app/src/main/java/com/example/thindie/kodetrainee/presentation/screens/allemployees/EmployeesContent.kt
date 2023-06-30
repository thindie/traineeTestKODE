package com.example.thindie.kodetrainee.presentation.screens.allemployees

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.rememberAsyncImagePainter
import com.example.thindie.kodetrainee.presentation.entity.EmployeeUiModel
import com.example.thindie.kodetrainee.presentation.navigation.mainRoute
import com.example.thindie.kodetrainee.presentation.screens.NothingFoundContent
import com.example.thindie.kodetrainee.presentation.theme.KodeTraineeTypography
import com.example.thindie.kodetrainee.presentation.theme.KodetraineeTheme
import com.example.thindie.kodetrainee.presentation.theme.lightContentDefaultSecondary
import com.example.thindie.kodetrainee.presentation.theme.lightTextPrimary
import com.example.thindie.kodetrainee.presentation.uiunits.SearchAble
import com.example.thindie.kodetrainee.presentation.uiunits.TabsState
import com.example.thindie.kodetrainee.presentation.uiunits.bottomSheet.EmployeeBottomSheet
import com.example.thindie.kodetrainee.presentation.uiunits.bottomSheet.EmployeeBottomSheetState
import com.example.thindie.kodetrainee.presentation.uiunits.bottomSheet.rememberEmployeeBottomSheetState
import com.example.thindie.kodetrainee.presentation.uiunits.navigationtabssection.NavigationBlock
import com.example.thindie.kodetrainee.presentation.uiunits.navigationtabssection.SearchBarState
import com.example.thindie.kodetrainee.presentation.uiunits.navigationtabssection.rememberSearchBarState
import com.example.thindie.kodetrainee.presentation.uiunits.rememberTabsBarState
import com.example.thindie.kodetrainee.presentation.uiunits.shimmeringskeleton.ShimmerState
import com.example.thindie.kodetrainee.presentation.uiunits.shimmeringskeleton.ShimmeringList
import com.example.thindie.kodetrainee.presentation.uiunits.shimmeringskeleton.rememberShimmerStateScreenDecoration
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.time.LocalDate
import okhttp3.internal.format


fun NavGraphBuilder.mainScreen(
    onError: () -> Unit,
    onClickConcrete: () -> Unit,
) {
    composable(mainRoute.route) {
        EmployeeMainScreen(onError = onError, onClickConcrete = onClickConcrete)
    }
}

@Suppress("LongParameterList")
@Composable
fun EmployeeMainScreen(
    viewModel: EmployeesViewModel = hiltViewModel(),
    onError: () -> Unit,
    onClickConcrete: () -> Unit,
    shimmerState: ShimmerState = rememberShimmerStateScreenDecoration(),
    searchBarState: SearchBarState = rememberSearchBarState(
        currentText = viewModel.currentText.value,
        isSearchActive = viewModel.isSearchActive.value,
        isTrailingIconColored = !viewModel.isFilteringByAlphabet.value
    ),
    tabsState: TabsState = rememberTabsBarState(currentTab = viewModel.currentTab),
    bottomSheetState: EmployeeBottomSheetState = rememberEmployeeBottomSheetState(
        isAlphabetFiltering = viewModel.isFilteringByAlphabet.value,
    ),
) {
    with(viewModel) {
        onStart()
        onSearch(searchBarState.textFieldState)
        onBottomSheetChoseSort(
            bottomSheetState.selected,
            searchBarState::switchSpecialStateOn,
            searchBarState::switchSpecialStateOff
        )
        onClickTab(tabsState.onClick.value)
        employeesScreen
    }

    val screen =
        viewModel
            .employeesScreen
            .collectAsStateWithLifecycle(minActiveState = Lifecycle.State.RESUMED)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        NavigationBlock(
            searchBarState = searchBarState,
            tabsState = tabsState,
            activateBottomSheet = { bottomSheetState.showList() }
        )

        SwipeRefresh(
            state = rememberSwipeRefreshState(
                isRefreshing = screen.value.isLoading && !screen.value.isHotting
            ), onRefresh = viewModel::onRefreshCurrentTab
        ) {
            if (!screen.value.isLoading) {
                if (screen.value.employees == null) {
                    onError()
                } else {
                    if (screen.value.isNothingFound) {
                        NothingFoundContent()
                    } else {

                        val list = screen
                            .value
                            .employees
                            .orEmpty()
                            .sortedWith(viewModel.currentFilter.value)

                        if (viewModel.isFilteringByAlphabet.value) {
                            EmployeesContent(
                                employee = list
                            ) { selectedEmployee ->
                                viewModel.onClickConcrete(selectedEmployee.getReified())
                                onClickConcrete()
                            }
                        } else {
                            EmployeesContentWithBirthDay(
                                employee = list
                            ) { selectedEmployee ->
                                viewModel.onClickConcrete(selectedEmployee.getReified())
                                onClickConcrete()
                            }
                        }

                    }
                }
            } else {
                ShimmeringList(shimmerState.brush)
            }
        }
        EmployeeBottomSheet(state = bottomSheetState)
    }

}


@Composable
fun EmployeesContent(
    employee: List<SearchAble>,
    onClickConcrete: (SearchAble) -> Unit,
) {

    LazyColumn(Modifier.padding(start = 16.dp, end = 16.dp)) {
        items(employee, key = { it.getID() }) {
            EmployeeListItem(searchAble = it, onClickConcrete = onClickConcrete)
        }
    }
}


@Composable
fun EmployeesContentWithBirthDay(
    employee: List<SearchAble>,
    onClickConcrete: (SearchAble) -> Unit,
) {
    val currentNextYear = LocalDate.now().year.plus(1).toString()

    val currentYearBirthdayEmployee =
        employee.filterNot {
            it.getReified<EmployeeUiModel>()
                .ageHub
                .isAtNextYear()
        }

    val nextYear = employee.filter {
        it.getReified<EmployeeUiModel>()
            .ageHub
            .isAtNextYear()
    }

    LazyColumn(Modifier.padding(start = 16.dp, end = 16.dp)) {
        items(currentYearBirthdayEmployee) { employee ->
            EmployeeListItemBirthDay(searchAble = employee, onClickConcrete = onClickConcrete)
        }
        item { YearSpacerPlanchette(nextYear = currentNextYear) }
        items(nextYear) { employee ->
            EmployeeListItemBirthDay(searchAble = employee, onClickConcrete = onClickConcrete)
        }
    }
}

@Composable
fun EmployeeListItem(
    searchAble: SearchAble,
    onClickConcrete: (SearchAble) -> Unit,
) {
    val employeeUiModel: EmployeeUiModel = searchAble.getReified()
    Row(
        modifier = Modifier
            .height(80.dp)
            .clickable { onClickConcrete(searchAble) },
        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .width(88.dp)
                .padding(top = 6.dp, bottom = 6.dp, end = 16.dp)
                .height(72.dp)
        ) {
            Image(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(50))
                    .size(72.dp),
                painter = rememberAsyncImagePainter(
                    model = searchAble
                        .getReified<EmployeeUiModel>()
                        .avatarUrl,

                    ),
                alignment = Alignment.Center,
                contentDescription = "image description",
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .height(60.dp)
                .padding(top = 7.dp, bottom = 7.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    modifier = Modifier
                        .offset(x = 0.dp, y = 3.dp),
                    text = format(
                        NAME_PATTERN,
                        employeeUiModel.firstName,
                        employeeUiModel.lastName
                    ),
                    style = KodeTraineeTypography.titleLarge
                )

                Text(
                    text = employeeUiModel.userTag,
                    style = KodeTraineeTypography.labelMedium
                )
            }

            Text(
                text = employeeUiModel.position,
                style = KodeTraineeTypography.labelSmall
            )
        }
    }
}


private const val NAME_PATTERN = "%s %s"

@Preview
@Composable
fun previewCard() {
    val mockUiModel: EmployeeUiModel = EmployeeUiModel(
        avatarUrl = "",
        birthday = "",
        department = "",
        firstName = "Алексей",
        id = "",
        lastName = "Миногаров",
        phone = "",
        position = "Analyst",
        userTag = "mi",
        mail = ""
    )
    KodetraineeTheme() {
        EmployeeListItem(searchAble = mockUiModel) {}
    }
}


@Composable
fun EmployeeListItemBirthDay(
    searchAble: SearchAble,
    onClickConcrete: (SearchAble) -> Unit,
) {
    val employeeUiModel: EmployeeUiModel = searchAble.getReified()
    Row(
        modifier = Modifier
            .height(80.dp)
            .clickable { onClickConcrete(searchAble) },
        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .width(88.dp)
                .padding(top = 6.dp, bottom = 6.dp, end = 16.dp)
                .height(72.dp)
        ) {
            Image(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(50))
                    .size(72.dp)
                    .clip(CircleShape),
                painter = rememberAsyncImagePainter(
                    model = searchAble
                        .getReified<EmployeeUiModel>()
                        .avatarUrl,
                ),
                contentDescription = "image description",
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .height(60.dp)
                .padding(top = 7.dp, bottom = 7.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    modifier = Modifier
                        .offset(x = 0.dp, y = 3.dp),
                    text = format(
                        NAME_PATTERN,
                        employeeUiModel.firstName,
                        employeeUiModel.lastName
                    ),
                    style = KodeTraineeTypography.titleLarge
                )

                Text(
                    text = employeeUiModel.userTag,
                    style = KodeTraineeTypography.labelMedium
                )
                Spacer(modifier = Modifier.weight(1f, true))
                Text(
                    text = EmployeeUiModel.formatBirthdayWithoutAge(employeeUiModel.ageHub),
                    style = KodeTraineeTypography.labelLarge,
                    fontWeight = FontWeight.W400,
                    color = lightTextPrimary
                )
            }

            Text(
                text = employeeUiModel.position,
                style = KodeTraineeTypography.labelSmall
            )
        }
    }
}

@Composable
@Preview
fun previewPlanchette() {
    KodetraineeTheme() {
        YearSpacerPlanchette(nextYear = 2020.toString())
    }
}


@Composable
fun YearSpacerPlanchette(nextYear: String) {

    Row(
        modifier = Modifier
            .height(68.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.weight(0.3f)) {
            PlanchetteDivider()
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.3f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = nextYear, style = KodeTraineeTypography.labelLarge)
        }
        Column(modifier = Modifier.weight(0.3f)) {
            PlanchetteDivider()
        }

    }
}

@Composable
private fun PlanchetteDivider() {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp, start = 12.dp),
            thickness = 2.dp,
            color = lightContentDefaultSecondary
        )
    }
}
