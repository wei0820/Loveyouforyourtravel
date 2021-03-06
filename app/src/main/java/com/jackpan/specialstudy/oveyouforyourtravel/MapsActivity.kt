package com.jackpan.specialstudy.oveyouforyourtravel

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jackpan.specialstudy.oveyouforyourtravel.Data.GoogleMapPlaceDetailsData


import com.jackpan.specialstudy.oveyouforyourtravel.Data.GoogleResponseData

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMapAPISerive.GetResponse, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener
        , LocationListener, GoogleMap.OnCameraMoveStartedListener,GoogleMap.OnCameraIdleListener {
    override fun getDetailData(googleMapPlaceDetailsData: GoogleMapPlaceDetailsData?) {
    }

    override fun onCameraIdle() {
//        Log.d("Jack","onCameraIdle")
//        Log.d("Jack", mMap.cameraPosition.target.latitude.toString())
//        Log.d("Jack", mMap.cameraPosition.target.longitude.toString())
//        val sydney = LatLng(mMap.cameraPosition.target.latitude, mMap.cameraPosition.target.longitude)
//        var latlon: String = mMap.cameraPosition.target.latitude.toString() + "," + mMap.cameraPosition.target.longitude.toString()
//
//        GoogleMapAPISerive.setPlaceForRestaurant(this@MapsActivity, latlon, "",this@MapsActivity)
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCameraMoveStarted(p0: Int) {
//        Log.d("Jack", "onCameraMoveStarted")
    }

    override fun onLocationChanged(p0: Location?) {


    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
    }

    override fun onProviderEnabled(p0: String?) {
    }

    override fun onProviderDisabled(p0: String?) {
    }

    override fun onCameraMove() {
//        Log.d("Jack","onCameraMove")
//        Log.d("Jack",mMap.cameraPosition.target.latitude.toString())
//        Log.d("Jack",mMap.cameraPosition.target.longitude.toString())
//        GoogleMapAPISerive.setPlaceForRestaurant(this@MapsActivity, latlon, this@MapsActivity)

    }

    override fun onCameraMoveCanceled() {
        Log.d("Jack", "onCameraMoveCanceled")
        Log.d("Jack", mMap.cameraPosition.target.latitude.toString())
        Log.d("Jack", mMap.cameraPosition.target.longitude.toString())

    }

    override fun getData(googleResponseData: GoogleResponseData?) {
        if (googleResponseData != null) {
            for (result in googleResponseData.results) {
                if (result.vicinity==null){
                    result.vicinity = "no address"
                }


                addMarker(LatLng(result.geometry.location.lat, result.geometry.location.lng),
                        result.name,
                        result.vicinity,
                        result.types)

            }
        }
    }
    var mFistBoolean : Boolean = true

    private lateinit var mMap: GoogleMap
    val MY_PERMISSIONS_REQUEST_LOCATION = 100
    private var locationManager: LocationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        checkPermission()
        initLayout()


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
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMyLocationEnabled(true)
        mMap.setOnCameraMoveStartedListener(this)
        mMap.setOnCameraMoveListener(this)
        mMap.setOnCameraMoveCanceledListener(this)
        mMap.setOnCameraIdleListener(this)

        // Add a marker in Sydney and move the camera

    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
        }

    }

    fun initLayout() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch (ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }

    }

    // 在地圖加入指定位置與標題的標記
    private fun addMarker(place: LatLng, title: String, context: String, array: Array<String>) {
        var icon: BitmapDescriptor  = BitmapDescriptorFactory.fromResource(R.mipmap.loction_icon)

        val markerOptions = MarkerOptions()
        markerOptions.position(place)
                .title(title)
                .snippet(context)
                .icon(icon)

        mMap.addMarker(markerOptions)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "需要定位功能,才能使用喔", Toast.LENGTH_SHORT).show()
                return
            }
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d("Location", location.latitude.toString())
            Log.d("Location", location.longitude.toString())
            var latlon: String = location.latitude.toString() + "," + location.longitude.toString()
//            locationTextView.text = "${location.latitude} - ${location.longitude}"

            if(mFistBoolean){
                GoogleMapAPISerive.setPlaceForRestaurant(this@MapsActivity, "22.666868,120.314892", GoogleMapAPISerive.TYPE_RESTAURANT,this@MapsActivity)
                GoogleMapAPISerive.setPlaceForRestaurant(this@MapsActivity, "22.663020,120.312451", GoogleMapAPISerive.TYPE_RESTAURANT,this@MapsActivity)
                GoogleMapAPISerive.setPlaceForRestaurant(this@MapsActivity, "22.660791,120.312044", GoogleMapAPISerive.TYPE_RESTAURANT,this@MapsActivity)

                GoogleMapAPISerive.setPlaceForRestaurant(this@MapsActivity, "22.657730,120.312742", GoogleMapAPISerive.TYPE_RESTAURANT,this@MapsActivity)

                GoogleMapAPISerive.setPlaceForRestaurant(this@MapsActivity, "22.655478,120.302776", GoogleMapAPISerive.TYPE_RESTAURANT,this@MapsActivity)


                GoogleMapAPISerive.setPlaceForRestaurant(this@MapsActivity, "22.649366,120.293058", GoogleMapAPISerive.TYPE_RESTAURANT,this@MapsActivity)

                GoogleMapAPISerive.setPlaceForRestaurant(this@MapsActivity, "22.638434,120.286278", GoogleMapAPISerive.TYPE_RESTAURANT,this@MapsActivity)



                GoogleMapAPISerive.setPlaceForRestaurant(this@MapsActivity, "22.646574,120.284491", GoogleMapAPISerive.TYPE_RESTAURANT,this@MapsActivity)

                GoogleMapAPISerive.setPlaceForRestaurant(this@MapsActivity, "22.625442,120.289308", GoogleMapAPISerive.TYPE_RESTAURANT,this@MapsActivity)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 18.0f))
                mFistBoolean = false
            }


        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}
