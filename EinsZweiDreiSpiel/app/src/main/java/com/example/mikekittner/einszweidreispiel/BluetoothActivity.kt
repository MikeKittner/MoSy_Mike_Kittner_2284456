package com.example.mikekittner.einszweidreispiel

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.mikekittner.einszweidreispiel.R


class BluetoothActivity : AppCompatActivity() {

    internal lateinit var mStatusBluetooth: TextView
    internal lateinit var mPairedDevices: TextView
    internal lateinit var mBlueToothLogo: ImageView
    internal lateinit var mOnBtn: Button
    internal lateinit var mOffBtn: Button
    internal lateinit var mDiscoverBtn: Button
    internal lateinit var mPairedBtn: Button

    internal var mBluetoothAdapter: BluetoothAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        mStatusBluetooth = findViewById(R.id.statusBluetooth) as TextView
        mPairedDevices = findViewById(R.id.pairedDevices) as TextView
        mBlueToothLogo = findViewById(R.id.bluetoothLogo) as ImageView
        mOnBtn = findViewById(R.id.onBtn) as Button
        mOffBtn = findViewById(R.id.offBtn) as Button
        mDiscoverBtn = findViewById(R.id.discoverableBtn) as Button
        mPairedBtn = findViewById(R.id.pairedBtn) as Button

        //adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        //prüfen ob Bluetooth verfügbar ist
        if (mBluetoothAdapter == null) {
            mStatusBluetooth.text = "Bluetooth ist nicht verfübar"
        } else {
            mStatusBluetooth.text = "Bluetooth ist verfübar"
        }

        //image für ein und aus setzen, je nach Zustand
        if (mBluetoothAdapter!!.isEnabled) {
            mBlueToothLogo.setImageResource(R.drawable.ic_action_on)
        } else {
            mBlueToothLogo.setImageResource(R.drawable.ic_action_off)
        }

        //Bluetooth einschalten
        mOnBtn.setOnClickListener {
            if (!mBluetoothAdapter!!.isEnabled) {
                showToast("Bitte Bluetooth einschalten")
                //intent to on Bluetooth
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                startActivityForResult(intent, REQUEST_ENABLE_BT)
            } else {
                showToast("Bluetooth ist aktiviert")
            }
        }

        //Gerät sichtbar machen
        mDiscoverBtn.setOnClickListener {
            if (!mBluetoothAdapter!!.isDiscovering) {
                showToast("Making your Device Discoverable")
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                startActivityForResult(intent, REQUEST_Discover_BT)
            }
            else{
                showToast("Your Device is Discoverable")
            }
        }

        //Bluetooth ausschalten
        mOffBtn.setOnClickListener {
            if (mBluetoothAdapter!!.isEnabled) {
                mBluetoothAdapter!!.disable()
                showToast("Bluetooth wird ausgeschaltet")
                mBlueToothLogo.setImageResource(R.drawable.ic_action_off)
            } else {
                showToast("Bluetooth ist bereits ausgeschaltet")
            }
        }

        //Gekoppelte Geräte anzeigen
        mPairedBtn.setOnClickListener {
            if (mBluetoothAdapter!!.isEnabled) {
                mPairedDevices.text = "Paired Devices"
                val devices = mBluetoothAdapter!!.bondedDevices
                for (device in devices) {
                    mPairedDevices.append("\nDevice" + device.name + "," + device)
                }
            } else {
                //Bluetooth is ausgeschaltet, also sind keine Geräte zu sehen
                showToast("Schalte Bluetooth ein um Geräte zu sehen")
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            REQUEST_ENABLE_BT -> if (resultCode == Activity.RESULT_OK) {
                //Bluetooth ist an
                mBlueToothLogo.setImageResource(R.drawable.ic_action_on)
                showToast("Bluetooth ist aktiviert")
            } else {
                //mBlueToothLogo.setImageResource(R.drawable.ic_action_off);
                showToast("Bluetooth ist deaktiviert")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    //toast message function
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {

        private val REQUEST_ENABLE_BT = 0
        private val REQUEST_Discover_BT = 1
    }
}
