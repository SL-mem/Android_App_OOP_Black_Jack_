package com.example.blackjack
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.interfacejeu.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.max


class jeu : AppCompatActivity() {
    // Variables
    var joueur = MainActivity().joueur
    var croupier = MainActivity().croupier
    val deck = Deck().creer_deck()
    var compteur_croupier = 0
    var compteur_piocher = 0
    var compteur_tour = 0
    var sa_joue = false
    var g_deja_mise = false
    var mise_potentielle = 5
    var doubler_disponible = false
    var passer_disponible = false



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.interfacejeu)

        //Une jauge qui permet au joueur d'insérer sa mise pour jouer
        seekBarMise.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                val chiffre_a_afficher = progress + 5
                mise.text = "Mise : " + "$chiffre_a_afficher" + "$"


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    mise_potentielle = seekBar.progress + 5
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    mise_potentielle  = seekBar.progress + 5
                }
            }

        })

    }




/*-----------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------
 Toutes les méthodes relatives au joueur dans le jeu
-----------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------*/

    /*
    Fonction qui permet de miser. Si le joueur n'a toujours pas misé alors le joueur peut miser dans le jeu cette mise sera stockée dans une variable et sera affichée dans le jeu. Une fois que le joueur a misé, la barre jaugée devient invisible. Deux cartes sont piochées par le joueur et le croupier.
    */
    fun miser(view: View) {
        if (g_deja_mise == false){
            joueur.miser(mise_potentielle)
            MontantAfficher.text = "Montant : ${joueur.wallet}$"
            seekBarMise.setVisibility(View.INVISIBLE)
            sa_joue = true
            doubler_disponible = true
            passer_disponible = true
            for (i in 1..2){
                piocher_joueur()
                tour_croupier(croupier.reponse_croupier())
            }
            g_deja_mise = true
        }
        else{
            Toast.makeText(this, "Vous avez déjà misé", Toast.LENGTH_SHORT).show()
        }
    }

    /*
    Fonction qui double la mise du joueur et tire une carte
     */
    fun doubler(view: View) {
        if(doubler_disponible == true){
            if(mise_potentielle*2 < 1000){
                joueur.doubler_joueur(mise_potentielle)
                piocher_joueur()
                sa_joue = false
                doubler_disponible = false
                tour_croupier_dans_jeu()
                mise.text = "Mise : " + "${joueur.mise}" + "$"
                MontantAfficher.text = "Montant : ${joueur.wallet}$"
                gameOver()

            }
            else{
                Toast.makeText(this, "Vous ne possédez pas assez d'argent", Toast.LENGTH_SHORT).show()
            }
        }

        else{
            Toast.makeText(this, "Il faut d'abord miser", Toast.LENGTH_SHORT).show()
        }
    }



    /*
    Lorsque le joueur passe, c'est le tour du croupier puis le jeu se finit.
     */
    fun passer(view: View) {
        if (passer_disponible == true){
            joueur.passer_joueur(sa_joue)
            println("SA JOUE : $sa_joue")
            sa_joue = false
            passer_disponible = false
            tour_croupier_dans_jeu()
            gameOver()
        }
        else{
            Toast.makeText(this, "Il faut d'abord miser", Toast.LENGTH_SHORT).show()
        }
    }


    /*
    Le joueur pioche une ou plusieurs cartes puis c'est le tour du croupier.
     */
    fun piocher(view: View) {
        if (sa_joue == true){
            piocher_joueur()
            tour_croupier_dans_jeu()
            gameOver()
        }
        else{
            Toast.makeText(this, "Il faut d'abord miser", Toast.LENGTH_SHORT).show()
        }
    }




    /*
    Fonction qui pioche une carte dans le deck et rajoute la carte dans la main du joueur puis l'affiche dans l'interface du jeu.
    */
    fun piocher_joueur(){
        compteur_piocher++
        val nouvelle_carte = deck.random()
        val nom_nouvelle_carte = nouvelle_carte.nom_carte
        val resourceId : Int = resources.getIdentifier(nom_nouvelle_carte, "drawable", this.packageName) // Sert à ce que je puisse, avec le nom de la carte, retrouver le resourceID de l'image correspondante dans le drawable
        val place_carte_a_afficher = place_carte_a_afficher()                                                    // Je définis l'endroit où je vais afficher la carte que je viens de piocher
        joueur.tirer(nouvelle_carte) // J'ajt une carte ds la main de mon joueur
        println("${croupier.reponse_croupier()}")
        place_carte_a_afficher.setImageResource(resourceId)
    }


    /*
    Fonction qui calcule la place ou les cartes du joueur vont être placées sur l'interface du jeu
    */
    fun place_carte_a_afficher() : ImageView{
        var carte_a_afficher = carte_1
        when(compteur_piocher){
            2 -> carte_a_afficher = carte_2
            3 -> carte_a_afficher = carte_3
            4 -> carte_a_afficher = carte_4
        }
        return  carte_a_afficher
    }



/*-----------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------
 Toutes les méthodes relatives au croupier dans le jeu
-----------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------*/


    /*
    Fonction qui montre toute la main du croupier
     */
    fun montrer_main_croupier(){
        val main_croupier = croupier.retourner_main()
        var compteur_boucle = 1
        for (carte in main_croupier){
            var place_carte = carte_croupier_1
            val nom_carte = carte.nom_carte
            val resourceId : Int = resources.getIdentifier(nom_carte, "drawable", this.packageName)
            when(compteur_boucle){
                2 -> place_carte = carte_croupier_2
                3 -> place_carte = carte_croupier_3
                4 -> place_carte = carte_croupier_4
            }
            place_carte.setImageResource(resourceId)
            compteur_boucle++
        }
    }





    /*
    Boucle sur la fonction reponse_croupier, tant que reponse_croupier laisse le croupier jouer, il continue de jouer.
    */
    fun tour_croupier_dans_jeu(){
        while(croupier.reponse_croupier() == true){
            tour_croupier(croupier.reponse_croupier())

        }
    }

    /*
    Fonction qui pioche une carte dans le deck pour la placer dans la main du croupier puis affiche cette carte de dos dans le jeu. La carte en question est stockée pour être révélée à la fin du jeu.
    */
    fun tour_croupier(reponse_croupier : Boolean){
        if(reponse_croupier == true){
            compteur_croupier++
            println("$compteur_croupier")
            val nouvelle_carte = deck.random()
            val nom_nouvelle_carte = nouvelle_carte.nom_carte
            val resourceId : Int = resources.getIdentifier(nom_nouvelle_carte, "drawable", this.packageName) // Sert à ce que je puisse, avec le nom de la carte, retrouver le resourceID de l'image correspondante dans le drawable
            val place_carte_a_afficher_croupier = place_carte_a_afficher_croupier()                                                    // Je définis l'endroit où je vais afficher la carte que je viens de piocher
            println("Nom de cartes est: $nom_nouvelle_carte" )
            println("$nouvelle_carte")
            croupier.tirer(nouvelle_carte) // Ajoute une carte dans la main du croupier
            if (place_carte_a_afficher_croupier != carte_croupier_1){
                place_carte_a_afficher_croupier.setImageResource(R.drawable.dos_carte)
            }
            else{
                place_carte_a_afficher_croupier.setImageResource(resourceId)
            }
            println(croupier.hand)
        }
    }

    /*
    Fonction qui calcule la place ou les cartes du croupier vont être placées sur l'interface du jeu
    */
    fun place_carte_a_afficher_croupier() : ImageView{
        var carte_a_afficher_croupier = carte_croupier_1
        when(compteur_croupier){
            2 -> carte_a_afficher_croupier = carte_croupier_2
            3 -> carte_a_afficher_croupier = carte_croupier_3
            4 -> carte_a_afficher_croupier = carte_croupier_4
        }
        return  carte_a_afficher_croupier
    }


/*-----------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------
 Toutes les méthodes relatives à la fin du jeu et règles : la comparaison des mains du joueur et du croupier/La méthode gameOver
-----------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------*/




    /*
    Permet de comparer les mains du croupier et joueur et retourne la main gagnante
    */
    fun comparer_hand(main1 : hand, main2 : hand) : hand{
        var main_gagnante = main1
        val somme_main1 = main1.calc_hand()
        val somme_main2 = main2.calc_hand()


        if (somme_main1 == somme_main2 || (somme_main1 > 21 && somme_main2 > 21)){
            main_gagnante = hand()
            return main_gagnante // Ce que notre fonction renvoie si les valeurs sont égales
        }
        if(max(somme_main1, somme_main2) == somme_main2){
            if(somme_main2 <= 21){
                main_gagnante = main2
            }

        }
        if (somme_main1 > 21 && somme_main2 < 21){ // main 1 > 21 et main 2 < 21 = > Main 2 gagne
            main_gagnante = main2
        }
        return  main_gagnante
    }



    /*
    Fonction qui vérifie si le jeu est fini. Si la main du joueur/croupier dépasse ou est égale à 21alors le jeu se finit. Si le joueur passe ou double alors le jeu se finit également. Le programme calcule la main du joueur et du croupier pour les comparer et désigner le vainqueur. Selon le vainqueur, un message différent apparaît.
    */
    fun gameOver(){
        joueur.verif_asjc()
        croupier.verif_asjc()
        val main_joueur = joueur.calculer_hand()
        val main_croupier = croupier.calculer_hand()
        println("$main_croupier + $main_joueur")
        if((main_joueur >= 21) || (sa_joue == false) || (main_croupier >= 21)){
            montrer_main_croupier()
            val main_gagnante = comparer_hand(joueur.hand, croupier.hand)
            if(main_gagnante == croupier.hand){
                Toast.makeText(this, "Vous avez perdu", Toast.LENGTH_LONG).show()
            }

            else if (main_gagnante == joueur.hand){
                Toast.makeText(this, "Vous avez gagné ", Toast.LENGTH_LONG).show()
                joueur.gagner()
            }
            else{
                joueur.egalite()
                Toast.makeText(this, "Egalité", Toast.LENGTH_LONG).show()
            }
            Timer().schedule(5000) {
                finish()
                startActivity(intent)
            }
        }
    }


}
