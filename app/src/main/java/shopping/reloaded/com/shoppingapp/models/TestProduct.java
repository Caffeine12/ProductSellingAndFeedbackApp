package shopping.reloaded.com.shoppingapp.models;

public class TestProduct {

    public String name, price, imageLink;

    public TestProduct(String name, String price, String imageLink) {
        this.name = name;
        this.price = price;
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nPrice: " + price + "\nImage: " + imageLink;
    }
}
