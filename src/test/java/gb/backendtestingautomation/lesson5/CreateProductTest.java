package gb.backendtestingautomation.lesson5;

import gb.backendtestingautomation.lesson5.dto.Product;
import gb.backendtestingautomation.lesson5.service.ProductService;
import gb.backendtestingautomation.lesson5.util.RetrofitUtils;
import gb.backendtestingautomation.lesson6.db.model.Products;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

    class CreateProductTest extends AbstractTest{
    static ProductService productService;
    static Product product;

    private static int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
        product = new Product()
                .withTitle("Bread")
                .withPrice(100)
                .withCategoryTitle("Food");
    }

/*
    @BeforeEach
    void setup() {
        product = new Product()
                .withTitle("Bread")
                .withPrice(100)
                .withCategoryTitle("Food");
    }
*/

    @Test
    void createProductInFoodCategoryTest() throws IOException {
        Response<Product> response = productService.createProduct(product).execute();
        assert response.body() != null;
        id = response.body().getId();
        System.out.println("created product with id " + id);
        //Assert by retrofit:
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        // assert with sql query:
        example.createCriteria().andIdEqualTo((long)id);
        List<Products> product = productsMapper.selectByExample(example);
        assertThat(product.size(), equalTo(1));
        assertThat(product.get(0).getTitle(), equalTo("Bread"));
        assertThat(product.get(0).getPrice(), equalTo(100));


    }

    @Test
    void getProductByIdTest() throws IOException {
        System.out.println("product with id " + id + " exists");
        Response<Product> response = productService.getProductById(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assert response.body() != null;
        assertThat(response.body().getId(), equalTo(id));
        assertThat(response.body().getTitle(), equalTo("Bread"));
        assertThat(response.body().getPrice(), equalTo(100));
        assertThat(response.body().getCategoryTitle(), equalTo("Food"));
        modifyProductById();




    }


    void modifyProductById() throws IOException {

        Product modifiedProduct =
                new Product()
                        .withId(id)
                        .withTitle("White Bread")
                        .withPrice(200)
                        .withCategoryTitle("Food");
        Response<Product> response = productService.modifyProduct(modifiedProduct).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        System.out.println("product with id " + id + " has been modified.");
    }

    @Test
    void getModifiedProductByIdTest() throws IOException {
        System.out.println("product with id " + id + " exists");
        Response<Product> response = productService.getProductById(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assert response.body() != null;
        assertThat(response.body().getId(), equalTo(id));
        assertThat(response.body().getTitle(), equalTo("White Bread"));
        assertThat(response.body().getPrice(), equalTo(200));
        assertThat(response.body().getCategoryTitle(), equalTo("Food"));

    }

    @AfterAll
    static void tearDown() throws IOException {

        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        System.out.println("product with id " + id + " has been deleted");
    }


}
