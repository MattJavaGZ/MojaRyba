package matt.pass.mojaryba.domain.type.dto;

public class FishTypeDto {
    private  Long id;
    private String name;
    private String fishSpecies;

    public FishTypeDto(Long id, String name, String fishSpecies) {
        this.id = id;
        this.name = name;
        this.fishSpecies = fishSpecies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFishSpecies() {
        return fishSpecies;
    }

    public void setFishSpecies(String fishSpecies) {
        this.fishSpecies = fishSpecies;
    }

}
