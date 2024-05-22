package rest;

import api.CarController;
import dto.CarDto;
import dto.ErrorMessageDtoString;
import dto.enumclasses.Fuel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class AddNewCarTestsRest extends CarController {

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
        Assert.assertEquals(statusCodeAddNewCarResponse(car, token), 200);
    }

    @Test
    public void addNewCarNegativeTest_emptyFieldSerialNumber(){
        int i = new Random().nextInt(1000)+1000;
        CarDto car = CarDto.builder()
                .manufacture("Toyota")
                .model("Corola")
                .year("2021")
                .fuel(Fuel.DIESEL.getFuel())
                .seats(4)
                .carClass("A")
                .pricePerDay(100.23)
                .city("Haifa")
                .build();
        ErrorMessageDtoString errorMessage = bodyNegativeAddNewCarResponse(car, token);
        Assert.assertEquals(errorMessage.getError(), "Bad Request");
    }

    @Test
    public void addNewCarNegativeTest_wrongToken(){
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
        Assert.assertEquals(statusCodeAddNewCarResponse(car, ""), 401);
    }

}
