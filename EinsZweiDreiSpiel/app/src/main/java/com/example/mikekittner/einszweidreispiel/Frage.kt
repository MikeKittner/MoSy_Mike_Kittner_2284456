package com.example.mikekittner.einszweidreispiel

import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.example.mikekittner.einszweidreispiel.R



class Frage//public Frage(String f, String o1, String o2, String o3, String o4, int l){
(val frage: String, val option1: String, val option2: String, val option3: String, //private String option3;
 private val lösung: Int)//option3=o3;
{


    //Frage und Antworten setzen
    fun anzeigen(quiz: AppCompatActivity) {
        (quiz.findViewById(R.id.frage) as TextView).text = frage
        (quiz.findViewById(R.id.antwort1Text) as TextView).text = "1: $option1"
        (quiz.findViewById(R.id.antwort2Text) as TextView).text = "2: $option2"
        (quiz.findViewById(R.id.antwort3Text) as TextView).text = "3: $option3"

    }

    //Überprüfung der Antwort
    fun richtig(ausgewaehlt: Int): Boolean {
        return ausgewaehlt == this.lösung
    }
}
