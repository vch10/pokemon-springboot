package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.Review;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.repository.ReviewRepository;
import com.pokemonreview.api.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PokemonRepository pokemonRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Pokemon pokemon;
    private PokemonDto pokemonDto;
    private Review review;
    private ReviewDto reviewDto;

    @BeforeEach
    public void init(){
         pokemon = Pokemon.builder().name("Picachu").type("Electric").build();
         pokemonDto = PokemonDto.builder().name("Picachu").type("Electric").build();
         review = Review.builder().title("title").content("content").stars(3).build();
         reviewDto = ReviewDto.builder().title("title").content("content").stars(3).build();
    }


    @Test
    public void ReviewService_CreateReview_ReturnReviewDto(){

        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto savedReview = reviewService.createReview(pokemon.getId(),reviewDto);

        Assertions.assertNotNull(savedReview);
    }

    @Test
    public void ReviewService_GetReviewsByPokemonId_ReturnListOfReviews(){
        when(reviewRepository.findByPokemonId(pokemon.getId())).thenReturn(Arrays.asList(review));

        List<ReviewDto> reviewDtoList = reviewService.getReviewsByPokemonId(pokemon.getId());

        Assertions.assertNotNull(reviewDtoList);

    }

    @Test
    public void ReviewService_GetReviewById_ReturnReviewDto(){
        review.setPokemon(pokemon);
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));

        ReviewDto expectedReview = reviewService.getReviewById(pokemon.getId(),review.getId());

        Assertions.assertNotNull(expectedReview);
    }

    @Test
    public void ReviewService_UpdateReview_ReturnReviewDto(){
        pokemon.setReviews(Arrays.asList(review));
        review.setPokemon(pokemon);
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto updatedReview = reviewService.updateReview(pokemon.getId(),review.getId(),reviewDto);

        Assertions.assertNotNull(updatedReview);
    }@Test
    public void ReviewService_DeleteReview_ReturnVoid(){
        pokemon.setReviews(Arrays.asList(review));
        review.setPokemon(pokemon);
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));

        Assertions.assertAll(()->reviewService.deleteReview(pokemon.getId(), review.getId()));
    }

}
