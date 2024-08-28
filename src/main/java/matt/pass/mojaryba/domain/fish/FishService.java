package matt.pass.mojaryba.domain.fish;

import matt.pass.mojaryba.domain.comment.Comment;
import matt.pass.mojaryba.domain.comment.CommentRepository;
import matt.pass.mojaryba.domain.fish.dto.FishDto;
import matt.pass.mojaryba.domain.fish.dto.FishToSaveDto;
import matt.pass.mojaryba.domain.like.Like;
import matt.pass.mojaryba.domain.like.LikeRepository;
import matt.pass.mojaryba.domain.photos.FishPhotoRepository;
import matt.pass.mojaryba.domain.photos.FishPhotos;
import matt.pass.mojaryba.domain.rating.Rating;
import matt.pass.mojaryba.domain.rating.RatingRepository;
import matt.pass.mojaryba.domain.type.FishType;
import matt.pass.mojaryba.domain.type.FishTypeRepository;
import matt.pass.mojaryba.domain.user.User;
import matt.pass.mojaryba.files.ImageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FishService {
    private final static int PAGE_SIZE = 15;
    private FishRepository fishRepository;
    private FishTypeRepository fishTypeRepository;
    private ImageService imageService;
    private FishPhotoRepository fishPhotoRepository;
    private CommentRepository commentRepository;
    private RatingRepository ratingRepository;
    private LikeRepository likeRepository;

    public FishService(FishRepository fishRepository, FishTypeRepository fishTypeRepository, ImageService imageService,
                       FishPhotoRepository fishPhotoRepository, CommentRepository commentRepository,
                       RatingRepository ratingRepository, LikeRepository likeRepository) {
        this.fishRepository = fishRepository;
        this.fishTypeRepository = fishTypeRepository;
        this.imageService = imageService;
        this.fishPhotoRepository = fishPhotoRepository;
        this.commentRepository = commentRepository;
        this.ratingRepository = ratingRepository;
        this.likeRepository = likeRepository;
    }

    public List<FishDto> findAllFishes(int page) {
        Pageable pageable = getPageable(page);
        return fishRepository.findAll(pageable)
                .stream()
                .map(FishMapper::map)
//                .sorted()
                .toList();
    }
    public int totalPagesAllFishes(int page){
        Pageable pageable = getPageable(page);
        return fishRepository.findAll(pageable).getTotalPages();
    }
    private static Pageable getPageable(int page) {
        return PageRequest.of(page, PAGE_SIZE, Sort.by("dateAdded").descending());
    }
    public List<FishDto> findFishesByType(String typeName, int page) {
        Pageable pageable = getPageable(page);
        return fishRepository.findAllByFishType_Name(typeName, pageable).stream()
                .map(FishMapper::map)
                .toList();
    }
    public int totalPagesFishesByType(String typeName, int page) {
        Pageable pageable = getPageable(page);
        return fishRepository.findAllByFishType_Name(typeName, pageable).getTotalPages();
    }
    public Optional<FishDto> findById(long id) {
        return fishRepository.findById(id)
                .map(FishMapper::map);
    }


    public long saveFish(FishToSaveDto fishToSaveDto, User user) {
        final Fish fish = new Fish();
        fish.setTitle(fishToSaveDto.getTitle());
        fish.setDateAdded(LocalDateTime.now());
        fish.setDescription(fishToSaveDto.getDescription());
        fish.setWeight(fishToSaveDto.getWeight());
        fish.setLength(fishToSaveDto.getLength());
        fish.setFishingMethod(fishToSaveDto.getFishingMethod());
        fish.setBait(fishToSaveDto.getBait());
        fish.setFishingSpot(fishToSaveDto.getFishingSpot());
        final FishType fishType = fishTypeRepository.findByName(fishToSaveDto.getFishType()).orElseThrow();
        fish.setFishType(fishType);
        fish.setUser(user);
        final Fish savedFish = fishRepository.save(fish);
        savePhotos(fishToSaveDto, savedFish);
        return savedFish.getId();
    }

    private void savePhotos(FishToSaveDto fishToSaveDto, Fish savedFish) {
        final List<MultipartFile> photos = fishToSaveDto.getPhotos();
        for (MultipartFile photo : photos) {
            if (!photo.isEmpty()) {
                final String photoName = imageService.saveImage(photo);
                final FishPhotos fishPhotosToSave = new FishPhotos();
                fishPhotosToSave.setFish(savedFish);
                fishPhotosToSave.setPhoto(photoName);
                fishPhotoRepository.save(fishPhotosToSave);
            }

        }
    }
    @Transactional
    public void editFish(FishToSaveDto fishToSave, long fishToEditId) {
        final Fish fishToEdit = fishRepository.findById(fishToEditId).orElseThrow();
        fishToEdit.setTitle(fishToSave.getTitle());
        fishToEdit.setDescription(fishToSave.getDescription());
        fishToEdit.setWeight(fishToSave.getWeight());
        fishToEdit.setLength(fishToSave.getLength());
        fishToEdit.setFishingMethod(fishToSave.getFishingMethod());
        fishToEdit.setBait(fishToSave.getBait());
        fishToEdit.setFishingSpot(fishToSave.getFishingSpot());
        if (!fishToEdit.getFishType().getName().equals(fishToSave.getFishType())) {
            final FishType fishType = fishTypeRepository.findByName(fishToSave.getFishType()).orElseThrow();
            fishToEdit.setFishType(fishType);
        }
        if (!fishToSave.getPhotos().isEmpty()){
            savePhotos(fishToSave, fishToEdit);
        }
    }

    public List<FishDto> getTop10RatedFishes() {
        return fishRepository.findAll().stream()
                .map(FishMapper::map)
                .filter(fish -> fish.getRatingAvg() > 0)
                .sorted(new Comparator<FishDto>() {
                    @Override
                    public int compare(FishDto o1, FishDto o2) {
                        return -Double.compare(o1.getRatingAvg(), o2.getRatingAvg());
                    }
                })
                .limit(10)
                .toList();
    }
    public List<FishDto> getTop10LikedFishes() {
        return fishRepository.findAllByLikesIsNotNull().stream()
                .map(FishMapper::map)
                .sorted(new Comparator<FishDto>() {
                    @Override
                    public int compare(FishDto o1, FishDto o2) {
                        return -Integer.compare(o1.getLikedUserEmails().size(), o2.getLikedUserEmails().size());
                    }
                })
                .limit(10)
                .toList();
    }
    public List<FishDto> getTop10BigestFishes() {
       return fishRepository.findAllByWeightGreaterThan(0).stream()
               .map(FishMapper::map)
               .sorted(new Comparator<FishDto>() {
                   @Override
                   public int compare(FishDto o1, FishDto o2) {
                       return -Double.compare(o1.getWeight(), o2.getWeight());
                   }
               })
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

    public List<FishDto> findAllByUserEmail(String userEmail) {
        return fishRepository.findAllByUser_Email(userEmail).stream()
                .map(FishMapper::map)
                .sorted()
                .toList();
    }

    public List<FishDto> findAllCommentedFishesByUser(String userEmail) {
        return commentRepository.findAllByUser_Email(userEmail).stream()
                .map(Comment::getFish)
                .distinct()
                .map(FishMapper::map)
                .sorted()
                .toList();
    }

    public List<FishDto> findAllRatedFishesByUser(String userEmail) {
        return ratingRepository.findAllByUser_Email(userEmail).stream()
                .map(Rating::getFish)
                .map(FishMapper::map)
                .sorted()
                .toList();
    }

    public List<FishDto> findAllLikedFishesByUser(String userEmail) {
        return likeRepository.findAllByUser_Email(userEmail).stream()
                .map(Like::getFish)
                .map(FishMapper::map)
                .sorted()
                .toList();
    }

    public List<FishDto> findFishesByTypeAndUser(String fishTypeName, String userEmail) {
        return fishRepository.findAllByFishType_NameAndUser_Email(fishTypeName, userEmail).stream()
                .map(FishMapper::map)
                .sorted()
                .toList();
    }

    public List<FishDto> findFishesByUserAndDate(String userEmail, LocalDate start, LocalDate end) {
        return fishRepository.findAllByUser_Email(userEmail).stream()
                .filter(fish -> fish.getDateAdded().isAfter(
                        start.atStartOfDay()) && fish.getDateAdded().isBefore(end.atStartOfDay().plusHours(24)))
                .map(FishMapper::map)
                .sorted()
                .toList();
    }
    public void deleteFishById(long id){
        final List<FishPhotos> photosToDelete = fishPhotoRepository.findAllByFish_Id(id);
        imageService.deleteImages(photosToDelete);
        fishRepository.deleteById(id);
    }
    public FishToSaveDto findByIdToSave(long id){
        return fishRepository.findById(id)
                .map(FishMapper::mapToSave)
                .orElseThrow();
    }
    public boolean fishDtoExist(FishDto fishDto) {
        return fishRepository.existsById(fishDto.getId());
    }
}
