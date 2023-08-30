package com.example.thindie.kodetrainee.presentation.uiunits.customStatusBar

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.BatteryManager
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.thindie.kodetrainee.R
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@Composable
fun StatusBar(
    modifier: Modifier = Modifier,
    statusBarState: StatusBarState = rememberStatusBarState(),
) {
    val time = statusBarState.timeState.collectAsStateWithLifecycle()
    val battery = statusBarState.batteryState.collectAsStateWithLifecycle()
    Row(modifier.height(44.dp)) {

    }
}



@Composable
fun rememberStatusBarState(): StatusBarState {
    val controller = rememberSystemUiController()

    val scope = rememberCoroutineScope()
    return remember(
        controller, scope
    ) {
        StatusBarState(controller, scope)
    }
}

@Stable
class StatusBarState(
    private val controller: SystemUiController,
    private val scope: CoroutineScope,
) {

  //  @Inject
    lateinit var connectivityManager: ConnectivityManager


    private val _batteryState = MutableStateFlow(1f)
    val batteryState: StateFlow<Float>
        get() = _batteryState

    private val _wifiState = MutableStateFlow(0)
    val wifiState
        get() = _wifiState.stateIn(
            scope, started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = 0
        )


    init {
        //controller.isStatusBarVisible = false
    }

    private val simpleDateFormatPattern = "HH:mm"
    private val formatter = SimpleDateFormat(simpleDateFormatPattern, Locale.GERMAN)

    private val _timeState: MutableStateFlow<String> = MutableStateFlow("")
    val timeState =
        _timeState.stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ""
        )

   // @Inject
    fun trackTime() {
        scope.launch {
            while (true) {
                val date = Date()
                _timeState.tryEmit(formatter.format(date))
                delay(60_000L)
            }
        }
    }

   // @Inject
    fun trackWifi(wifiManager: WifiManager) {
        scope.launch {
            while (true) {
                val wifiInfo = wifiManager.connectionInfo.rssi
                _wifiState.tryEmit(wifiInfo)
                delay(4_000L)
            }
        }
    }

    //@Inject
    fun trackBattery(context: Application) {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            context.registerReceiver(null, ifilter)
        }
        scope.launch {
            while (true) {
                val batteryPct: Float? = batteryStatus?.let { intent ->
                    val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                    val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                    level * 100 / scale.toFloat()
                }
                _batteryState.tryEmit(batteryPct ?: 100f)
                delay(1_000L)
            }
        }
    }

    @DrawableRes
    fun bindBatteryImageResource(value: Float): Int {
        return when (value) {
            in 0.0f..14.0f -> R.drawable.notification_battery_5
            in 15.0f..49.0f -> R.drawable.notification_battery_15
            in 50.0f..74.0f -> R.drawable.notification_battery_50
            in 75.0f..99.0f -> R.drawable.notification_battery_75
            else -> R.drawable.notification_battery_100
        }
    }

}