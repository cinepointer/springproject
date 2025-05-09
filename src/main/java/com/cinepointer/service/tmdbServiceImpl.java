package com.cinepointer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cinepointer.dao.tmdbDao;
import com.cinepointer.dto.genreDto;
import com.cinepointer.dto.tmdbActorDto;
import com.cinepointer.dto.tmdbGenreDto;
import com.cinepointer.dto.tmdbMovieDto;

import lombok.RequiredArgsConstructor;

//생성자 주입 자동화용 lombok기능
@Service
@RequiredArgsConstructor
public class tmdbServiceImpl implements tmdbService {
	private final tmdbApiService api;
    private final tmdbDao tmdbDao;
    

    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.base-url}")
    private String baseUrl;
	
    @Override
    public void insertGenre() {
        List<tmdbGenreDto> genres = api.fetchGenres();
        tmdbDao.insertGenre(genres);
    }
    
    @Override
    public void insertPopularMovies() {
        List<Integer> genreList = tmdbDao.selectGenre();

        for (int genre : genreList) {
            List<tmdbMovieDto> movieList = api.fetchPopularMoviesByGenre(genre, 500);

            for (tmdbMovieDto movie : movieList) {
                int movieId = movie.getMovieNum();
                List<tmdbActorDto> actors = api.fetchActorsByMovieId(movieId); // 출연진 API 호출
                System.out.println(actors);
                // 2. 배우 정보 먼저 insert (중복 시 갱신)
                for (tmdbActorDto actor : actors) {
                	tmdbDao.insertActor(actor);
                }
                
                // 3. 영화 정보 insert (중복 시 갱신)
                tmdbDao.insertMovie(movie);
                for(Integer genreId:movie.getGenreIds()) {
                	genreDto mg=new genreDto();
                	mg.setMovieNum(movieId);
                	mg.setGenreNum(genreId);
                	tmdbDao.insertMovieGenre(mg);
                	
                }
                
                for(tmdbActorDto actor:actors) {
                	tmdbDao.insertActorMovie(actor,movieId);
                }
                
            }
            
        }
    }

	
	
}
