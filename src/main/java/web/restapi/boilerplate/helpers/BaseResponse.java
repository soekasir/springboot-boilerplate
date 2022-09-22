package web.restapi.boilerplate.helpers;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    @Getter @Setter
    private Boolean success=false;
    @Getter @Setter
    private String message="tidak ada pesan";

    @Getter @Setter
    private HashMap<String, Object> data=new HashMap<String, Object>();

    public void put(String key, Object value){
      data.put(key, value);
    }
}
