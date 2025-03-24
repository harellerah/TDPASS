package com.att.tdp.popcorn_palace;

import com.att.tdp.popcorn_palace.entity.Booking;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.service.BookingService;
import com.att.tdp.popcorn_palace.service.MovieService;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PopcornPalaceApplicationTests {

	@Autowired
	private MovieService movieService;

	@Autowired
	private ShowtimeService showtimeService;

	@Autowired
	private BookingService bookingService;

	@Test
	void testAddMovie() {
		Movie movie = new Movie();
		movie.setTitle("Inception");
		movie.setGenre("Sci-Fi");
		movie.setDuration(148);
		movie.setRating(8.8f);
		movie.setReleaseYear(2010);

		Movie savedMovie = movieService.addMovie(movie);
		assertNotNull(savedMovie.getId());
		assertEquals("Inception", savedMovie.getTitle());
	}

	@Test
	void testUpdateMovie_Success() {
		Movie movie = new Movie();
		movie.setTitle("Inception");
		movie.setGenre("Sci-Fi");
		movie.setDuration(148);
		movie.setRating(8.8f);
		movie.setReleaseYear(2010);
		movieService.addMovie(movie);

		Movie updatedMovie = new Movie();
		updatedMovie.setTitle("Inception Updated");
		updatedMovie.setGenre("Action");
		updatedMovie.setDuration(120);
		updatedMovie.setRating(7.5f);
		updatedMovie.setReleaseYear(2020);

		movieService.updateMovie("Inception", updatedMovie);
		List<Movie> movies = movieService.getAllMovies();
		Movie result = movies.stream()
				.filter(m -> m.getTitle().equals("Inception Updated"))
				.findFirst()
				.orElse(null);
		assertNotNull(result);
		assertEquals("Action", result.getGenre());
	}

	@Test
	void testUpdateMovie_NotFound() {
		Movie updatedMovie = new Movie();
		updatedMovie.setTitle("Nonexistent Movie");
		assertThrows(RuntimeException.class, () -> movieService.updateMovie("Nonexistent", updatedMovie));
	}

	@Test
	void testGetAllMovies() {
		Movie movie = new Movie();
		movie.setTitle("Inception");
		movie.setGenre("Sci-Fi");
		movie.setDuration(148);
		movie.setRating(8.8f);
		movie.setReleaseYear(2010);
		movieService.addMovie(movie);

		List<Movie> movies = movieService.getAllMovies();
		assertFalse(movies.isEmpty());
		assertEquals("Inception", movies.get(0).getTitle());
	}

	@Test
	void testAddShowtime_Success() {
		Movie movie = new Movie();
		movie.setTitle("Inception");
		movie.setGenre("Sci-Fi");
		movie.setDuration(148);
		movie.setRating(8.8f);
		movie.setReleaseYear(2010);
		Movie savedMovie = movieService.addMovie(movie);

		Showtime showtime = new Showtime();
		showtime.setMovieId(savedMovie.getId());
		showtime.setTheater("Sample Theater");
		showtime.setStartTime(LocalDateTime.of(2025, 3, 24, 14, 0));
		showtime.setEndTime(LocalDateTime.of(2025, 3, 24, 16, 0));
		showtime.setPrice(10.0);

		Showtime savedShowtime = showtimeService.addShowtime(showtime);
		assertNotNull(savedShowtime.getId());
		assertEquals("Sample Theater", savedShowtime.getTheater());
	}

	@Test
	void testAddShowtime_Overlap() {
		Movie movie = new Movie();
		movie.setTitle("Inception");
		movie.setGenre("Sci-Fi");
		movie.setDuration(148);
		movie.setRating(8.8f);
		movie.setReleaseYear(2010);
		Movie savedMovie = movieService.addMovie(movie);

		Showtime showtime1 = new Showtime();
		showtime1.setMovieId(savedMovie.getId());
		showtime1.setTheater("Sample Theater");
		showtime1.setStartTime(LocalDateTime.of(2025, 3, 24, 14, 0));
		showtime1.setEndTime(LocalDateTime.of(2025, 3, 24, 16, 0));
		showtime1.setPrice(10.0);
		showtimeService.addShowtime(showtime1);

		Showtime showtime2 = new Showtime();
		showtime2.setMovieId(savedMovie.getId());
		showtime2.setTheater("Sample Theater");
		showtime2.setStartTime(LocalDateTime.of(2025, 3, 24, 15, 0));
		showtime2.setEndTime(LocalDateTime.of(2025, 3, 24, 17, 0));
		showtime2.setPrice(12.0);

		assertThrows(RuntimeException.class, () -> showtimeService.addShowtime(showtime2));
	}

	@Test
	void testAddShowtime_InvalidTime() {
		Showtime showtime = new Showtime();
		showtime.setMovieId(1L);
		showtime.setTheater("Sample Theater");
		showtime.setStartTime(LocalDateTime.of(2025, 3, 24, 16, 0));
		showtime.setEndTime(LocalDateTime.of(2025, 3, 24, 14, 0)); // Start after end

		assertThrows(RuntimeException.class, () -> showtimeService.addShowtime(showtime));
	}

	@Test
	void testGetShowtimeById_Success() {
		Movie movie = new Movie();
		movie.setTitle("Inception");
		movie.setGenre("Sci-Fi");
		movie.setDuration(148);
		movie.setRating(8.8f);
		movie.setReleaseYear(2010);
		Movie savedMovie = movieService.addMovie(movie);

		Showtime showtime = new Showtime();
		showtime.setMovieId(savedMovie.getId());
		showtime.setTheater("Sample Theater");
		showtime.setStartTime(LocalDateTime.of(2025, 3, 24, 14, 0));
		showtime.setEndTime(LocalDateTime.of(2025, 3, 24, 16, 0));
		showtime.setPrice(10.0);
		Showtime savedShowtime = showtimeService.addShowtime(showtime);

		Showtime result = showtimeService.getShowtimeById(savedShowtime.getId());
		assertEquals(savedShowtime.getId(), result.getId());
	}

	@Test
	void testGetShowtimeById_NotFound() {
		assertThrows(RuntimeException.class, () -> showtimeService.getShowtimeById(999L));
	}

	@Test
	void testBookTicket_Success() {
		Movie movie = new Movie();
		movie.setTitle("Inception");
		movie.setGenre("Sci-Fi");
		movie.setDuration(148);
		movie.setRating(8.8f);
		movie.setReleaseYear(2010);
		Movie savedMovie = movieService.addMovie(movie);

		Showtime showtime = new Showtime();
		showtime.setMovieId(savedMovie.getId());
		showtime.setTheater("Sample Theater");
		showtime.setStartTime(LocalDateTime.of(2025, 3, 24, 14, 0));
		showtime.setEndTime(LocalDateTime.of(2025, 3, 24, 16, 0));
		showtime.setPrice(10.0);
		Showtime savedShowtime = showtimeService.addShowtime(showtime);

		Booking booking = new Booking();
		booking.setShowtimeId(savedShowtime.getId());
		booking.setSeatNumber(15);
		booking.setUserId(UUID.fromString("84438967-f68f-4fa0-b620-0f08217e76af"));

		Booking savedBooking = bookingService.bookTicket(booking);
		assertNotNull(savedBooking.getId());
		assertEquals(15, savedBooking.getSeatNumber());
	}

	@Test
	void testBookTicket_DoubleBooking() {
		Movie movie = new Movie();
		movie.setTitle("Inception");
		movie.setGenre("Sci-Fi");
		movie.setDuration(148);
		movie.setRating(8.8f);
		movie.setReleaseYear(2010);
		Movie savedMovie = movieService.addMovie(movie);

		Showtime showtime = new Showtime();
		showtime.setMovieId(savedMovie.getId());
		showtime.setTheater("Sample Theater");
		showtime.setStartTime(LocalDateTime.of(2025, 3, 24, 14, 0));
		showtime.setEndTime(LocalDateTime.of(2025, 3, 24, 16, 0));
		showtime.setPrice(10.0);
		Showtime savedShowtime = showtimeService.addShowtime(showtime);

		Booking booking1 = new Booking();
		booking1.setShowtimeId(savedShowtime.getId());
		booking1.setSeatNumber(15);
		booking1.setUserId(UUID.fromString("84438967-f68f-4fa0-b620-0f08217e76af"));
		bookingService.bookTicket(booking1);

		Booking booking2 = new Booking();
		booking2.setShowtimeId(savedShowtime.getId());
		booking2.setSeatNumber(15); // Same seat
		booking2.setUserId(UUID.fromString("84438967-f68f-4fa0-b620-0f08217e76af"));

		assertThrows(RuntimeException.class, () -> bookingService.bookTicket(booking2));
	}
}
