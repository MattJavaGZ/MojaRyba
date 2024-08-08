package matt.pass.mojaryba.domain.rating;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    Optional<Rating> findByFish_IdAndUser_Email(long fishId, String userEmail);
    List<Rating> findAllByUser_Email(String userEmail);

}
