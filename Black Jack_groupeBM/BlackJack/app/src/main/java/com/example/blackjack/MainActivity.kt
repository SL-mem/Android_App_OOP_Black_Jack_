package com.example.blackjack

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_main.*
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_main.*


// Création de ma classe MainActivity
class MainActivity : AppCompatActivity() {

    // Variables
    var sexe : Boolean = true // True = Homme
    lateinit var prenom : String
    val deck = Deck().mon_deck
    var joueur = Joueur(1000, 0, "", sexe, hand(), deck)
    var croupier = Croupier("Philippe", true, hand())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Définit comment le btn sexe va interagir
        btnsexe.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.femaleBtn) {
                sexe = false
                println("Tu es une femme")
            }
            if (checkedId == R.id.maleBtn) {
                sexe = true
                println("Tu es un homme")
            }

        }


        }


    //Fonction qui stocke le prénom du joueur et change l'interface pour envoyer le joueur à l'interface du jeu.
    fun changeActivity(view: View) {
        prenom = name.text.toString()
        intent = Intent(this, jeu :: class.java)
        joueur.mettre_coord(prenom)
        startActivity(intent)
        Toast.makeText(this, "Bienvenue $prenom", Toast.LENGTH_LONG).show()
        println(joueur.name)

    }
    // Fonction qui renvoie à l'interface ou se trouvent les règles du jeu
    fun showrules(view: View) {
        intent = Intent(this, Regles :: class.java)
        startActivity(intent)
    }


}




