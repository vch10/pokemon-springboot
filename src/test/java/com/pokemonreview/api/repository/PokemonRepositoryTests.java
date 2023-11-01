package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTests {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRepository_SaveAll_ReturnSavedPokemon(){
        //Arrange
        Pokemon pokemon = Pokemon.builder().name("Picachu").type("Electric").build();
        //Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        //Assert
        //Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertEquals(pokemon,savedPokemon);
        Assertions.assertTrue(savedPokemon.getId() > 0);
    }

    @Test
    public void PokemonRepository_GetAll_ReturnMoreThanOnePokemon(){
        //Arrange
        Pokemon pokemon = Pokemon.builder().name("Picachu").type("Electric").build();
        Pokemon pokemon2 = Pokemon.builder().name("Picachu2").type("Electric").build();

        //Act
        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);
        List<Pokemon> pokemonList = pokemonRepository.findAll();

        //Assert
        Assertions.assertEquals(pokemonList.size(),2);
    }

    @Test
    public void PokemonRepository_FindById_ReturnPokemon(){
        //Arrange
        Pokemon pokemon = Pokemon.builder().name("Picachu").type("Electric").build();

        //Act
        pokemonRepository.save(pokemon);
        Pokemon findedPokemon = pokemonRepository.findById(pokemon.getId()).get();

        //Assert
        Assertions.assertEquals(findedPokemon, pokemon);
    }
}
