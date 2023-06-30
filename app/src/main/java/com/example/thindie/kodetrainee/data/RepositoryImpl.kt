package com.example.thindie.kodetrainee.data

import com.example.thindie.kodetrainee.data.remote.EmployeesService
import com.example.thindie.kodetrainee.domain.ConcreteEmployeeRepository
import com.example.thindie.kodetrainee.domain.Employee
import com.example.thindie.kodetrainee.domain.Repository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.delay

@Singleton
class RepositoryImpl @Inject constructor(private val service: EmployeesService) : Repository,
    ConcreteEmployeeRepository {
    private var lock: Int = 1
    private lateinit var employee: Employee
    override suspend fun getEmployees(): Result<List<Employee>> {
        return kotlin.runCatching {
            val response = service.getEmployees()
            response.employeeList
        }
            .mapCatching { employees ->
                employees.map { dto ->
                    dto.toEmployee(lock++)
                }
            }.apply { lock = 1 }
    }

    override fun setEmployee(employee: Employee) {
        this.employee = employee
    }

    override suspend fun getEmployee(): Result<Employee> {
        //simulate som suspend action
        return kotlin.runCatching {
            delay(1L)
            employee
        }
    }
}