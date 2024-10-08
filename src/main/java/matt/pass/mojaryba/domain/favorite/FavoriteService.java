package matt.pass.mojaryba.domain.favorite;

import matt.pass.mojaryba.domain.fish.FishMapper;
import matt.pass.mojaryba.domain.fish.FishRepository;
import matt.pass.mojaryba.domain.fish.dto.FishDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class FavoriteService {

    private final FishRepository fishRepository;

    public FavoriteService(FishRepository fishRepository) {
        this.fishRepository = fishRepository;
    }

    public Set<FishDto> getFavoritesList(String favorite) {
        Set<FishDto> favoritesList = new HashSet<>();

        if (!favorite.isEmpty()) {
            final String[] split = favorite.split(";");
            for (String id : split) {
                fishRepository.findById(Long.valueOf(id.trim()))
                        .map(FishMapper::map)
                        .ifPresent(favoritesList::add);
            }
        }
        return favoritesList;
    }

    public String addToFavorite(String favoriteCookie, Long fishId) {
        return favoriteCookie + fishId + ";";
    }

    public String deleteWithFavorite(String favoriteCookie, Long fishId) {
        return favoriteCookie.replaceAll(fishId + ";", "");
    }


}


