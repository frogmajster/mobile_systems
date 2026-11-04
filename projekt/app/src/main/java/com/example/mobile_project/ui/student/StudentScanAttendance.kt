package com.example.mobile_project.ui.student

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.mobile_project.viewmodel.AttendanceViewModel


@Composable
fun ScanStudentAttendance(viewModel: AttendanceViewModel) {
    var studentName by remember { mutableStateOf("") }
    var enteredPIN by remember { mutableStateOf("") }
}
