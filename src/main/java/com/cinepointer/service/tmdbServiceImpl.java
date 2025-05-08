package com.cinepointer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cinepointer.dao.tmdbDao;
import com.cinepointer.dto.tmdbGenreDto;
import com.cinepointer.dto.tmdbMovieDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

//생성자 주입 자동화용 lombok기능
@Service
@RequiredArgsConstructor
public class tmdbServiceImpl implements TmdbService {
	private final RestTemplate restTemplate = new RestTemplate();
	private ObjectMapper objectMapper;
    private final tmdbDao tmdbDao;
    

    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.base-url}")
    private String baseUrl;
	
   @Override
	public List<tmdbGenreDto> insertGenre() {
		String url = baseUrl + "/genre/movie/list?api_key=" + apiKey + "&language=ko";
        JsonNode root = restTemplate.getForObject(url, JsonNode.class);
        List<tmdbGenreDto> genres = new ArrayList<>();

        if (root != null && root.has("genres")) {
            for (JsonNode genreNode : root.get("genres")) {
                tmdbGenreDto genre = new tmdbGenreDto();
                genre.setGenreNum(genreNode.get("id").asInt());
                genre.setGenreName(genreNode.get("name").asText());

                //tmdbDao.insertGenre(genre); // DB에 삽입
                genres.add(genre);
            }
        }

        return genres;
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
