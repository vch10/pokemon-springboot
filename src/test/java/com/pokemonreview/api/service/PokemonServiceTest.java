package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.service.impl.PokemonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {
    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Test
    public void PokemonService_CreatePokemon_ReturnsPokemonDto(){
        Pokemon pokemon = Pokemon.builder()
                .name("Picachu")
                .type("Electric")
                .build();

        PokemonDto pokemonDto = PokemonDto.builder()
                .name("Picachu")
                .type("Electric")
                .build();

        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto savedPokemon = pokemonService.createPokemon(pokemonDto);

        Assertions.assertNotNull(savedPokemon);
    }

    @Test
    public void PokemonService_GetAllPokemon_ReturnsResponseDto(){
        PokemonResponse pokemonResponse = Mockito.mock(PokemonResponse.class);
        Page<Pokemon> pokemons = Mockito.mock(Page.class);

        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);

        PokemonResponse savedPokemon = pokemonService.getAllPokemon(1,10);

        Assertions.assertNotNull(savedPokemon);

    }

    @Test
    public void PokemonService_updatePokemon_ReturnsPokemonDto(){
        Pokemon pokemon = Pokemon.builder()
                .name("Picachu")
                .type("Electric")
                .build();

        PokemonDto pokemonDto = PokemonDto.builder()
                .name("Picachu")
                .type("Electric")
                .build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));
        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto savedPokemon = pokemonService.updatePokemon(pokemonDto,1 );

        Assertions.assertNotNull(savedPokemon);
    }

    @Test
    public void PokemonService_deletePokemon_ReturnsPokemonDto(){
        Pokemon pokemon = Pokemon.builder()
                .name("Picachu")
                .type("Electric")
                .build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));
        assertAll(()-> pokemonService.deletePokemonId(1));
    }
}
