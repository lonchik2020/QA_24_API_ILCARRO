package rest;


import api.AuthenticationController;
import dto.ErrorMessageDtoString;
import dto.RegistrationBodyDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Random;

public class RegistrationTestsRest extends AuthenticationController {

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void registrationPositiveTest(){
        RegistrationBodyDto registrationBodyDto = RegistrationBodyDto.builder()
                .firstName("Frodo")
                .lastName("Baggins")
                .username("baggins"+new Random().nextInt(1000)+"@gmail.com")
                .password("ZDv12345!")
                .build();
        Assert.assertEquals(statusCodeResponseRegistrationLogin(registrationBodyDto,REGISTRATION_URL), 200);

    }

    @Test
    public void registrationNegativeTest_wrongEmail(){
        RegistrationBodyDto registrationBodyDto = RegistrationBodyDto.builder()
                .firstName("Frodo")
                .lastName("Baggins")
                .username("baggins"+new Random().nextInt(1000)+"gmail.com")
                .password("ZDv12345!")
                .build();
        //System.out.println(statusCodeResponseRegistrationLogin(registrationBodyDto, REGISTRATION_URL));
        ErrorMessageDtoString errorMessage = bodyNegativeResponseRegLog(registrationBodyDto, REGISTRATION_URL);
        softAssert.assertEquals(errorMessage.getStatus(), 400);
        softAssert.assertEquals(errorMessage.getError(), "Bad Request");
        softAssert.assertTrue(errorMessage.getMessage().toString().contains("must be a well-formed email address"));
        softAssert.assertAll();

    }

}
