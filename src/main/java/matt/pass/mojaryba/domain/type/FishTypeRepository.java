package matt.pass.mojaryba.domain.type;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface FishTypeRepository extends ListCrudRepository<FishType, Long> {
    Optional<FishType> findByName(String name);
}
