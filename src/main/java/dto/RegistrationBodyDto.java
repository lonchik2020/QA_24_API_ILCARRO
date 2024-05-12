package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class RegistrationBodyDto {//body of request for registration
    private String username; //*    string
    private String password; // string
    private String firstName;// string
    private String lastName; // string


}
