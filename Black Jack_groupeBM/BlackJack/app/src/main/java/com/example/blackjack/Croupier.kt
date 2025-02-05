package com.example.blackjack

class Croupier(name: String?, sexe: Boolean, hand : hand) : Personnages(name,sexe, hand){


/*
En fonction de la main du croupier, il agit en conséquences. Il retourne l'information relative à son prochain tour (continuer de tirer ou passer)
*/
    fun reponse_croupier() : Boolean{   //Sert à définir si le croupier va tirer ou passer
        val score_croupier : Int = calculer_hand()
        if (score_croupier < 16){
            return true
            println("le croupier pioche")
        }
        else{
            return false
        }
    }

}