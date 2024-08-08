package matt.pass.mojaryba.domain.rating;

import matt.pass.mojaryba.domain.fish.Fish;
import matt.pass.mojaryba.domain.fish.FishRepository;
import matt.pass.mojaryba.domain.user.User;
import matt.pass.mojaryba.domain.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    private RatingRepository ratingRepository;
    private FishRepository fishRepository;
    private UserRepository userRepository;

    public RatingService(RatingRepository ratingRepository, FishRepository fishRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.fishRepository = fishRepository;
        this.userRepository = userRepository;
    }

    public void addOrUpdateRating(String userEmail, long fishId, int rating){
        final Rating ratingToUpdateOrSave = ratingRepository.findByFish_IdAndUser_Email(fishId, userEmail)
                .orElseGet(Rating::new);
        final User user = userRepository.findByEmailIgnoreCase(userEmail).orElseThrow();
        final Fish fish = fishRepository.findById(fishId).orElseThrow();
        ratingToUpdateOrSave.setFish(fish);
        ratingToUpdateOrSave.setUser(user);
        ratingToUpdateOrSave.setRating(rating);
        ratingRepository.save(ratingToUpdateOrSave);
    }

    public Integer getCurrentRating(String userEmail, long fishId) {
       return ratingRepository.findByFish_IdAndUser_Email(fishId, userEmail)
                .map(Rating::getRating)
                .orElse(0);
    }
}
