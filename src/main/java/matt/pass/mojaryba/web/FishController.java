package matt.pass.mojaryba.web;

import matt.pass.mojaryba.domain.comment.CommentService;
import matt.pass.mojaryba.domain.comment.dto.CommentDto;
import matt.pass.mojaryba.domain.fish.Fish;
import matt.pass.mojaryba.domain.fish.FishRepository;
import matt.pass.mojaryba.domain.fish.FishService;
import matt.pass.mojaryba.domain.fish.dto.FishDto;
import matt.pass.mojaryba.domain.rating.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class FishController {
    private FishService fishService;
    private RatingService ratingService;
    private CommentService commentService;



    public FishController(FishService fishService, RatingService ratingService, CommentService commentService) {
        this.fishService = fishService;
        this.ratingService = ratingService;
        this.commentService = commentService;
    }

    @GetMapping("/okaz/{id}")
    String singleFishPage(Model model, @PathVariable long id, @RequestParam(required = false) String commentsSize,
                          Authentication authentication) {
        final FishDto fishDto = fishService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("fish", fishDto);

        if (authentication != null) {
            final String userEmail = authentication.getName();
            final Integer currentRating = ratingService.getCurrentRating(userEmail, id);
            model.addAttribute("currentRating", currentRating);
        }

        List<CommentDto> commentsForFish = commentService.getCommentsForFish(id);
        if (commentsSize == null) {
            model.addAttribute("comments", commentsForFish.stream()
                    .limit(5)
                    .toList());
        }
        else {
            model.addAttribute("comments", commentsForFish);
        }
        return "fish";
    }
    @GetMapping("/top10/oceniane")
    String top10Rated (Model model) {
        final List<FishDto> top10Fishes = fishService.getTop10RatedFishes();
        model.addAttribute("heading", "Top10");
        model.addAttribute("description", "W tej sekcji znajdziesz najlepiej ocenione okazy");
        model.addAttribute("fishes", top10Fishes);
        return "top10";
    }
    @GetMapping("/top10/lubiane")
    String top10Liked(Model model) {
        final List<FishDto> top10LikedFishes = fishService.getTop10LikedFishes();
        model.addAttribute("heading", "Top10");
        model.addAttribute("description", "W tej sekcji znajdziesz najbaerdziej lubiane okazy");
        model.addAttribute("fishes", top10LikedFishes);
        return "top10";
    }

}
