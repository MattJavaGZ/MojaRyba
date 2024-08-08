package matt.pass.mojaryba.web;

import matt.pass.mojaryba.domain.like.LikeService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LikeController {
    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/like/{id}")
    String updateLikeFish(@PathVariable long id, Authentication authentication){
        final String userEmail = authentication.getName();
        likeService.addOrUpdateLike(userEmail, id);
        return "redirect:/";

    }
}
