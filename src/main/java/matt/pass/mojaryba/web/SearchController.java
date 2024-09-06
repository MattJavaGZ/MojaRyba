package matt.pass.mojaryba.web;

import matt.pass.mojaryba.domain.fish.FishSearchAndTopService;
import matt.pass.mojaryba.domain.fish.dto.FishDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private final FishSearchAndTopService fishSearchAndTopService;

    public SearchController(FishSearchAndTopService fishSearchAndTopService) {
        this.fishSearchAndTopService = fishSearchAndTopService;
    }

    @GetMapping("/search-form")
    String searchForm(){
        return "search-form";
    }
    @GetMapping("/search")
    String searchFishes(Model model, @RequestParam String search) {
        final List<FishDto> foundFishes = fishSearchAndTopService.searchFishes(search);
        model.addAttribute("heading", "Znalezione okazy");
        model.addAttribute("description",
                "Poniżej znalezione okazy. Szukaliśmy po tytule oraz opisach okazów.");
        model.addAttribute("fishes", foundFishes);

        return "fish-listing";
    }
}
