package com.vindove.pos.savesoul.ui.emergency

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Base64
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.Audio
import com.otaliastudios.cameraview.controls.Engine
import com.otaliastudios.cameraview.controls.Facing
import com.otaliastudios.cameraview.controls.Mode
import com.otaliastudios.cameraview.controls.PictureFormat
import com.otaliastudios.cameraview.controls.Preview


@Composable
fun EmergencyScreen(
    modifier: Modifier = Modifier,
    viewModel: EmergencyScreenViewModel = hiltViewModel(),
    onSuccessfullyPosted: () -> Unit = {}
) {
    val screenState by viewModel.emergencyScreenState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraView = remember { CameraView(context) }

    val fusedLocationClient = remember(context) {
        LocationServices.getFusedLocationProviderClient(context)
    }

    // Request location permission and handle the result
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                LocationRequest.create().apply {
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    interval = 10000 // Update location every 10 seconds
                },
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        locationResult.lastLocation?.let {
                            viewModel.updateUserLocation(it)
                        }
                    }
                },
                Looper.getMainLooper()
            )
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_LOCATION
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {

        AndroidView(
            modifier = Modifier,
            factory = {
                cameraView.apply {
                    setRequestPermissions(true)
                    mode = Mode.PICTURE
                    facing = Facing.BACK
                    audio = Audio.OFF
                    engine = Engine.CAMERA2
                    preview = Preview.SURFACE
                    pictureFormat = PictureFormat.JPEG
                    addCameraListener(object : CameraListener() {
                        override fun onPictureTaken(result: PictureResult) {
                            val data = result.data
                            val base64String = Base64.encodeToString(data, Base64.DEFAULT)
                            viewModel.updateImageString(base64String)

                            viewModel.sendEmergencyReport()
                        }
                    })
                }
            })

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { source, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {
                        cameraView.open();
                    }

                    Lifecycle.Event.ON_PAUSE -> {
                        cameraView.close()
                    }

                    Lifecycle.Event.ON_DESTROY -> {
                        cameraView.destroy()
                    }

                    else -> {

                    }
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

        LaunchedEffect(key1 = Unit) {
            cameraView.open()
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            onClick = { cameraView.takePicture() }) {
            Text(text = "Capture Image")
        }
        if (screenState.isSuccessful == true) {
            onSuccessfullyPosted.invoke()
        }
    }
}

private const val PERMISSION_REQUEST_LOCATION = 1001