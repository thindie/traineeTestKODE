package com.example.thindie.kodetrainee.presentation.screens.allemployees


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.kodetrainee.data.remote.di.ConnectionStatus
import com.example.thindie.kodetrainee.domain.Repository
import com.example.thindie.kodetrainee.presentation.di.FilterHolder
import com.example.thindie.kodetrainee.presentation.entity.EmployeeUiModel
import com.example.thindie.kodetrainee.presentation.matchCriteria
import com.example.thindie.kodetrainee.presentation.toUiModel
import com.example.thindie.kodetrainee.presentation.uiunits.SearchAble
import com.example.thindie.kodetrainee.presentation.uiunits.bottomSheet.SelectedType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@HiltViewModel
class EmployeesViewModel @Inject constructor(
    private val repository: Repository,
    private val status: ConnectionStatus,
    private val filter: FilterHolder,
) : ViewModel() {
    private val _filter: MutableState<Comparator<SearchAble>> =
        mutableStateOf(filter.alphabetCompare)
    val currentFilter: State<Comparator<SearchAble>>
        get() = _filter

    private val _isFilteringByAlphabet = mutableStateOf(true)
    val isFilteringByAlphabet: State<Boolean>
        get() = _isFilteringByAlphabet


    private val _currentText = mutableStateOf("")
    val currentText: State<String>
        get() = _currentText

    private val _isSearchActive = mutableStateOf(false)
    val isSearchActive: State<Boolean>
        get() = _isSearchActive

    private val employeesList = mutableStateOf(emptyList<SearchAble>())
    private val bufferList = mutableStateOf(emptyList<SearchAble>())

    private val _currentTab = mutableStateOf(SHOW_ALL)
    val currentTab
        get() = _currentTab.value


    private val _employeesScreen: MutableStateFlow<ScreenState> =
        MutableStateFlow(ScreenState())
    val employeesScreen = _employeesScreen.stateIn(
        started = SharingStarted.WhileSubscribed(5_000L),
        scope = viewModelScope,
        initialValue = ScreenState()
    )

    private val _isHotting = mutableStateOf(true)


    fun onStart() {
        stateStart()
        viewModelScope.launch {
            if (status.isConnected()) {
                repository.getEmployees()
                    .onSuccess { employees ->
                        employeesList.value = employees.map { they -> they.toUiModel() }
                        onClickTab(currentTab)
                    }
                    .onFailure {
                        stateError()
                    }
            } else {
                stateError()
            }
        }
    }


    fun onClickTab(tabTag: String) {
        _currentTab.value = tabTag
        if (employeesList.value.isEmpty()) {
            onStart()
            return
        }
        val listToShow = employeesList
            .value
            .filter { employee ->
                if (tabTag == SHOW_ALL) {
                    true
                } else {
                    employee
                        .getReified<EmployeeUiModel>()
                        .department == tabTag
                }
            }.filter { searchableEmployee ->
                searchableEmployee
                    .getTagShadow()
                    .matchCriteria(currentText.value)
            }

        if (listToShow.isEmpty()) {
            stateNothingFound()
        } else {
            stateSuccess(listToShow)
        }

    }

    fun onClickConcrete(employeeUiModel: EmployeeUiModel) {
        repository.setEmployee(
            employeeUiModel.toEmployee()
        )
    }


    private fun onFilterByAlphabet() {
        _isFilteringByAlphabet.value = true
        _filter.value = filter.alphabetCompare
    }

    private fun onFilterByBirthday() {
        _isFilteringByAlphabet.value = false
        _filter.value = filter.birthDayCompare
    }


    fun onSearch(searchState: State<String>) {
        _currentText.value = searchState.value
    }


    fun onRefreshCurrentTab() {
        _employeesScreen.value = ScreenState()
        viewModelScope.launch {
            repository.getEmployees()
                .onSuccess { employees ->

                    employeesList.value = employees.map { they -> they.toUiModel() }
                    onClickTab(currentTab)
                }
                .onFailure { throwable ->
                    onClickTab(currentTab)
                }
        }
    }


    fun onBottomSheetChoseSort(
        selected: StateFlow<SelectedType>,
        activate: () -> Unit,
        deactivate: () -> Unit,
    ) {
        selected.onEach { type ->
            when (type) {
                SelectedType.BIRTHDAY -> {
                    onFilterByBirthday()
                    activate.invoke()
                }
                SelectedType.ALPHABET -> {
                    onFilterByAlphabet()
                    deactivate.invoke()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun stateSuccess(listToShow: List<SearchAble>) {
        bufferList.value = listToShow
        _employeesScreen.value =
            ScreenState(employees = listToShow, isLoading = false, isNothingFound = false)
    }

    private fun stateStart() {
        if (_isHotting.value) {
            _employeesScreen.value =
                ScreenState(isHotting = _isHotting.value)
            _isHotting.value = false
        }
    }

    private fun stateNothingFound() {
        _employeesScreen.value =
            ScreenState(isLoading = false, employees = emptyList(), isNothingFound = true)
    }

    private fun stateError() {
        _employeesScreen.value =
            ScreenState(isLoading = false, employees = null, isNothingFound = false)
    }


    data class ScreenState(
        val isHotting: Boolean = false,
        val isLoading: Boolean = true,
        val employees: List<SearchAble>? = null,
        val isNothingFound: Boolean = false,
    )

    companion object {
        private const val SHOW_ALL = "show_all"
    }
}