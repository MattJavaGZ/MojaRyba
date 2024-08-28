package matt.pass.mojaryba.domain.fish;

import matt.pass.mojaryba.domain.fish.dto.FishDto;
import matt.pass.mojaryba.domain.fish.dto.FishToSaveDto;
import matt.pass.mojaryba.domain.like.Like;
import matt.pass.mojaryba.domain.photos.FishPhotos;
import matt.pass.mojaryba.domain.rating.Rating;
import matt.pass.mojaryba.domain.user.User;

import java.util.ArrayList;
import java.util.Objects;

public class FishMapper {

    public static FishDto map(Fish fish) {
        final double ratingAvg = fish.getRatings().stream()
                .mapToDouble(Rating::getRating)
                .average().orElse(0);
        return new FishDto(
                fish.getId(),
                fish.getTitle(),
                fish.getWeight(),
                fish.getFishType().getName(),
                fish.getDateAdded(),
                fish.getLength(),
                fish.getDescription(),
                fish.getFishingMethod(),
                fish.getBait(),
                fish.getFishingSpot(),
                fish.getPhotos().stream()
                        .map(FishPhotos::getPhoto)
                        .toList(),
                ratingAvg,
                fish.getRatings().size(),
                fish.getLikes().stream()
                        .map(Like::getUser)
                        .filter(Objects::nonNull)
                        .map(User::getEmail)
                        .toList(),
                fish.getUser() == null ? "Usunięty" : fish.getUser().getNick()
        );
    }

    public static FishToSaveDto mapToSave(Fish fish) {
        return new FishToSaveDto(
                fish.getTitle(),
                fish.getDescription(),
                fish.getWeight(),
                fish.getLength(),
                fish.getFishingMethod(),
                fish.getBait(),
                fish.getFishingSpot(),
                new ArrayList<>(),
                fish.getFishType().getName()
        );
    }
}
