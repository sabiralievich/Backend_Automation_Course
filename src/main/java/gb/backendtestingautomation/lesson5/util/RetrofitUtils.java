package gb.backendtestingautomation.lesson5.util;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitUtils {
//     static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//     static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public static Retrofit getRetrofit() {
//            logging.setLevel(Level.BODY);
//            httpClient.addInterceptor(logging);
        return new Retrofit.Builder()
                .baseUrl(ConfigUtils.getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
//                .client(httpClient.build())
                .build();
    }
}
