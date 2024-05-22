package api;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import dto.CarDto;
import dto.ErrorMessageDtoString;
import dto.RegistrationBodyDto;
import dto.TokenDto;
import org.testng.annotations.BeforeSuite;

import static com.jayway.restassured.RestAssured.given;

public class CarController implements BaseApi{

    public String token = "";

    @BeforeSuite
    public void getTokenForCarController(){
        RegistrationBodyDto registrationBodyDto = RegistrationBodyDto.builder()
                .username(EMAIL)
                .password(PASSWORD)
                .build();
      token =  given()
                .body(registrationBodyDto)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL+LOGIN_URL)
                .thenReturn()
              .getBody()
              .as(TokenDto.class)
              .getAccessToken();
        System.out.println("token ---> "+token);
    }

    private Response addNewCarResponse(CarDto car, String token){
        return given()
                .body(car)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .post(BASE_URL+ADD_NEW_CAR_URL)
                .thenReturn();
    }

    public int statusCodeAddNewCarResponse(CarDto carDto, String token){
        return addNewCarResponse(carDto, token).getStatusCode();
    }

    public ErrorMessageDtoString bodyNegativeAddNewCarResponse(CarDto carDto, String token){
        return addNewCarResponse(carDto, token).getBody().as(ErrorMessageDtoString.class);
    }
}
