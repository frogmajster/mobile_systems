package com.example.mobile_project.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.mobile_project.data.AttendanceRepoistory

class AttendanceViewModel : ViewModel() {
    private val repository = AttendanceRepoistory()
    val currentPIN = repository.currentPIN
    val attendees = repository.attendees

    fun generatePIN(){
        repository.generatePIN()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registerAttendance(enteredPIN: String, studentName: String): Boolean{
        if(enteredPIN.isNotEmpty() && enteredPIN == currentPIN.value) {
            repository.addAttendee(studentName)
            return true
        } else {
            return false
        }
    }
}
