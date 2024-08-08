package matt.pass.mojaryba.domain.fish.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class FishDto implements Comparable<FishDto> {
    private Long id;
    private String title;
    private double weight;
    private String fishType;
    private LocalDateTime dateAdded;
    private int length;
    private String description;
    private String fishingMethod;  //metoda połowu
    private String bait;           // przynęta
    private String fishingSpot;
    private List<String> photos;
    private double ratingAvg;
    private int ratingCount;
    private List<String> likedUserEmails;
    private String author;

    public FishDto(Long id, String title, double weight, String fishType, LocalDateTime dateAdded, int length,
                   String description, String fishingMethod, String bait, String fishingSpot,
                   List<String> photos, double ratingAvg, int ratingCount, List<String> likedUserEmails, String author) {
        this.id = id;
        this.title = title;
        this.weight = weight;
        this.fishType = fishType;
        this.dateAdded = dateAdded;
        this.length = length;
        this.description = description;
        this.fishingMethod = fishingMethod;
        this.bait = bait;
        this.fishingSpot = fishingSpot;
        this.photos = photos;
        this.ratingAvg = ratingAvg;
        this.ratingCount = ratingCount;
        this.likedUserEmails = likedUserEmails;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getFishType() {
        return fishType;
    }

    public void setFishType(String fishType) {
        this.fishType = fishType;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFishingMethod() {
        return fishingMethod;
    }

    public void setFishingMethod(String fishingMethod) {
        this.fishingMethod = fishingMethod;
    }

    public String getBait() {
        return bait;
    }

    public void setBait(String bait) {
        this.bait = bait;
    }

    public String getFishingSpot() {
        return fishingSpot;
    }

    public void setFishingSpot(String fishingSpot) {
        this.fishingSpot = fishingSpot;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public double getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<String> getLikedUserEmails() {
        return likedUserEmails;
    }

    public void setLikedUserEmails(List<String> likedUserEmails) {
        this.likedUserEmails = likedUserEmails;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FishDto fishDto = (FishDto) o;
        return Double.compare(fishDto.weight, weight) == 0 && length == fishDto.length &&
                Double.compare(fishDto.ratingAvg, ratingAvg) == 0 && ratingCount == fishDto.ratingCount &&
                Objects.equals(id, fishDto.id) && Objects.equals(title, fishDto.title) &&
                Objects.equals(fishType, fishDto.fishType) && Objects.equals(dateAdded, fishDto.dateAdded) &&
                Objects.equals(description, fishDto.description) && Objects.equals(fishingMethod, fishDto.fishingMethod)
                && Objects.equals(bait, fishDto.bait) && Objects.equals(fishingSpot, fishDto.fishingSpot) &&
                Objects.equals(photos, fishDto.photos)  && Objects.equals(author, fishDto.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, weight, fishType, dateAdded, length, description, fishingMethod, bait, fishingSpot,
                photos, ratingAvg, ratingCount, author);
    }

    @Override
    public int compareTo(FishDto o) {
        return - dateAdded.compareTo(o.getDateAdded());
    }
}
