package com.example.mikekittner.einszweidreispiel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.mikekittner.einszweidreispiel.R

import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

import java.io.IOException
import java.io.PrintWriter
import java.net.Socket
import java.sql.DriverManager.println
import java.util.HashMap
import java.util.Scanner



class Neuesspiel : AppCompatActivity(), View.OnClickListener {

    var spiel: SpieleLogik123Spiel? = null
        private set


    internal lateinit var surfaceView: SurfaceView
    internal lateinit var cameraSource: CameraSource
    internal lateinit var txtView: TextView
    internal lateinit var barcodeDetector: BarcodeDetector
    internal val qrCode = arrayOfNulls<String>(1)
    internal var runden: Int = 0
    internal var spieler: Int = 0


    @SuppressLint("WrongViewCast")
    public override fun onCreate(savedInstanceStade: Bundle?) {


        super.onCreate(savedInstanceStade)
        setContentView(R.layout.neuesspiel)

        //Werte aus SharedPreferences auslesen
        val mySPR = getSharedPreferences("MeineEinstellungen", 0)


        spieler = mySPR.getInt("spieler", 0)

        runden = mySPR.getInt("runden", 0)

        spiel = SpieleLogik123Spiel(runden, spieler)
        spiel!!.ipAdresse = mySPR.getString("ipAdresse", "0.0.0.0")
        spiel!!.isMasterHandySpiel = mySPR.getBoolean("masterHandy", false)
        spiel!!.updateFortschritt(this)

        val meinEditor = mySPR.edit()
        meinEditor.putInt("richtigeAntworten", 0)
        meinEditor.putInt("falscheAntworten", 0)
        meinEditor.putString("iNettAdresse", spiel!!.inetAdresse)
        meinEditor.commit()

        spiel!!.testAbgeben()
        println("yyyyyyyyyyyyyyyyyyyyyyyy" + spiel!!.inetAdresse.toString())



        val antwortNr = 1

        val ersteFrage = spiel!!.fragen[0]
        ersteFrage.anzeigen(this)




        //Kameravorschau und QR CODE Scanner
        surfaceView = findViewById(R.id.camerapreview) as SurfaceView




        txtView = findViewById(R.id.txtContent) as TextView

        barcodeDetector = BarcodeDetector.Builder(applicationContext)
                .setBarcodeFormats(Barcode.DATA_MATRIX or Barcode.QR_CODE)
                .build()
        if (!barcodeDetector.isOperational) {
            txtView.text = "Could not set up the detector!"
            return
        }
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {

            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val qrCodes = detections.detectedItems
                if (qrCodes.size() != 0) {
                    txtView.post {
                        val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        vibrator.vibrate(1000)
                        txtView.text = qrCodes.valueAt(0).displayValue
                        qrCode[0] = qrCodes.valueAt(0).displayValue
                    }
                }

            }
        })

        cameraSource = CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(800, 600).build()

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            //)
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    cameraSource.start(holder)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })



        val btn = findViewById(R.id.buttonAbgeben) as Button
        btn.setOnClickListener {

            val qrCodeInt = Integer.parseInt(qrCode[0])
            //Überprüfen ob eine gültige Antwort ausgewählt wurde
            if(qrCodeInt==1 || qrCodeInt==2 || qrCodeInt==3 ) {
                if (spiel!!.antwortAbgeben(qrCodeInt, this@Neuesspiel)) {

                    val mySPR = getSharedPreferences("Spielstand", 0)
                    val meinEditor = mySPR.edit()
                    meinEditor.putInt("richtigeAntworten", spiel!!.richtigeAntworten)
                    meinEditor.putInt("falscheAntworten", spiel!!.falscheAntworten)
                    meinEditor.commit()

                    val intent = Intent(this@Neuesspiel, AuswertungActivity::class.java)
                    startActivity(intent)
                }

                spiel!!.testAbgeben()
            }
            else{
                Toast.makeText(this@Neuesspiel, "Ungültige Antwort", Toast.LENGTH_LONG).show()
            }
        }


    }

    //Nur zum Testen, hat keine Funktion
    override fun onClick(v: View) {
        if (3 == 5) {

            Toast.makeText(this@Neuesspiel, "Abgeben Button", Toast.LENGTH_LONG).show()

            val qrCodeInt = Integer.parseInt(qrCode[0])
            spiel!!.antwortAbgeben(3, this@Neuesspiel)
        }
    }

    companion object {


        var richtigeAntworten: Double = 0.toDouble()
        var falscheAntworten: Double = 0.toDouble()
    }


}
