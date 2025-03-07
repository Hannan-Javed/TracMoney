package com.example.tracmoney

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    companion object {
        var totalSectionsCreated: Int = 1
    }
    private val _selectedSectionId = MutableStateFlow(1)
    val selectedSectionId: StateFlow<Int> get() = _selectedSectionId


    private val _monthlyTransactions = MutableStateFlow<MonthlyTransactions?>(null)
    val monthlyTransactions: StateFlow<MonthlyTransactions?> get() = _monthlyTransactions

    init {
        _monthlyTransactions.value = MonthlyTransactions(
            month = 1,
            year = 2023,
            sections = mutableListOf(
                TransactionSection(
                    id = 1,
                    title = "Default Section",
                    currency = "USD"
                )
            )
        )
        _selectedSectionId.value = monthlyTransactions.value?.sections?.first()?.id!!
    }

    fun getNewId(): Int {
        return ++totalSectionsCreated
    }

    fun selectSection(sectionId: Int) {
        _selectedSectionId.value = sectionId
    }

    fun addSection(newSection: TransactionSection) {
        _monthlyTransactions.value?.sections?.add(newSection)
        _selectedSectionId.value = newSection.id
    }

    fun addTransaction(transaction: Transaction) {
        _monthlyTransactions.value?.sections?.find { it.id == selectedSectionId.value }?.addTransaction(transaction)
    }

    fun deleteSection(sectionId: Int) {
        if (_selectedSectionId.value == sectionId) {
            _selectedSectionId.value = monthlyTransactions.value?.sections?.first()?.id!!
        }
        _monthlyTransactions.value?.sections?.removeIf { it.id == sectionId }
    }
}