package web.restapi.boilerplate.helpers;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    private Boolean success=false;
    private String message="tidak ada pesan";
    private Object data=null;
}
