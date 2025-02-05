package com.example.blackjack

class Deck {
    val mon_deck = creer_deck()

    /*
    Fonction qui crée et retourne une arraylist des 52 cartes du black jack.
    */
    fun creer_deck(): ArrayList<Cartes> {
        val deck = arrayListOf<Cartes>()
        for (valeur in 2..14) {
            var valeur_carte = valeur
            var nom_carte = "$valeur"
            if (valeur > 10 && valeur != 14) {
                nom_carte = when(valeur){
                    11 -> "j"
                    12 -> "q"
                    13 -> "k"
                    else -> "$valeur"
                }
                valeur_carte = 10
            } else if (valeur == 14) {
                nom_carte = "a"
                valeur_carte = 11
            } else {
                valeur_carte = valeur
            }
            for (signe_boucle in 1..4) {
                var couleur = "r"
                if (signe_boucle > 2) {
                    couleur = "n"
                }
                val signe = when (signe_boucle) {
                    1 -> "ca"
                    2 -> "co"
                    3 -> "p"
                    4 -> "t"
                    else -> "pas de signe trouvé"
                }
                deck.add(Cartes(valeur_carte, couleur, signe, "${couleur}_${nom_carte}_$signe"))
            }
        }
        return deck
    }

}