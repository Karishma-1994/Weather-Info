package com.example.weather_info.weatherInfo

import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather_info.R
import com.example.weather_info.databinding.FragmentWeatherBinding
import com.example.weather_info.util.loadWeatherIcon
import com.example.weather_info.weatherInfo.adapter.TodayAdapter
import com.example.weather_info.weatherInfo.adapter.WeeklyAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.net.Uri


class WeatherFragment : Fragment() {
    //    private val locationRequestCode = 1000
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var todayAdapter: TodayAdapter
    private lateinit var weeklyAdapter: WeeklyAdapter
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentWeatherBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_weather, container, false
        )

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(activity as AppCompatActivity)





        binding.lifecycleOwner = this
        binding.weatherViewModel = viewModel

        todayAdapter = TodayAdapter()
        binding.rvToday.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvToday.adapter = todayAdapter


        binding.rvWeekly.layoutManager = LinearLayoutManager(requireContext())
        weeklyAdapter = WeeklyAdapter()
        binding.rvWeekly.adapter = weeklyAdapter

        viewModel.currentModel.observe(viewLifecycleOwner) {
            it?.icon?.let { icon ->
                binding.currentWeatherIconImageView.loadWeatherIcon(icon)
            }
        }

        viewModel.weeklyModels.observe(viewLifecycleOwner) {
            it?.let {
                weeklyAdapter.setWeekly(it)
            }
        }

        viewModel.todayModels.observe(viewLifecycleOwner) {
            it?.let {
                todayAdapter.setTodays(it)
            }
        }

        getCurrentLocation()

        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        println("KarishmaTest requestCode: $requestCode")
        println("KarishmaTest permissions: $permissions")
        println("KarishmaTest grantResults: $grantResults")
        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                Toast.makeText(requireContext(), "Denied", Toast.LENGTH_SHORT).show()
                if (shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION)) {
                    showPermissionRational()
                } else {//Never ask again
                    //Open Settings
                    openSettingsDialog()
                }

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getCurrentLocation() {
        if (checkPermission()) {
            if (isLocationEnable()) {
                fusedLocationClient.lastLocation.addOnCompleteListener(activity as FragmentActivity) { task ->
                    val location: Location? = task.result
                    location?.let {
                        viewModel.setLatLon(it.latitude, it.longitude)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "TURN ON LOCATION", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermission()
        }
    }

    private fun isLocationEnable(): Boolean {
        val locationManager: LocationManager =
            activity?.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun showPermissionRational() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Permission Required")
            setMessage("Location Permission is important to get Weather Information")
            setCancelable(false)
            setPositiveButton("YES") { _, _ ->
                requestPermission()
            }
            setNegativeButton("NO") { _, _ ->
                activity?.finish()
            }
        }.show()
    }

    private fun openSettingsDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Permission Required")
            setMessage("We need Location permission")
            setPositiveButton("GO TO SETTINGS") { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                intent.data = Uri.parse("package:" + activity?.packageName)
                startActivity(intent)
            }
            setNegativeButton("NO") { _, _ ->
                activity?.finish()
            }
        }.show()
    }

    private fun requestPermission() {
        requestPermissions(
            arrayOf(
                ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }
}
