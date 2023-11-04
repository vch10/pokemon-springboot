package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTests {
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewRepositoryTests(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Test
    public void ReviewRepository_SaveAll_ReturnSavedReview(){
        //Arrange
        Review review = Review.builder().title("title").content("...").stars(3).build();

        //Act
        Review savedReview = reviewRepository.save(review);

        //Assert
        Assertions.assertNotNull(savedReview);

    }

    @Test
    public void ReviewRepository_GetAll_ReturnMoreThenOneReview(){
        //Arrange
        Review review = Review.builder().title("title").content("...").stars(3).build();
        Review review1 = Review.builder().title("title1").content("content").stars(4).build();

        //Act
        reviewRepository.save(review);
        reviewRepository.save(review1);
        List<Review> reviewList = reviewRepository.findAll();

        //Assert
        Assertions.assertFalse(reviewList.isEmpty());
        Assertions.assertSame(reviewList.size(), 2);

    }

    @Test
    public void ReviewRepository_FindById_ReturnSavedReview(){
        //Arrange
        Review review = Review.builder().title("title").content("...").stars(3).build();

        //Act
        reviewRepository.save(review);
        Review findedReview = reviewRepository.findById(review.getId()).get();

        //Assert
        Assertions.assertNotNull(findedReview);
        Assertions.assertNotSame(findedReview.getId(), 0);
    }

    @Test
    public void ReviewRepository_Update_ReturnReview(){
        //Arange
        Review review = Review.builder().title("...").content("...").stars(3).build();

        //Act
        reviewRepository.save(review);
        Review savedReview = reviewRepository.findById(review.getId()).get();
        savedReview.setTitle("title");
        savedReview.setContent("content");
        savedReview.setStars(5);
        Review updatedReview = reviewRepository.save(savedReview);

        //Assert
        //Assertions.assertNotNull(updatedReview);
        Assertions.assertTrue(updatedReview.getId() > 0);

    }
    @Test
    public void ReviewRepository_Delete_ReturnReviewIsEmpty(){
        //Arange
        Review review = Review.builder().title("...").content("...").stars(3).build();

        //Act
        reviewRepository.save(review);
        reviewRepository.deleteById(review.getId());
        Optional<Review> findedReview = reviewRepository.findById(review.getId());


        //Assert
        Assertions.assertTrue(findedReview.isEmpty());
    }


}
