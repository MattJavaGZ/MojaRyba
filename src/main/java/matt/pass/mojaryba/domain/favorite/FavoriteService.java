package matt.pass.mojaryba.domain.favorite;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import matt.pass.mojaryba.domain.fish.Fish;
import matt.pass.mojaryba.domain.fish.FishMapper;
import matt.pass.mojaryba.domain.fish.FishRepository;
import matt.pass.mojaryba.domain.fish.dto.FishDto;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


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
                final Optional<Fish> fishById = fishRepository.findById(Long.valueOf(id.trim()));
                if (fishById.isPresent()) {
                    final FishDto fishToSave = FishMapper.map(fishById.get());
                    favoritesList.add(fishToSave);
                }
            }
        }
        return favoritesList;
    }

    public void addToFavorite(HttpServletResponse response, String favoriteCookie, Long fishId) {
        final String updateFavoriteCookie = favoriteCookie + fishId + ";";
        createAndSendFavoriteCookie(response, updateFavoriteCookie);
    }

    public void deleteWithFavorite(HttpServletResponse response, String favoriteCookie, Long fishId) {
        final String updateFavoriteCookie = favoriteCookie.replaceAll(fishId + ";", "");
        createAndSendFavoriteCookie(response, updateFavoriteCookie);
    }

    private void createAndSendFavoriteCookie(HttpServletResponse response, String favorite) {
        final Cookie cookie = new Cookie("favorite", URLEncoder.encode(favorite, StandardCharsets.UTF_8));
        cookie.setMaxAge(365 * 24 * 60 * 60);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

}


