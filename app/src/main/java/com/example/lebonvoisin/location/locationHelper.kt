package com.example.lebonvoisin.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class LocationHelper(
    private val context: Context
) {
    @SuppressLint("MissingPermission")
    fun getCurrentAddress(onResult: (String) -> Unit) {
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location ->

            if (location == null) {
                onResult("Position introuvable")
                return@addOnSuccessListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val geocoder = Geocoder(context, Locale.FRANCE)

                    val addresses = geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    )

                    val address = addresses?.firstOrNull()

                    val numero = address?.subThoroughfare
                    val rue = address?.thoroughfare
                    val ville = address?.locality

                    val adresseCourte = listOfNotNull(numero, rue, ville)
                        .joinToString(" ")

                    withContext(Dispatchers.Main) {
                        onResult(adresseCourte.ifBlank { "Adresse introuvable" })
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        //onResult("Erreur géolocalisation")
                        onResult("${location.latitude}, ${location.longitude}")
                    }
                }
            }

        }.addOnFailureListener {
            onResult("Erreur position")
        }
    }
}