package com.example.ejemplopermisos

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.tasks.OnSuccessListener

//Para este tipo de permisos de mayor riesgo para el usuario se debe buscar en SDKManager y habilitar google play service
//Luego aplico la libreria en gradle implementation 'com.google.android.gms:play-services-location:18.0.0'
//En manifest se debe poner uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" y de igual foma para coarse
class MainActivity : AppCompatActivity() {

    //Fine location utiliza una localizacion muy precisa y "costosa"
    private val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION
    //Coarse Location no es tan precisa pero gasta menos recursos y no utiliza gps
    private val permisoCoarseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION
    //Es un codigo que identifica el permiso
    private val CODIGO_SOLICITUD_PERMISO = 100
    //Esta variable almacena la ultima hubicacion
    var fusedLocationClient:FusedLocationProviderClient? = null
    //LocationRequest permite estar sabiendo contantemente mi hubicacion
    var locationRequest:com.google.android.gms.location.LocationRequest? = null

    var callback:LocationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = FusedLocationProviderClient(this)

        inicializarLocationRequest()
    }

    private fun inicializarLocationRequest(){
        locationRequest = com.google.android.gms.location.LocationRequest()
        //10s
        locationRequest?.interval = 10000
        //Lo maximo que se va a actualizar en 5seg
        locationRequest?.fastestInterval = 5000
        //que tanta proximidad se solicita, esto afecta al uso de la bateria
        locationRequest?.priority = LocationRequest.QUALITY_HIGH_ACCURACY

    }



    private fun pedirPermiso() {
        //shouldShowRequestPermissionRationale en caso de rechazar el permiso le dara informacion al usuario de porque es necesaria
        var deboProveerContexto:Boolean = ActivityCompat.shouldShowRequestPermissionRationale(this, permisoFineLocation)

        if (deboProveerContexto) {
            //Explicacion adicional

            solicitudPermiso()
        }
        else
            solicitudPermiso()
    }

    private fun solicitudPermiso() {
        requestPermissions(arrayOf(permisoCoarseLocation, permisoFineLocation),CODIGO_SOLICITUD_PERMISO)
    }

    //Cuando se solicite el permiso requestPermissions se ejecutara esta funcion, grantResults es un arreglo que me garantiza la respuesta
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CODIGO_SOLICITUD_PERMISO -> {
                if (grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Obtener Ubicacion
                    obtenerUbicacion()
                }else
                    Toast.makeText(this, "No diste permiso para obtener ubicacion", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //SuppressLint elimina el error MissingPermission
    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion() {
        //lastLocation me da la ultima ubicacion utilizada en el sistema andriod
        /*fusedLocationClient?.lastLocation?.addOnSuccessListener(this, object: OnSuccessListener<Location>{
            override fun onSuccess(location: Location) {
                if (location != null)
                    Toast.makeText(applicationContext, location.latitude.toString() + "-" + location.longitude.toString(), Toast.LENGTH_SHORT).show()
            }

        })*/

        callback = object: LocationCallback(){
            //me entrega las cordenadas actualizadas
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                for (ubicacion in locationResult.locations){
                    Toast.makeText(applicationContext, ubicacion.latitude.toString() + " - " + ubicacion.longitude.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        //me permite estar monitoriando la hubicacion constantemente, VER COMO SACAR LOOPER
        fusedLocationClient?.requestLocationUpdates(locationRequest!!, callback!!, Looper.myLooper()!! )
    }

    private fun validarPermsosUbicacion(): Boolean {
        //me permite saber si esta autorizado el perimso
        val hayUbicacionPrecisa = ActivityCompat.checkSelfPermission(this, permisoFineLocation) == PackageManager.PERMISSION_GRANTED
        val hayUbicacionOrdinaria = ActivityCompat.checkSelfPermission(this, permisoCoarseLocation) == PackageManager.PERMISSION_GRANTED

        return hayUbicacionOrdinaria && hayUbicacionPrecisa
    }

    private fun detenerActualizacionUbicacion() {
        //detiene el pedido de hubicacion
        fusedLocationClient?.removeLocationUpdates(callback!!)
    }

    override fun onStart() {
        super.onStart()
        if(validarPermsosUbicacion())
            obtenerUbicacion()
        else
            pedirPermiso()
    }

    override fun onPause() {
        super.onPause()
        //para no malgastar recursos al estar en pausa este se detiene
        detenerActualizacionUbicacion()
    }


}
