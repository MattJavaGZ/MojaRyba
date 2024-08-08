package matt.pass.mojaryba.web;

import matt.pass.mojaryba.domain.favorite.Favorite;
import matt.pass.mojaryba.domain.fish.FishService;
import matt.pass.mojaryba.domain.fish.dto.FishDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Controller
public class FavoriteController {
    private Favorite favorite;
    private FishService fishService;

    public FavoriteController(Favorite favorite, FishService fishService) {
        this.favorite = favorite;
        this.fishService = fishService;
    }

    @GetMapping("/favorite")
    String favorite(Model model) {
        final Set<FishDto> favorites = favorite.getFavorites();
        model.addAttribute("heading", "Ulubione");
        model.addAttribute("description", "W tej sekcji znajdziesz okazy dodane przez Ciebie do ulubionych");
        model.addAttribute("fishes", favorites);
        return "fish-listing";
    }
    @GetMapping("/favorite/add/{id}")
    String addToFavorite(@PathVariable long id, @RequestHeader String referer) {
        final FishDto fishDto = fishService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        favorite.addFishToFavorite(fishDto);
        return "redirect:" + referer;
    }
    @GetMapping("/favorite/delete/{id}")
    String deleteFromFavorite(@PathVariable long id, @RequestHeader String referer) {
        final FishDto fishDto = fishService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        favorite.deleteFishFromFavorite(fishDto);
        return "redirect:" + referer;
    }
}
