package com.example.task_prp.ui.connectivity

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun connectionObserver(): Flow<ConnectionStatus>

    enum class ConnectionStatus{
        Connected,
        Offline
    }
}