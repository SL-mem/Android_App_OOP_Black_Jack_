package com.example.blackjack

import kotlin.math.max

class hand(val main_joueur : MutableList<Cartes> = mutableListOf()) {


    /*
    Fonction qui calcule la main du joueur/croupier
    */
    fun calc_hand(): Int {
        var somme = 0
        for (cartes in main_joueur) {
            somme += cartes.valeur
        }
        return somme
    }

    /*
    Fonction qui ajoute une carte à la main du joueur/croupier
    */
    fun add_card(carte: Cartes) {
        main_joueur.add(carte)
    }

    /*
    Fonction qui calcule l'as en fonction de ce qui est favorable pour le joueur
    */
    fun verif_as() {

        for (cartes in main_joueur) {
            if (cartes.valeur == 11) {
                if (calc_hand() > 21) {
                    cartes.valeur = 1
                }
            }
        }
    }

}