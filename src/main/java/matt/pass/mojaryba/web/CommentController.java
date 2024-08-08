package matt.pass.mojaryba.web;

import matt.pass.mojaryba.domain.comment.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/dodaj-komentarz")
    String addComment(@RequestParam String content, @RequestParam int id, Authentication authentication, @RequestHeader String referer){
        final String userEmail = authentication.getName();
        commentService.addComment(userEmail, id, content);
        return "redirect:" + referer;
    }
}
