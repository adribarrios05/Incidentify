package com.alaturing.incidentify.main.incident.ui.edit

sealed class IncidentEditUiState {
    data object New: IncidentEditUiState()
    data class Created(val id:Int): IncidentEditUiState()
    data class Error(val message:String): IncidentEditUiState()

}