package com.cinepointer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cinepointer.dto.TmdbCreditsResponse;
import com.cinepointer.dto.TmdbMovieResponse;
import com.cinepointer.dto.tmdbActorDto;
import com.cinepointer.dto.tmdbGenreDto;
import com.cinepointer.dto.tmdbMovieDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class tmdbApiService {
    private final RestTemplate restTemplate;

    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.base-url}")
    private String baseUrl;

    public List<tmdbGenreDto> fetchGenres() {
        String url = baseUrl + "/genre/movie/list?api_key=" + apiKey + "&language=ko-KR";
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);

        List<Map<String, Object>> genresData = (List<Map<String, Object>>) response.getBody().get("genres");
        List<tmdbGenreDto> genres = new ArrayList<>();

        for (Map<String, Object> genreData : genresData) {
            tmdbGenreDto genre = new tmdbGenreDto();
            genre.setGenreNum((Integer) genreData.get("id"));
            genre.setGenreName((String) genreData.get("name"));
            genres.add(genre);
        }

        return genres;
    }
    public List<tmdbMovieDto> fetchPopularMoviesByGenre(int genreId, int count) {
        List<tmdbMovieDto> movies = new ArrayList<>();
        int fetched = 0;
        int page = 1;

        while (fetched < count) {
            String url = baseUrl + "/discover/movie"
                    + "?api_key=" + apiKey
                    + "&language=ko-KR"
                    + "&sort_by=popularity.desc"
                    + "&with_genres=" + genreId
                    + "&page=" + page;

            ResponseEntity<TmdbMovieResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<TmdbMovieResponse>() {}
            );

            List<tmdbMovieDto> resultList = response.getBody().getResults();

            for (tmdbMovieDto movie : resultList) {
                if (fetched >= count) break;
                movies.add(movie);
                fetched++;
            }

            page++;
            if (page > 500 || resultList.isEmpty()) break;
        }

        return movies;
    }

    public List<tmdbActorDto> fetchActorsByMovieId(int movieId) {
        String url = baseUrl + "/movie/" + movieId + "/credits"
                   + "?api_key=" + apiKey
                   + "&language=ko-KR";

        try {
            ResponseEntity<TmdbCreditsResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<TmdbCreditsResponse>() {}
            );

            return response.getBody().getCast();
        } catch (Exception e) {
            System.err.println("출연진 정보 API 호출 실패: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    
    
}
