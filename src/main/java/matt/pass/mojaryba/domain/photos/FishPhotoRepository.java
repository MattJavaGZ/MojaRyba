package matt.pass.mojaryba.domain.photos;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface FishPhotoRepository extends ListCrudRepository<FishPhotos, Long> {
    List<FishPhotos> findAllByFish_Id(long fishId);
}
