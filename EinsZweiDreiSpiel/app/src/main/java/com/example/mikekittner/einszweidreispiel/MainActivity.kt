package com.example.mikekittner.einszweidreispiel

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.mikekittner.einszweidreispiel.R

import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)


        val button1 = findViewById(R.id.neuesspielButton) as Button
        button1.setOnClickListener(this as View.OnClickListener)
        val button2 = findViewById(R.id.einstellungenButton) as Button
        button2.setOnClickListener(this as View.OnClickListener)
        val button3 = findViewById(R.id.bluetoothEinstellungenButton) as Button
        button3.setOnClickListener(this as View.OnClickListener)
        val button4 = findViewById(R.id.anleitungButton) as Button
        button4.setOnClickListener(this as View.OnClickListener)
        val button5 = findViewById(R.id.auswertungButton) as Button
        button5.setOnClickListener(this as View.OnClickListener)


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.neuesspielButton -> {

                val intent1 = Intent(this@MainActivity, Neuesspiel::class.java)
                startActivity(intent1)
            }

            R.id.einstellungenButton -> {

                val intent2 = Intent(this@MainActivity, einstellungenActivity::class.java)
                startActivity(intent2)
            }


            R.id.bluetoothEinstellungenButton -> {

                val intent3 = Intent(this@MainActivity, BluetoothActivity::class.java)
                startActivity(intent3)
            }


            R.id.anleitungButton -> {

                val intent4 = Intent(this@MainActivity, AnleitungActivity::class.java)
                startActivity(intent4)
            }

            R.id.auswertungButton -> {

                val intent5 = Intent(this@MainActivity, AuswertungActivity::class.java)
                startActivity(intent5)
            }
        }

    }
}
