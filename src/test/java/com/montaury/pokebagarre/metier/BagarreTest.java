package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.erreurs.ErreurRecuperationPokemon;
import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BagarreTest {

    /*
    - verifier aver le premier  nom pokemon null
    - verifier aver le second  nom pokemon null
    - verifier aver le second  nom pokemon chaine vide
    - verifier aver le second  nom pokemon chaine vide
    - verifier queles deux nom pokemeon soi parreille
    - verifier si le premier nom est un vrai pokemon
    - verifier si le seconde nom est un vrai pokemon
    - verifier que bien tou le pokemen 1 est renvoyer si il gagne
    - verifier que bien tou le pokemen 1 est renvoyer si il gagne
     */
    // nom fonction cas + consequence
    @Test
    void le_premier_nom_est_null_devrait_renvoyer_une_erreur(){
        //GIVEN
        Bagarre bagarre = new Bagarre();
        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(null, "pikachu"));
        // THEN
        assertThat(thrown).hasMessage("Le premier pokemon n'est pas renseigne");


}
    @Test
    void le_second_nom_est_null_devrait_renvoyer_une_erreur(){
        //GIVEN
        Bagarre bagarre = new Bagarre();
        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer("pikachu", null));
        // THEN
        assertThat(thrown).hasMessage("Le second pokemon n'est pas renseigne");
    }
    @Test
    void le_premier_nom_est_une_chaine_vide_devrait_renvoyer_une_erreur(){
        //GIVEN
        Bagarre bagarre = new Bagarre();
        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer("", "pikachu"));
        // THEN
        assertThat(thrown).hasMessage("Le premier pokemon n'est pas renseigne");



    }
    @Test
    void le_second_nom_est_une_chaine_vide_devrait_renvoyer_une_erreur(){
        //GIVEN
        Bagarre bagarre = new Bagarre();
        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer("pikachu", ""));
        // THEN
        assertThat(thrown).hasMessage("Le second pokemon n'est pas renseigne");
    }
    @Test
    void les_deux_nom_sont_egaux_devrait_renvoyer_une_erreur(){
        //GIVEN
        Bagarre bagarre = new Bagarre();
        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer("pikachu", "pikachu"));
        // THEN
        assertThat(thrown).hasMessage("Impossible de faire se bagarrer un pokemon avec lui-meme");
    }
    @Test
    void le_premier_nom_n_est_pas_un_vrai_nom_de_pokemon (){
        //GIVEN
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Bagarre bagarre = new Bagarre(fausseApi);
        // WHEN
        when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachu", "url1", new Stats(1, 2))));
        when(fausseApi.recupererParNom("tomate"))
                .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon("tomate")));
        var futurV = bagarre.demarrer("tomate", "pikachu");
        // THEN
        assertThat(futurV)
                .failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur 'tomate'") ;
    }
    @Test
    void le_segonde_nom_n_est_pas_un_vrai_nom_de_pokemon(){
        //GIVEN
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Bagarre bagarre = new Bagarre(fausseApi);
        // WHEN
         when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachu", "url1", new Stats(1, 2))));
         when(fausseApi.recupererParNom("tomate"))
                .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon("tomate")));

         var futurV =  bagarre.demarrer("pikachu","tomate");

        // THEN
        assertThat(futurV)
                .failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur 'tomate'") ;

    }

    @Test
    void derait_renvoir_un_message_erreur_si_le_pokemon_revoie_n_est_pas_levainquer  (){
        //GIVEN
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Bagarre bagarre = new Bagarre(fausseApi);
        // WHEN
        when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachu", "url1", new Stats(1, 2))));
        when(fausseApi.recupererParNom("tomate"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("tomate", "url1", new Stats(5, 2))));

        var futurVainqueur =  bagarre.demarrer("pikachu","tomate");

        // THEN
        assertThat(futurVainqueur)
                .succeedsWithin(Duration.ofSeconds(2))
                .satisfies (pokemon -> {
                            assertThat(pokemon.getNom())
                                    .isEqualTo("tomate");
                            assertThat(pokemon.getStats().getAttaque()).isEqualTo(5);
                            assertThat(pokemon.getStats().getDefense()).isEqualTo(2);
                        }
                ) ;

    }
@Test
void derait_renvoir_un_message_erreur_si_le_pokemon_revoie_n_est_pas_levainquer_desiem_pokemon  (){
    //GIVEN
    var fausseApi = Mockito.mock(PokeBuildApi.class);
    Bagarre bagarre = new Bagarre(fausseApi);
    // WHEN
    when(fausseApi.recupererParNom("pikachu"))
            .thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachu", "url1", new Stats(1, 2))));
    when(fausseApi.recupererParNom("tomate"))
            .thenReturn(CompletableFuture.completedFuture(new Pokemon("tomate", "url1", new Stats(5, 2))));

    var futurVainqueur =  bagarre.demarrer("tomate","pikachu");

    // THEN
    assertThat(futurVainqueur)
            .succeedsWithin(Duration.ofSeconds(2))
            .satisfies (pokemon -> {
                        assertThat(pokemon.getNom())
                                .isEqualTo("tomate");
                        assertThat(pokemon.getStats().getAttaque()).isEqualTo(5);
                        assertThat(pokemon.getStats().getDefense()).isEqualTo(2);
                    }
            ) ;

}

}