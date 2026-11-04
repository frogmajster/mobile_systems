package com.example.mobile_project.ui.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.mobile_project.viewmodel.AttendanceViewModel

@Composable
fun AdminAttendanceScreen(viewModel: AttendanceViewModel){
    val currentPIN by viewModel.currentPIN.collectAsState()
    val attendees by viewModel.attendees.collectAsState()
}
