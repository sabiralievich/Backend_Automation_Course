package gb.backendtestingautomation.lesson5;

import gb.backendtestingautomation.lesson5.dto.GetCategoryResponse;
import gb.backendtestingautomation.lesson5.dto.Product;
import gb.backendtestingautomation.lesson5.service.CategoryService;
import gb.backendtestingautomation.lesson5.util.RetrofitUtils;
import gb.backendtestingautomation.lesson6.db.model.Products;
import org.hamcrest.CoreMatchers;
import retrofit2.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

    class GetCategoryTest extends AbstractTest {

    static CategoryService categoryService;

    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @Test
    void getCategoryByIdPositiveTest() throws IOException {
        Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assert response.body() != null;
        assertThat(response.body().getId(), equalTo(1));
        assertThat(response.body().getTitle(), equalTo("Food"));
        response.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo("Food")));

    }

    //To check if GET request returns the same quantity of records that SQL query does:
    @Test
    void getCategoryByIdPositiveDBTest () throws IOException {
        Response<GetCategoryResponse> response = categoryService.getCategory(2).execute();
        assert response.body() != null;
        List<Product> listProductsByRest = response.body().getProducts();
        example.createCriteria().andCategory_idEqualTo(2L);
        List<Products> listProductsBySql = productsMapper.selectByExample(example);
        assertThat(listProductsByRest.size(), equalTo(listProductsBySql.size()));
    }

}