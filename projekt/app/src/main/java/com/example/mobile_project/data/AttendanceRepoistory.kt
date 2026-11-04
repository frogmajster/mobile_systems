package com.example.mobile_project.data

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

//https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/

class AttendanceRepoistory {
    private val _currentPIN = MutableStateFlow("")
    val currentPIN: StateFlow<String> = _currentPIN.asStateFlow()

    private val _attendees = MutableStateFlow<List<AttendanceRecord>>(emptyList())
    val attendees: StateFlow<List<AttendanceRecord>> = _attendees.asStateFlow()


    fun generatePIN(){
        val newPIN = (1000..9999).random().toString()
        _currentPIN.value = newPIN
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addAttendee(name: String){
        val newRecord = AttendanceRecord(studentName = name, currentTime = LocalDateTime.now())
        _attendees.value += newRecord
    }
}