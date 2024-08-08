package matt.pass.mojaryba.domain.type;

import matt.pass.mojaryba.domain.type.dto.FishTypeDto;

public class FishTypeMapper {

    public static FishTypeDto map (FishType fishType) {
        return new FishTypeDto(
                fishType.getId(),
                fishType.getName(),
                fishType.getFishSpecies().getName()
        );
    }
}
