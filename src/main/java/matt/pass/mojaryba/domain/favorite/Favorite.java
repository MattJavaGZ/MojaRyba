//package matt.pass.mojaryba.domain.favorite;
//
//import matt.pass.mojaryba.domain.fish.FishService;
//import matt.pass.mojaryba.domain.fish.dto.FishDto;
//import org.springframework.context.annotation.ScopedProxyMode;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.annotation.SessionScope;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
//public class Favorite {
//
//    private final FishService fishService;
//    private Set<FishDto> favorites = new HashSet<>();
//
//    public Favorite(FishService fishService) {
//        this.fishService = fishService;
//    }
//
//    public void addFishToFavorite(FishDto fishDto) {
//        favorites.add(fishDto);
//    }
//
//    public void deleteFishFromFavorite(FishDto fishDto) {
//        favorites.remove(fishDto);
//    }
//
//    public int getFavoriteSize() {
//        return (int) favorites.stream()
//                .filter(fishService::fishDtoExist)
//                .count();
//    }
//
//    public boolean isExist(FishDto fishDto) {
//        return favorites.contains(fishDto);
//    }
//
//    public Set<FishDto> getFavorites() {
//        return favorites.stream()
//                .filter(fishService::fishDtoExist)
//                .collect(Collectors.toSet());
//    }
//
//}
