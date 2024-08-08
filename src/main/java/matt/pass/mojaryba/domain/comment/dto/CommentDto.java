package matt.pass.mojaryba.domain.comment.dto;

import java.time.LocalDateTime;

public class CommentDto  {
    private Long id;
    private String content;
    private String userNick;
    private LocalDateTime dateAdded;

    public CommentDto(Long id, String content, String userNick, LocalDateTime dateAdded) {
        this.id = id;
        this.content = content;
        this.userNick = userNick;
        this.dateAdded = dateAdded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }


}
