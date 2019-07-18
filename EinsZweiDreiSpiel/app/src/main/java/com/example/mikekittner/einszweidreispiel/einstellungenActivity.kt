package com.example.mikekittner.einszweidreispiel

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast



class einstellungenActivity : AppCompatActivity(), View.OnClickListener, OnCheckedChangeListener {

    private var iNettAdresseString: String? = null

    private var iNettAdresseString2: String? = null

    var anzahlSpieler = 0
        private set

    var anzahlRunden = 3
        private set

    var isMasterHandy = false


    var ipAdresse: String? = ""

    internal lateinit var anzahlRundenEditText: EditText // = (EditText) findViewById(R.id.anzahlRundenEditText);
    internal lateinit var ipAdresseEditText: EditText
    internal lateinit var spielerAnzahl1: RadioButton
    internal lateinit var spielerAnzahl2: RadioButton
    internal lateinit var spielerAnzahl3: RadioButton
    internal lateinit var speichernButton: Button
    internal lateinit var masterHandySwitch: Switch
    internal lateinit var iNettAdresse: TextView

    fun setiNettAdresseString2(iNettAdresseString2: String) {
        this.iNettAdresseString2 = iNettAdresseString2
    }


    fun setiNettAdresse(iNettAdresse: TextView, text: String) {
        this.iNettAdresse.text = text
    }


    fun getiNettAdresse(): TextView {
        return iNettAdresse
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_einstellungen)

        //auslesen der Werte aus ShredPreferences
        val mySPR = getSharedPreferences("MeineEinstellungen", 0)
        anzahlSpieler = mySPR.getInt("spieler", 0)
        anzahlRunden = mySPR.getInt("runden", 0)
        ipAdresse = mySPR.getString("ipAdresse", null)
        isMasterHandy = mySPR.getBoolean("masterHandy", false)
        iNettAdresseString = mySPR.getString("iNettAdresse", null)
        //masterHandy=true;


        anzahlRundenEditText = findViewById(R.id.anzahlRundenEditText) as EditText
        anzahlRundenEditText.setText("" + anzahlRunden)

        ipAdresseEditText = findViewById(R.id.ipAdresseEditText) as EditText
        //ipAdresseEditText.setText("" + ipAdresse!!)
        ipAdresseEditText.setText("" + ipAdresse)

        iNettAdresse = findViewById(R.id.iNetAdresseTextView) as TextView
        iNettAdresse.text = iNettAdresseString2


        spielerAnzahl1 = findViewById(R.id.spielerAnzahl1Button) as RadioButton
        spielerAnzahl2 = findViewById(R.id.spielerAnzahl2Button) as RadioButton
        spielerAnzahl3 = findViewById(R.id.spielerAnzahl3Button) as RadioButton

        if (anzahlSpieler == 1) {
            spielerAnzahl1.isChecked = true
        } else if (anzahlSpieler == 2) {
            spielerAnzahl2.isChecked = true
        } else if (anzahlSpieler == 3) {
            spielerAnzahl3.isChecked = true
        }


        speichernButton = findViewById(R.id.speichernButton) as Button
        speichernButton.setOnClickListener(this as View.OnClickListener)

        masterHandySwitch = findViewById(R.id.masterHandyswitch) as Switch
        masterHandySwitch.isChecked = isMasterHandy

        masterHandySwitch.setOnCheckedChangeListener(this)

    }


    @SuppressLint("ResourceType")
    override fun onClick(v: View) {

        when (v.id) {

            //Einstellungen speichern
            R.id.speichernButton -> {
                val anzahlRundenNeu = Integer.parseInt(anzahlRundenEditText.text.toString())
                if (anzahlRundenNeu > 10 || anzahlRundenNeu < 1) {
                    Toast.makeText(this, "Mindestens 1 Runde und höchstens 10 Runden", Toast.LENGTH_LONG).show()
                } else {


                    if (spielerAnzahl1.isChecked) {
                        anzahlSpieler = 1
                    } else if (spielerAnzahl2.isChecked) {
                        anzahlSpieler = 2
                    } else if (spielerAnzahl3.isChecked) {
                        anzahlSpieler = 3
                    }


                    anzahlRunden = Integer.parseInt(anzahlRundenEditText.text.toString())
                    ipAdresse = "" + ipAdresseEditText.text.toString()


                    anzahlRundenEditText.setText(" $anzahlRunden")

                    //Werte in SharedPreferences speichern
                    val mySPR = getSharedPreferences("MeineEinstellungen", 0)
                    val meinEditor = mySPR.edit()

                    meinEditor.putInt("runden", anzahlRunden)
                    meinEditor.putInt("spieler", anzahlSpieler)
                    meinEditor.putString("ipAdresse", ipAdresse)
                    meinEditor.putBoolean("masterHandy", isMasterHandy)
                    //meinEditor.putString("iNettAdresse",iNettAdresseString);
                    //meinEditor.putBoolean("masterHandy",true);


                    meinEditor.commit()

                    Toast.makeText(this, "" + isMasterHandy, Toast.LENGTH_LONG).show()

                    val intent1= Intent(this, MainActivity::class.java)
                    startActivity(intent1)
                }
            }
        }

    }


    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            isMasterHandy = true
            Toast.makeText(this, "" + isMasterHandy, Toast.LENGTH_LONG).show()
        } else {
            isMasterHandy = false
            Toast.makeText(this, "" + isMasterHandy, Toast.LENGTH_LONG).show()
        }
    }


}
