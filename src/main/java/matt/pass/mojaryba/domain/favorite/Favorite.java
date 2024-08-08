package matt.pass.mojaryba.domain.favorite;

import matt.pass.mojaryba.domain.fish.dto.FishDto;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import java.util.HashSet;
import java.util.Set;

@Service
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Favorite {

    private Set<FishDto> favorites = new HashSet<>();

    public void addFishToFavorite(FishDto fishDto) {
        favorites.add(fishDto);
    }
    public void deleteFishFromFavorite(FishDto fishDto) {
        favorites.remove(fishDto);
    }
    public int getFavoriteSize() {
        return favorites.size();
    }
    public boolean isExist(FishDto fishDto) {
        return favorites.contains(fishDto);
    }
    public Set<FishDto> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<FishDto> favorites) {
        this.favorites = favorites;
    }
}
