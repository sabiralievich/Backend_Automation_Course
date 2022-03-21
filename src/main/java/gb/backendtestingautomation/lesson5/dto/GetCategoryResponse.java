package gb.backendtestingautomation.lesson5.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class GetCategoryResponse {
/*
                 "id": 2,
               "title": "Electronic",
               "products":

 */
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("products")
    private List<Product> products = new ArrayList<>();

    public GetCategoryResponse() {}

    public GetCategoryResponse(Integer id, String title, List<Product> products) {
        super();
        this.id = id;
        this.title = title;
        this.products = products;
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

    @JsonProperty("products")
    public List<Product> getProducts() {
        return products;
    }

    @JsonProperty("products")
    public void setProducts(List<Product> products) {
        this.products = products;
    }


}
