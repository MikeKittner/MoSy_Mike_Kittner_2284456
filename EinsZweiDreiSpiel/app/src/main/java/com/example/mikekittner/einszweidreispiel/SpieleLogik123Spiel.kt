package com.example.mikekittner.einszweidreispiel

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.mikekittner.einszweidreispiel.R

import java.io.IOException
import java.io.PrintWriter
import java.net.InetAddress
import java.net.Socket
import java.sql.DriverManager.println
import java.util.Scanner

class SpieleLogik123Spiel(var anzahlFragen: Int, var anzahlSpieler: Int) {

    internal var fragen: Array<Frage>
    var aktFrage: Int = 0
    var endScore: Long = 0
    private val played: Int = 0
    //private int richtigeAntworten=0;
    //private int falscheAntworten=0;
    var ipAdresse = ""
    var inetAdresse = ""
    private var inetAddressHandy: InetAddress? = null

    var richtigeAntworten = 0
        private set
    var falscheAntworten = 0
        private set

    var isMasterHandySpiel: Boolean = false


    fun setRichtigeAntworten(richtigeAntworten: Double) {
        Neuesspiel.richtigeAntworten = richtigeAntworten
    }

    fun setFalscheAntworten(falscheAntworten: Double) {
        Neuesspiel.falscheAntworten = falscheAntworten
    }

    //Fragen einfügen
    init {

        fragen = arrayOf(

                Frage("Was bedeutet HAW ?", "Hamburg am Wasser", "Hochschule für Angewandte Wissenschaften", "Hamburgs angewandten Wissenschaften", 2),

                Frage("Was ist die HAW ?", "Eine Hochschule", "Eine Schule", "Ein Kindergarten", 1),


                Frage("Wie viele Studenten gibt es an der HAW-Finkenau ?" , "Weniger als 100", "100 bis 200", "Mehr als 200", 3),

                Frage( "Welche U-Bahn Station befindet sich in der Nähe ?" , "Mundsburg", "Altona", "Wandsbek", 1),

                Frage( "Was bedeutet die Abkürzung MS ?" , "Mathe Stunde", "Media Systems ", "Medien Speicher", 2),

                Frage("Wie viele Semester gibt es pro Jahr ?" , "Eins", "Zwei", "Drei", 2),

                Frage("Was war früher im Altbau der HAW-Finkenau ?" , "Büros ", "Geburtsklinik", "Kindergarten", 2),

                Frage("Wie viele Semester hat der Studiengang Media Systems ?" , "Sieben", "Sechs", "Acht", 2),

                Frage("In welchem Gebäude befindet sich die Mensa ?" , "Neubau", "Altbau", "Ausserhalb des Campus", 1),

                Frage("Welcher der Begriffe ist eine Programmiersprache ?" , "Komat", "Kotlin", "Kamit", 2),

                Frage("Wie heißt der Hörsaal der HAW-Finkenau ?" , "Kleist", "Ditze", "Bach", 2)


        )

    }

    //Antwort auswerten
    fun auswerten(antwortNummer: Int, quiz: AppCompatActivity) {
        if (!fragen[aktFrage].richtig(antwortNummer)) {
            //falsch beantwortet
            falscheAntworten++
            if (aktFrage.toInt() == 0) {
                Toast.makeText(quiz, "Boosted Animal, versuch nochmal!", Toast.LENGTH_LONG).show()
            } else {
                val fragen = if (aktFrage.toInt() == 0) " Frage" else " Fragen "
                val str = "Sie haben " + (aktFrage + 1) + fragen +
                        " in Folge richtig beantwortet und eine Punkteanzahl von " + endScore + " erreicht"
                Toast.makeText(quiz, str, Toast.LENGTH_SHORT).show()


            }
            //Restart-Button anzeigen
            restartButtonZeigen(quiz)
        } else {
            richtigeAntworten++
            endScore += 50
            aktFrage++
            Toast.makeText(quiz, "Richtig $endScore Punkte",
                    Toast.LENGTH_SHORT).show()
            if (aktFrage < anzahlFragen) {
                // weiter mit der nächsten Frage
                updateFortschritt(quiz)

                fragen[aktFrage].anzeigen(quiz)
            } else { // Ende erreicht

                val str = "Super, Sie haben alles richtig beantwortet und eine Punkteanzahl von $endScore erreicht"
                Toast.makeText(quiz, str, Toast.LENGTH_SHORT).show()


                // Restart-Button zeigen
                //restartButtonZeigen(quiz);
            }
        }

    }

    //Antwort abgeben und prüfen ob die Anzahl der Runden erreicht wurde
    fun antwortAbgeben(antwortNummer: Int, quiz: AppCompatActivity): Boolean {
        if (aktFrage <= anzahlFragen) {
            if (!fragen[aktFrage].richtig(antwortNummer)) {
                //falsch beantwortet

                falscheAntworten += 1
                aktFrage++
                Toast.makeText(quiz, "Falsche Antworten: $falscheAntworten", Toast.LENGTH_SHORT).show()

            } else {
                richtigeAntworten += 1
                aktFrage++
                Toast.makeText(quiz, "Richtige Antworten: $richtigeAntworten", Toast.LENGTH_SHORT).show()
            }

            if (aktFrage < anzahlFragen) {
                // weiter mit der nächsten Frage
                updateFortschritt(quiz)
                fragen[aktFrage].anzeigen(quiz)
                return false
            } else { // Ende erreicht
                val str = "richtige Antwort:$richtigeAntworten    Falsche Antworten:$falscheAntworten"
                Toast.makeText(quiz, str, Toast.LENGTH_SHORT).show()


                // Restart-Button zeigen
                //restartButtonZeigen(quiz);
            }

            if (aktFrage.toInt() == anzahlFragen) {
                return true
            }


        }
        return false
    }

    //Fortschritt Balken aktualisieren
    public fun updateFortschritt(quiz: AppCompatActivity) {
        val pb = quiz.findViewById(R.id.progressBar) as ProgressBar
        val text = quiz.findViewById(R.id.tv_progress_horizontal) as TextView
        pb.progress = aktFrage + 1
        text.text = "Frage " + (aktFrage + 1) + " von " + anzahlFragen
    }

    //Wurde entfernt
    private fun restartButtonZeigen(quiz: AppCompatActivity) {
        //Button deaktivieren
        //buttonsAktivieren(quiz,false);
        val layout = quiz.findViewById(R.id.neuesSpiel) as LinearLayout
        val neuStart = Button(quiz)
        neuStart.setBackgroundColor(Color.argb(255, 201, 86, 255))
        val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        neuStart.layoutParams = params
        neuStart.text = "Hier klicken für Neustart"
        layout.addView(neuStart)
        neuStart.setOnClickListener {
            //buttonsAktivieren(quiz,true);
            layout.removeView(neuStart)
            aktFrage = 0
            updateFortschritt(quiz)
            fragen[aktFrage].anzeigen(quiz)
        }

    }


    //Daten per Lan übertragen
    fun testAbgeben() {

        val runnable = Runnable {
            try {
                val socket: Socket

                //String ip = "141.22.93.43";
                //String ip = "192.168.2.101";
                val ip = ipAdresse

                socket = Socket(ip, 3445)

                inetAddressHandy = socket.localAddress
                inetAdresse = inetAddressHandy.toString()
                //System.out.println("hhhhhhhhhh"+String.valueOf(inetAddressHandy));
                val einstellungen1 = einstellungenActivity()
                //SharedPreferences mySPR = getSharedPreferences("MeineEinstellungen",0);

                einstellungen1.setiNettAdresseString2(inetAdresse)
                println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk$inetAdresse")
                //SharedPreferences.Editor meinEditor = mySPR.edit();
                //meinEditor.putString("iNettAdresse",inetAdresse);
                //meinEditor.putString("iNettAdresse",("555.544.5"));
                //einstellungen1.getMeinEditor2().commit();
                //System.out.println("yyyyyyyyyyyyyyyyyyyyyyyy"+String.valueOf(spiel.getInetAdresse()));

                //SharedPreferences mySPR = getSharedPreferences("MeineEinstellungen",0);
                //SharedPreferences.Editor meinEditor = mySPR.edit();
                //meinEditor.putString("iNettAdresse",String.valueOf(inetAddressHandy);
                //System.out.println("yyyyyyyyyyyyyyyyyyyyyyyy"+String.valueOf(inetAddressHandy));

                //inetAdresse=String.valueOf(socket.getInetAddress());
                println(ip)
                var printWriter: PrintWriter? = null
                printWriter = PrintWriter(socket.getOutputStream())
                printWriter.println("\n\n\n")
                printWriter.flush()
                if (isMasterHandySpiel) {
                    printWriter.println("Master Handy: $isMasterHandySpiel")
                    printWriter.flush()
                    printWriter.println("Anzahl Spieler: $anzahlSpieler")
                    printWriter.flush()
                    printWriter.println("Anzahl Fragen: $anzahlFragen")
                    printWriter.flush()
                    printWriter.println("Aktuelle Runde: " + (aktFrage + 1))
                    printWriter.flush()


                    printWriter.println("Richtige Antworten: $richtigeAntworten")
                    printWriter.flush()
                    printWriter.println("Falsche Antworten: $falscheAntworten\n")
                    printWriter.flush()

                    printWriter.println("" + fragen[aktFrage].frage)
                    printWriter.flush()
                    printWriter.println("1: " + fragen[aktFrage].option1)
                    printWriter.flush()
                    printWriter.println("2: " + fragen[aktFrage].option2)
                    printWriter.flush()
                    printWriter.println("3: " + fragen[aktFrage].option3)
                    printWriter.flush()
                } else {
                    printWriter.println("Handy")
                    printWriter.flush()


                }


                val scanner = Scanner(socket.getInputStream())
                val text = scanner.nextLine()
                println("Antwort vom Server: $text")


                scanner.close()
                printWriter.close()
                socket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        val thread = Thread(runnable)
        thread.start()

    }


}
