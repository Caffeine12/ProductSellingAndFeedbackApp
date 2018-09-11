package shopping.reloaded.com.shoppingapp.models;

public class Hamdard {
    String id, name, imageLink,description, actionAndUsages, side_effectstorage;

    public Hamdard(String id, String name, String imageLink, String description, String actionAndUsages, String side_effectstorage) {
        this.id = id;
        this.name = name;
        this.imageLink = imageLink;
        this.description = description;
        this.actionAndUsages = actionAndUsages;
        this.side_effectstorage = side_effectstorage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionAndUsages() {
        return actionAndUsages;
    }

    public void setActionAndUsages(String actionAndUsages) {
        this.actionAndUsages = actionAndUsages;
    }

    public String getSide_effectstorage() {
        return side_effectstorage;
    }

    public void setSide_effectstorage(String side_effectstorage) {
        this.side_effectstorage = side_effectstorage;
    }
}
