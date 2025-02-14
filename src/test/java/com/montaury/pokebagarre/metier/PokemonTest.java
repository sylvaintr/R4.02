package com.montaury.pokebagarre.metier;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class PokemonTest {

    /*
liste test :
- le premire pokémon extet mais pas l'autre
-le premeier n'exite pas mais le segon exeicte
- les deux pokemones son pareille
- le permer pokemen est plus fort
- le segon  pokemen est plus fort
-les deux pokemen ont la meme attaque
- les deux pokemeon on les meme state

 */


    @Test
    void le_premier_doit_gagner_l_attque_du_premier_supereur(){
        //GIVEN
        Pokemon p1 = new Pokemon("pokemon1","", new Stats(30,30) );
        Pokemon p2 = new Pokemon("pokemon2","",new Stats(20,20) );
        // WHEN
        boolean resultat = p1.estVainqueurContre(p2);

        // THEN
        assertThat(resultat).isTrue();

    }

    @Test
    void le_seconde_doit_gagner_l_attque_du_seconde_supereur(){
        //GIVEN
        Pokemon p1 = new Pokemon("pokemon1","", new Stats(10,10) );
        Pokemon p2 = new Pokemon("pokemon2","",new Stats(20,20) );
        // WHEN
        boolean resultat = p1.estVainqueurContre(p2);

        // THEN
        assertThat(resultat).isFalse();

    }

    @Test
    void les_deux_pokemeons_ont_les_meme_attaque_mais_le_premier_a_plus_de_defance_le_premier_gagne(){
        //GIVEN
        Pokemon p1 = new Pokemon("pokemon1","", new Stats(20,20) );
        Pokemon p2 = new Pokemon("pokemon2","",new Stats(20,10) );
        // WHEN
        boolean resultat = p1.estVainqueurContre(p2);

        // THEN
        assertThat(resultat).isTrue();

    }

    @Test
    void les_deux_pokemeons_ont_les_meme_attaque_mais_le_segonde_a_plus_de_defance_le_segond_gagne (){
        //GIVEN
        Pokemon p1 = new Pokemon("pokemon1","", new Stats(20,10) );
        Pokemon p2 = new Pokemon("pokemon2","",new Stats(20,20) );
        // WHEN
        boolean resultat = p1.estVainqueurContre(p2);

        // THEN
        assertThat(resultat).isFalse();

    }

    @Test
    void les_deux_pokemeons_ont_les_meme_stastistique_le_premier_gagne (){
        //GIVEN
        Pokemon p1 = new Pokemon("pokemon1","", new Stats(20,20) );
        Pokemon p2 = new Pokemon("pokemon2","",new Stats(20,20) );

        // WHEN
        boolean resultat = p1.estVainqueurContre(p2);

        // THEN
        assertThat(resultat).isTrue();

    }




}