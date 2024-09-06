package matt.pass.mojaryba.domain.fish;

import matt.pass.mojaryba.domain.fish.dto.FishDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FishSearchAndTopService {
    private final FishRepository fishRepository;

    public FishSearchAndTopService(FishRepository fishRepository) {
        this.fishRepository = fishRepository;
    }

    public List<FishDto> getTop10RatedFishes() {
        return fishRepository.findAll().stream()
                .map(FishMapper::map)
                .filter(fish -> fish.getRatingAvg() > 0)
                .sorted((o1, o2) -> -Double.compare(o1.getRatingAvg(), o2.getRatingAvg()))
                .limit(10)
                .toList();
    }
    public List<FishDto> getTop10LikedFishes() {
        return fishRepository.findAllByLikesIsNotNull().stream()
                .map(FishMapper::map)
                .sorted((o1, o2) -> -Integer.compare(o1.getLikedUserEmails().size(), o2.getLikedUserEmails().size()))
                .limit(10)
                .toList();
    }
    public List<FishDto> getTop10BigestFishes() {
        return fishRepository.findAllByWeightGreaterThan(0).stream()
                .map(FishMapper::map)
                .sorted((o1, o2) -> -Double.compare(o1.getWeight(), o2.getWeight()))
                .limit(10)
                .toList();
    }

    public List<FishDto> searchFishes(String userSearch) {
        final List<Fish> allFishes = fishRepository.findAll();
        return search(allFishes, userSearch);
    }

    public List<FishDto> searchInUserFishes(String userEmail, String userSearch) {
        final List<Fish> allFishesByUser = fishRepository.findAllByUser_Email(userEmail);
        return search(allFishesByUser, userSearch);
    }

    private List<FishDto> search(List<Fish> fishes, String userSearch) {
        return fishes.stream()
                .map(FishMapper::map)
                .filter(fish -> searchInFish(fish, userSearch))
                .sorted()
                .toList();
    }

    private static boolean searchInFish(FishDto fish, String userSearch) {
        final String[] userSearchSplit = userSearch.toLowerCase().split(" ");

        for (String text : userSearchSplit) {
            if (fish.getTitle().toLowerCase().contains(text) ||
                    fish.getDescription().toLowerCase().contains(text) ||
                    fish.getFishingMethod().toLowerCase().contains(text) ||
                    fish.getFishingSpot().toLowerCase().contains(text) ||
                    fish.getFishType().toLowerCase().contains(text) ||
                    fish.getBait().toLowerCase().contains(text))
                return true;
        }
        return false;
    }

}
