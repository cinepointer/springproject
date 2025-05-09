package com.cinepointer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cinepointer.dao.tmdbDao;
import com.cinepointer.dto.tmdbGenreDto;
import com.cinepointer.dto.tmdbMovieDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

//생성자 주입 자동화용 lombok기능
@Service
@RequiredArgsConstructor
public class tmdbServiceImpl implements tmdbService {
	private final RestTemplate restTemplate;
	private ObjectMapper objectMapper;
    private final tmdbDao tmdbDao;
    

    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.base-url}")
    private String baseUrl;
	
    @Override
    public void insertGenre() {

    	String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + apiKey + "&language=en-US";
    	// TMDB API 호출하여 JSON 데이터 받아오기
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);
        
        // API에서 받은 장르 데이터 (JSON)에서 "genres" 필드를 추출
        List<Map<String, Object>> genresData = (List<Map<String, Object>>) response.getBody().get("genres");
        
        // JSON 데이터를 tmdbGenreDto 객체 리스트로 변환
        List<tmdbGenreDto> genres = new ArrayList<>();
        for (Map<String, Object> genreData : genresData) {
            tmdbGenreDto genre = new tmdbGenreDto();
            genre.setGenreNum((Integer) genreData.get("id"));  
            genre.setGenreName((String) genreData.get("name"));
            genres.add(genre);
            System.out.println(genre);
        }
    	
    	
    	tmdbDao.insertGenre(genres);
    }
    
	@Override
		public List<tmdbMovieDto> updatePopularMovies() {
			// TODO Auto-generated method stub
			return null;
		}
	@Override
		public List<tmdbMovieDto> insertPopularMovies() {
			// TODO Auto-generated method stub
			return null;
		}
	
	
}
