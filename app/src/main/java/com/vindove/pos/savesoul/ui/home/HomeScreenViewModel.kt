package com.vindove.pos.savesoul.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vindove.pos.savesoul.di.IoDispatcher
import com.vindove.pos.savesoul.domain.model.EmergencyContact
import com.vindove.pos.savesoul.domain.repository.SosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val sosRepository: SosRepository,
    @IoDispatcher private val  ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _emergencyContacts : MutableStateFlow<List<EmergencyContact>> = MutableStateFlow(emptyList())
    val emergencyContact = _emergencyContacts.asStateFlow()


    init {
        viewModelScope.launch {
            fetchEmergencyContacts()
        }
    }

    private suspend fun fetchEmergencyContacts() {
       withContext(ioDispatcher){
           sosRepository.fetchAllEmergencyContact().collectLatest {
               _emergencyContacts.value = it
           }
       }
    }

}