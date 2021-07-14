package `in`.bitcode.wifi_sensors

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var wifiManager: WifiManager

    @SuppressLint("WifiManagerLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        showConfiguredNetworks()
        manageNetworks()

        //scan wifi networks

        registerReceiver(
            BRWiFi(),
            IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        )

        wifiManager.startScan()

    }

    inner class BRWiFi : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            for (scanRes in wifiManager.scanResults) {
                mt("SSID: ${scanRes.SSID}")
                mt("BSSID: ${scanRes.BSSID}")
                mt("Capabilities: ${scanRes.capabilities}")
            }

        }
    }

    private fun manageNetworks() {

        //wifiManager.enableNetwork(2, true)
        //wifiManager.disableNetwork(1)
        //wifiManager.removeNetwork(1)

        /*var newWifiConfig = WifiConfiguration()
        newWifiConfig.SSID = "bitcode_tech"
        newWifiConfig.BSSID = "F2:A1:1B:45:6D"
        newWifiConfig.wepKeys = arrayOf("password1", "password2")

        var newNetId = wifiManager.addNetwork(newWifiConfig)*/

    }

    @SuppressLint("MissingPermission")
    private fun showConfiguredNetworks() {
        for (wifiConfig in wifiManager.configuredNetworks) {
            mt("Network ID: ${wifiConfig.networkId}")
            mt("BSSID: ${wifiConfig.BSSID}") //2F:1A:3C:D4
            mt("SSID: ${wifiConfig.SSID}") //
            mt("Status: ${wifiConfig.status}") //
            mt("-----------------------------------")
        }
    }


    private fun mt(text: String) {
        Log.e("tag", text)
    }
}