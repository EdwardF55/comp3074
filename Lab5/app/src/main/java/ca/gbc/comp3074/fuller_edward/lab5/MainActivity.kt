package ca.gbc.comp3074.fuller_edward.lab5

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import ca.gbc.comp3074.fuller_edward.lab5.ui.theme.Lab5Theme
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.GoogleMap

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LocationComponent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LocationComponent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val fusedClient = remember(context) {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val scope = rememberCoroutineScope()
    val location = remember{mutableStateOf<Location?>(null)}
    val status = remember{mutableStateOf("")}

    var locationCallback: LocationCallback? = null

    fun hasPermission(): Boolean{
        val fine = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarse = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return fine == PackageManager.PERMISSION_GRANTED || coarse == PackageManager.PERMISSION_GRANTED
    }

    fun fetchLocation(){
        if(!hasPermission()){
            status.value = "We don't have permission to get the location."
            return
        }

        status.value = "Fetching the location. Give us a momen'!"
        val cancelToken = CancellationTokenSource()
        fusedClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancelToken.token
        ).addOnSuccessListener{locationStatus ->
            if (locationStatus == null){
                status.value = "Location wasn't available!"
            } else {
                location.value = locationStatus
                status.value = "Found it. (%.5f, %.5f)".format(locationStatus.latitude, locationStatus.longitude)
            }
        }.addOnFailureListener { e ->
            status.value = "Ran into an issue! ${e.localizedMessage ?: "I have no idea what happened. Unknown error!"}"
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()){
            res ->
            if(res.values.any{it}){
                fetchLocation()
            } else {
                status.value = "Didn't get permission."
            }
        }

    fun startTracking(){
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000L
        ).setMinUpdateIntervalMillis(2000L).build()

        locationCallback = object: LocationCallback(){
            override fun onLocationResult(result: LocationResult){
                super.onLocationResult(result)
                location.value = result.lastLocation
                status.value = "Location: (%.5f, %.5f)".format(
                    result.lastLocation?.latitude,
                    result.lastLocation?.longitude
                )
            }
        }
        fusedClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = status.value,
            modifier = modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if(!hasPermission()){
                    permissionLauncher.launch(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ))
                } else {
                    fetchLocation()
                }
            },
            modifier = modifier.fillMaxWidth()
        ){
            Text(text = "Get location")
        }
        Button(
            onClick = {
                status.value = "Now tracking..."
                startTracking()
                      },
            modifier = modifier.fillMaxWidth()
        ){
            Text(text = "Start tracking")
        }
        Button(
            onClick = {locationCallback?.let {fusedClient.removeLocationUpdates(it)}},
            modifier = modifier.fillMaxWidth()
        ){
            Text(text = "Stop tracking")
        }
//        GoogleMap(modifier = Modifier.fillMaxWidth()){
//            location.value?.let(
//
//            )
//        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab5Theme {
        LocationComponent()
    }
}