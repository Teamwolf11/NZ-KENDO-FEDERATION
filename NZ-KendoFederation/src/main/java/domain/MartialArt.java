package domain;

public class MartialArt {
    private String martialArtId;
    private String name;             //Name of the martial art

    public MartialArt(){
    }
    
    public MartialArt(String martialArtId, String name) {
        this.martialArtId = martialArtId;
        this.name = name;
    }

    public String getMartialArtId() {
        return martialArtId;
    }

    public void setMartialArtId(String martialArtId) {
        this.martialArtId = martialArtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
