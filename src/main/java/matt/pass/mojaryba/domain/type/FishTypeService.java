package matt.pass.mojaryba.domain.type;

import matt.pass.mojaryba.domain.type.dto.FishTypeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FishTypeService {
    private FishTypeRepository fishTypeRepository;

    public FishTypeService(FishTypeRepository fishTypeRepository) {
        this.fishTypeRepository = fishTypeRepository;
    }

    public Optional<FishTypeDto> findFishTypeByName (String typeName){
        return fishTypeRepository.findByName(typeName)
                .map(FishTypeMapper::map);
    }
    public List<FishTypeDto> findAllTypes(){
        return fishTypeRepository.findAll().stream()
                .map(FishTypeMapper::map)
                .toList();
    }

}
