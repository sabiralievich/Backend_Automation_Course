package gb.backendtestingautomation.lesson5;

import gb.backendtestingautomation.lesson5.dto.Product;
import gb.backendtestingautomation.lesson5.service.ProductService;
import gb.backendtestingautomation.lesson5.util.RetrofitUtils;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateProductTest {
    static ProductService productService;
    Product product;

    int id;
    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @BeforeEach
    void setup() {
        product = new Product()
                .withTitle("Bread")
                .withPrice(100)
                .withCategoryTitle("Food");
    }

    @Test
    void createProductInFoodCategoryTest() throws IOException {
        Response<Product> response = productService.createProduct(product).execute();
        id = response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @AfterEach
    void tearDown() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

}
