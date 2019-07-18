package com.example.mikekittner.einszweidreispiel

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


import java.io.IOException
import java.sql.DriverManager.println

class AuswertungActivity : AppCompatActivity() {

    var richtigeAntworten = 0
    var falscheAntworten = 0
    private var ergebnisRichtigeAntworten: Double = 0.toDouble()
    private var ergebnisFalscheAntworten: Double = 0.toDouble()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auswertung)

        //Auslesen der gespeicherten Werte
        val mySPR = getSharedPreferences("Spielstand", 0)
        richtigeAntworten = mySPR.getInt("richtigeAntworten", 0)
        falscheAntworten = mySPR.getInt("falscheAntworten", 0)

        //Berechnung wie viele richtige und falsche Antworten es gibt in Zahlen und Prozent
        ergebnisRichtigeAntworten = (100 / (richtigeAntworten + falscheAntworten) * richtigeAntworten).toDouble()
        ergebnisFalscheAntworten = (100 / (richtigeAntworten + falscheAntworten) * falscheAntworten).toDouble()
        val richtigPunkte = findViewById(R.id.RichtigPunkte) as TextView

        //Übergabe der Werte an die Views
        richtigPunkte.setText(""+richtigeAntworten)

        println(""+richtigeAntworten)
        val falschPunkte = findViewById(R.id.FalschPunkte) as TextView

        falschPunkte.text = "" + falscheAntworten

        val richtigProzent = findViewById(R.id.RichtigProzent) as TextView

        richtigProzent.text = "" + Math.round(ergebnisRichtigeAntworten).toString()

        val falschProzent = findViewById(R.id.FalschProzent) as TextView

        falschProzent.text = "" + Math.round(ergebnisFalscheAntworten)

        val testErgebis = findViewById(R.id.testErgebnis) as TextView



        println("richtig:$richtigeAntworten")

        println("falsch:$falscheAntworten")

        println("richtig:$richtigeAntworten")

        println("falsch:$falscheAntworten")

        //Überprüfung und Ausgabe ob man mindestens 50 Prozent richtig hatte und somit bestanden hat
        if (richtigeAntworten >= falscheAntworten) {
            testErgebis.text = "Der Test hat ergeben, dass Sie mindestens 50 Prozent der Fragen richtig beantwortet haben und somit bestanden hätten."
        } else {
            testErgebis.text = "Der Test hat ergeben, dass Sie weniger als 50 Prozent der Fragen richtig beantwortet haben und somit nicht bestanden hätten."
        }


    }
}

