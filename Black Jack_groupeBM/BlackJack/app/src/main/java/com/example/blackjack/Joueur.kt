package com.example.blackjack

import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class Joueur(var wallet : Int, var mise : Int, name: String?, sexe: Boolean, hand : hand, val deck : ArrayList<Cartes>) : Personnages(name,sexe, hand) {


    //Fonction qui permet de changer la wallet du joueur et stocke la mise lorsque
    //le joueur mise une certaine somme
    fun miser(nouvelle_mise : Int) {
        changer_mise(nouvelle_mise)
        changer_wallet(nouvelle_mise)
    }
    //Permet de changer la wallet du joueur en soustrayant la mise du joueur
    fun changer_wallet(nouvelle_mise: Int){
        wallet -= nouvelle_mise
    }
    //Stocke la mise du joueur
    fun changer_mise(nouvelle_mise: Int){
        mise += nouvelle_mise
    }

    //Double la mise du joueur
    fun doubler_joueur(mise_pot : Int){
        mise = mise_pot*2
        wallet -= mise_pot
    }

    //retourne si le joueur passe ou pas
    fun passer_joueur(passe : Boolean): Boolean {
        return passe
    }
    //Si le joueur gagne, il faut lui rajouter deux fois sa mise

    fun gagner(){
        wallet += mise*2
    }
    //S'il y a égalité, le joueur reprend sa mise initiale
    fun egalite(){
        wallet += mise
    }
    //Change le prénom du joueur
    fun mettre_coord(nom_a_changer : String){
        name = nom_a_changer
    }




}