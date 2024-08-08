package matt.pass.mojaryba.domain.like;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends CrudRepository<Like, Long> {

     Optional<Like> findByUser_EmailAndFish_Id(String userEmail, long fishId);
     List<Like> findAllByUser_Email(String userEmail);
}
