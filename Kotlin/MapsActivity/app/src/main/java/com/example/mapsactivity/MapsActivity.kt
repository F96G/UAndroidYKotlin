package com.example.mapsactivity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mapsactivity.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

//Una vez creada la actividad se debe llenar lo necesario en google maps api
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    //Se reciclo de ejemploPermiso
    private val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val permisoCoarseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION
    private val CODIGO_SOLICITUD_PERMISO = 100
    var fusedLocationClient: FusedLocationProviderClient? = null
    var locationRequest:com.google.android.gms.location.LocationRequest? = null

    var callback: LocationCallback? = null

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = FusedLocationProviderClient(this)
        inicializarLocationRequest()

        callback = object: LocationCallback(){
            //me entrega las cordenadas actualizadas
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                for (ubicacion in locationResult.locations){
                    Toast.makeText(applicationContext, ubicacion.latitude.toString() + " - " + ubicacion.longitude.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if(validarPermsosUbicacion())
            obtenerUbicacion()
        else
            pedirPermiso()

        // Add a marker in Sydney and move the camera

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
                    val sydney = LatLng(ubicacion.latitude, ubicacion.longitude)
                    mMap.addMarker(MarkerOptions().position(sydney).title("Estoy aqui"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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