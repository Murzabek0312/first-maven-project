package com.mentor.dmdev.http.controller;


import com.mentor.dmdev.dto.MovieCreateEditDto;
import com.mentor.dmdev.dto.MovieReadDto;
import com.mentor.dmdev.dto.paging.PageResponse;
import com.mentor.dmdev.enums.SubscriptionTypes;
import com.mentor.dmdev.service.ActorService;
import com.mentor.dmdev.service.MovieService;
import com.mentor.dmdev.service.SubscriptionService;
import com.mentor.dmdev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final UserService userService;
    private final ActorService actorService;
    private final SubscriptionService subscriptionService;

    @GetMapping
    public String findAll(Model model, Pageable pageable) {
        Page<MovieReadDto> page = movieService.findAll(pageable);
        model.addAttribute("movies", PageResponse.of(page));
        return "movie/movies";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return movieService.findById(id)
                .map(movie -> {
                    model.addAttribute("subscriptions", SubscriptionTypes.values());
                    model.addAttribute("director", userService.findById(movie.getDirectorId()).get());
                    model.addAttribute("subscription", subscriptionService.findById(movie.getSubscriptionId()).get());
                    model.addAttribute("actors", actorService.findAllByIds(movie.getActorIds()));
                    model.addAttribute("movie", movie);
                    return "movie/movie";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, MovieCreateEditDto movieCreateEditDto) {
        return "redirect:/movies/" + movieService.update(id, movieCreateEditDto).get().getId();
    }
}