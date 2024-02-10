package com.vindove.pos.savesoul.ui.emergency

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vindove.pos.savesoul.data.remote.dto.request.SOSRequest
import com.vindove.pos.savesoul.domain.repository.SosRepository
import com.vindove.pos.savesoul.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EmergencyScreenViewModel @Inject constructor(private val repository: SosRepository) :
    ViewModel() {
    private val _emergencyScreenState = MutableStateFlow(EmergencyScreenViewModelState())
    val emergencyScreenState = _emergencyScreenState.asStateFlow()

    fun updateUserLocation(location: Location?) {
        _emergencyScreenState.update {
            it.copy(
                longitude = location?.longitude.toString(),
                latitude = location?.latitude.toString()
            )
        }
    }

    fun updateImageString(base64String: String) {
        _emergencyScreenState.update {
            it.copy(
                imageString = base64String
            )
        }
    }

    fun sendEmergencyReport() {
        var emergencyList = emptyList<String>()
        viewModelScope.launch {
            async {
                repository.fetchAllEmergencyContact().collectLatest {
                    emergencyList = it.map { it.contactNumber }
                }
            }.await()

            val request = SOSRequest(
                emergencyList,
                emergencyScreenState.value.imageString.orEmpty(),
                com.vindove.pos.savesoul.data.remote.dto.request.Location(
                    emergencyScreenState.value.longitude.orEmpty(),
                    emergencyScreenState.value.latitude.orEmpty()
                )
            )
            repository.sendSOSRequest(request).collectLatest {
                when(it){
                    is Result.Success -> {
                        _emergencyScreenState.update {
                            it.copy(
                                isSuccessful = true
                            )
                        }
                    }

                    is Result.Failure -> {
                        Log.e("wadud", it.exception.toString())
                    }

                    is Result.Loading-> {

                    }
                }
            }
        }
    }
}


data class EmergencyScreenViewModelState(
    val longitude: String? = null,
    val latitude: String? = null,
    val imageString: String? = null,
    val isSuccessful : Boolean? = null
)