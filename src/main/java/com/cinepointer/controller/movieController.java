package com.cinepointer.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinepointer.dto.movieDto;
import com.cinepointer.dto.reviewDto;
import com.cinepointer.service.movieService;
import com.cinepointer.service.reviewService;

import jakarta.servlet.http.HttpSession;

@Controller
public class movieController {

    private final movieService movieService;
    private final reviewService reviewService;

    @Autowired
    public movieController(movieService movieService, reviewService reviewService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
    }

    @GetMapping({"/", "/movies"})
    public String mainPage(
        @RequestParam(required = false, value="search") String search,
        @RequestParam(required = false, value="genre") String genre,
        @RequestParam(required = false, value="sort") String sort,
        Model model
    ) {
        // TODO: search, genre, sort 파라미터 활용 예정이면 아래에 구현

        model.addAttribute("popularMovies", movieService.findPopular(50));
        model.addAttribute("latestMovies", movieService.findLatest(50));
        model.addAttribute("recommendedMovies", movieService.findPopular(50)); // TODO: 추천 로직 분리 고려

        // 장르 코드와 이름 매핑
        Map<String, String> genreMap = new LinkedHashMap<>();
        genreMap.put("1", "액션");
        genreMap.put("2", "로맨스");
        genreMap.put("3", "코미디");
        genreMap.put("4", "스릴러");
        genreMap.put("5", "드라마");

        List<Map<String, Object>> genres = new ArrayList<>();
        for (Map.Entry<String, String> entry : genreMap.entrySet()) {
            Map<String, Object> genreSection = new HashMap<>();
            genreSection.put("code", entry.getKey()); // 코드도 넣어두면 확장에 유리
            genreSection.put("name", entry.getValue());
            genreSection.put("movies", movieService.findByGenre(entry.getValue(), 50));
            genres.add(genreSection);
        }
        model.addAttribute("genres", genres);

        model.addAttribute("pageType", "main");

        return "mainPage";
    }


    // 영화 상세 페이지
    @GetMapping("/movies/{id}")
    public String movieDetail(@PathVariable("id") Long id, Model model, HttpSession session) {
        movieDto movie = movieService.findById(id);
        model.addAttribute("movie", movie);

        // 관련 영화 (같은 장르 4개, 자기 자신 제외)
        List<movieDto> related = movieService.findByGenre(movie.getGenre(), 4);
        if (related != null) {
            related.removeIf(m -> m == null || m.getId() == null || m.getId().equals(id));
        }
        model.addAttribute("relatedMovies", related);

        // 로그인 여부
        Integer userNum = (Integer) session.getAttribute("userNum");
        boolean isLogin = (userNum != null);
        model.addAttribute("isLogin", isLogin);

        // 찜 여부 및 userNum 전달
        boolean isWished = false;
        if (isLogin) {
            isWished = movieService.isWished(userNum, id.intValue());
            model.addAttribute("userNum", userNum);
        }
        model.addAttribute("isWished", isWished);

        // 리뷰 미리보기 3개 추가
        List<reviewDto> recentReviews = reviewService.getLimitedReviewsByMovie(id.intValue(), 3);
        model.addAttribute("reviews", recentReviews);

        return "moviePage";
    }


    // 찜하기 (Wish 추가)
    @PostMapping("/movie/wish")
    public String wishMovie(@RequestParam("movieNum") int movieNum, HttpSession session) {
        Integer userNum = (Integer) session.getAttribute("userNum");
        if (userNum != null) {
            movieService.addWish(userNum, movieNum);
        }
        return "redirect:/movies/" + movieNum;
    }

    // 찜 취소
    @PostMapping("/movie/unwish")
    public String unwishMovie(@RequestParam("movieNum") int movieNum, HttpSession session) {
        Integer userNum = (Integer) session.getAttribute("userNum");
        if (userNum != null) {
            movieService.removeWish(userNum, movieNum);
        }
        return "redirect:/movies/" + movieNum;
    }

    // 내 찜 목록 (경로와 반환값을 myMovie로 변경)
    @GetMapping("/my-movie")
    public String myMovie(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Integer userNum = (Integer) session.getAttribute("userNum");
        redirectAttributes.addAttribute("fragment","myMovie");
        if (userNum != null) {
            List<movieDto> wishList = movieService.getWishList(userNum);
            model.addAttribute("myMovie", wishList); // myMovie.html에서 myMovies 사용
        }
        return "redirect:/info"; // 대안
    }

    // (좋아요 기능은 서비스에 없으므로 주석 처리)
    /*
    @PostMapping("/movies/{id}/like")
    @ResponseBody
    public String likeMovie(@PathVariable("id") Long id, HttpSession session) {
        // likesService.addLike 등 구현 필요
        return "fail";
    }
    */
}
