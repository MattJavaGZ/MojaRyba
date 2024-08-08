package matt.pass.mojaryba.domain.rating;

import jakarta.persistence.*;
import matt.pass.mojaryba.domain.fish.Fish;
import matt.pass.mojaryba.domain.user.User;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "fish_id")
    private Fish fish;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private int rating;

    public Rating(Fish fish, User user, int rating) {
        this.fish = fish;
        this.user = user;
        this.rating = rating;
    }

    public Rating() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fish getFish() {
        return fish;
    }

    public void setFish(Fish fish) {
        this.fish = fish;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
