package shopping.reloaded.com.shoppingapp.models;

public class Hamdard_new {
    String id, imageLink, name, description, side_effects;

    public Hamdard_new(){}

    public Hamdard_new(String id, String imageLink, String name, String description, String side_effects) {
        this.id = id;
        this.imageLink = imageLink;
        this.name = name;
        this.description = description;
        this.side_effects = side_effects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSide_effects() {
        return side_effects;
    }

    public void setSide_effects(String side_effects) {
        this.side_effects = side_effects;
    }
}
