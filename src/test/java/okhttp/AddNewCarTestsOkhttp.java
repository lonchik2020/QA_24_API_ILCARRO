package okhttp;

import api.BaseApi;
import com.google.gson.Gson;
import dto.CarDto;
import dto.RegistrationBodyDto;
import dto.ResponseMessageDto;
import dto.TokenDto;
import dto.enumclasses.Fuel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.Random;

public class AddNewCarTestsOkhttp implements BaseApi {
    OkHttpClient okHttpClient = new OkHttpClient();
    Gson gson = new Gson();
    SoftAssert softAssert = new SoftAssert();
    TokenDto token;
    @BeforeClass
    public void registrationPositiveTest(){///v1/user/registration/usernamepassword
        RegistrationBodyDto registrationBodyDto = RegistrationBodyDto.builder()
                .username(EMAIL)
                .password(PASSWORD)
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(registrationBodyDto), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try{
            response = okHttpClient.newCall(request).execute();
            System.out.println(response.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            token = gson.fromJson(response.body().string(), TokenDto.class );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(token.getAccessToken());
    }

    @Test
    public void addNewCarPositiveTest(){
        int i = new Random().nextInt(1000)+1000;
        CarDto car = CarDto.builder()
                .serialNumber("444-"+i)
                .manufacture("Toyota")
                .model("Corola")
                .year("2021")
                .fuel(Fuel.DIESEL.getFuel())
                .seats(4)
                .carClass("A")
                .pricePerDay(100.23)
                .city("Haifa")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(car), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+ADD_NEW_CAR_URL)
                .addHeader("Authorization",token.getAccessToken())
                .post(requestBody)
                .build();
        Response response;
        String responseJson;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(response==null)
            Assert.fail("response is null");
        else if(response.code() == 200){
            try {
                responseJson = response.body().string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ResponseMessageDto responseMessage = gson.fromJson(responseJson, ResponseMessageDto.class);
            Assert.assertEquals(responseMessage.getMessage(), "Car added successfully");
        }else Assert.fail("Error, response code ---> "+response.code());

    }

}
