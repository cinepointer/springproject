package com.cinepointer.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cinepointer.dao.tmdbDao;
import com.cinepointer.dto.actorMovieDto;
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
    
    @Async
    @Override
    public void insertPopularMovies() {
        List<Integer> genreList = tmdbDao.selectGenre();
        List<tmdbActorDto> actorList = new ArrayList<>();
        List<tmdbMovieDto> movieList = new ArrayList<>();
        List<genreDto> movieGenreList = new ArrayList<>();
        List<actorMovieDto> actorMovieList = new ArrayList<>();

        Set<Integer> actorIdSet = new HashSet<>(); // 중복 제거

        for (int genre : genreList) {
            List<tmdbMovieDto> movies = api.fetchPopularMoviesByGenre(genre, 50);
            System.out.println(genre + " 장르의 영화 가져오는 중");

            for (tmdbMovieDto movie : movies) {
                movieList.add(movie);

                for (Integer genreId : movie.getGenreIds()) {
                    genreDto mg = new genreDto();
                    mg.setMovieNum(movie.getMovieNum());
                    mg.setGenreNum(genreId);
                    movieGenreList.add(mg);
                }

                List<tmdbActorDto> actors = api.fetchActorsByMovieId(movie.getMovieNum());
                for (tmdbActorDto actor : actors) {
                    if (actorIdSet.add(actor.getActorId())) {
                        actorList.add(actor); // 중복 제거
                    }

                    actorMovieDto am = new actorMovieDto();
                    am.setMovieNum(movie.getMovieNum());
                    am.setActorNum(actor.getActorId());
                    am.setCharacterName(actor.getCharacterName());
                    am.setActorOrder(actor.getCastOrder());
                    actorMovieList.add(am);
                }
            }
        }

        // 한 번에 배치 삽입
        tmdbDao.insertMoviesBatch(movieList);
        tmdbDao.insertActorsBatch(actorList);
        tmdbDao.insertMovieGenresBatch(movieGenreList);
        tmdbDao.insertActorMoviesBatch(actorMovieList);
    }

    @Override
    public int existsGanre() {
    	return tmdbDao.existsGenre();
    }

	
	
}
