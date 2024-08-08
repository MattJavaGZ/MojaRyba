package matt.pass.mojaryba.web;

import matt.pass.mojaryba.domain.rating.RatingService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RatingController {
    private RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/ocen-rybe")
    String addRating(@RequestParam long fishId, @RequestParam int rating, Authentication authentication,
                     @RequestHeader String referer) {
        final String userEmail = authentication.getName();
        ratingService.addOrUpdateRating(userEmail, fishId, rating);
        return "redirect:" + referer;
    }
}
