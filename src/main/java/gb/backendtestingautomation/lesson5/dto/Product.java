package gb.backendtestingautomation.lesson5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

/*            "id": 18882,
            "title": "Kirin Inchiban",
            "price": 190,
            "categoryTitle": "Electronic"*/

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("categoryTitle")
    private String categoryTitle;

    public Product() {
    }

    public Product(Integer id, String title, Integer price, String categoryTitle) {
        super();
        this.id = id;
        this.title = title;
        this.price = price;
        this.categoryTitle = categoryTitle;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("price")
    public Integer getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Integer price) {
        this.price = price;
    }

    @JsonProperty("categoryTitle")
    public String getCategoryTitle() {
        return categoryTitle;
    }

    @JsonProperty("categoryTitle")
    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Product withId(Integer id) {
        this.id = id;
        return this;
    }

    public Product withTitle(String title) {
        this.title = title;
        return this;
    }

    public Product withPrice(Integer price) {
        this.price = price;
        return this;
    }

    public Product withCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
        return this;
    }
}
