package com.cinepointer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class cinepointerService {
    @Value("${tmdb.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, Object>> fetchGenres() {
        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + apiKey + "&language=ko";
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return (List<Map<String, Object>>) response.get("genres");
    }

    public List<Map<String, Object>> fetchPopularMoviesByGenre(int genreId) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (int page = 1; page <= 25; page++) {
            String url = String.format("https://api.themoviedb.org/3/discover/movie?api_key=%s&language=ko&with_genres=%d&page=%d",
                    apiKey, genreId, page);
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            List<Map<String, Object>> movies = (List<Map<String, Object>>) response.get("results");
            result.addAll(movies);
        }
        return result;
    }
	
	
	
	public static void main(String args[]) {
		
	}
}
