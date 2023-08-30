package com.example.thindie.kodetrainee.presentation.screens.concreteemployee

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.kodetrainee.domain.ConcreteEmployeeRepository
import com.example.thindie.kodetrainee.presentation.entity.EmployeeUiModel
import com.example.thindie.kodetrainee.presentation.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ConcreteEmployeeViewModel
@Inject constructor(private val concreteEmployeeRepository: ConcreteEmployeeRepository) :
    ViewModel() {

    private val _concreteScreen = MutableStateFlow(ConcreteScreenState())
    val concreteScreen: StateFlow<ConcreteScreenState>
        get() = _concreteScreen.asStateFlow()

    fun onClickEmployee() {
        viewModelScope.launch {
            concreteEmployeeRepository.getEmployee()
                .onSuccess {
                    _concreteScreen.value = ConcreteScreenState(employee = it.toUiModel())
                }
                .onFailure { _concreteScreen.value = ConcreteScreenState() }
        }
    }

    data class ConcreteScreenState(
        val employee: EmployeeUiModel? = null,
    )
}