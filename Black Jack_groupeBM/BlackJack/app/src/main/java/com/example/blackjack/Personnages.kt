package com.example.blackjack

import java.util.*


abstract class Personnages(var name : String?, val sexe : Boolean, val hand : hand) {

    /*
    Une fois que le personnage a tiré, la fonction ajoute la carte dans la main du personnage.
    */
    fun tirer(nouvelle_carte : Cartes){
        hand.add_card(nouvelle_carte)
    }
    /*
    La fonction retourne la somme de la main
    */
    fun calculer_hand() : Int{
        val somme_main = hand.calc_hand()
        return  somme_main
    }
    /*
    La fonction retourne une liste mutable contenant la main du personnage.
    */
    fun retourner_main(): MutableList<Cartes>{
        return hand.main_joueur
    }
    /*
    Fonction qui vérifie la valeur de l'as (1 ou 11) dans une main selon la somme totale de celle-ci
    */
    fun verif_asjc(){
        hand.verif_as()
    }
}

