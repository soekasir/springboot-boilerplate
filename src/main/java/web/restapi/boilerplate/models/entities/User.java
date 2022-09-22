package web.restapi.boilerplate.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.restapi.boilerplate.models.enums.Role;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String email;
    private String username;
    private String password; //hiden
    private String validator; //hiden

    @Column(columnDefinition = "boolean default false")
    private Boolean isValidate;

    @Enumerated(EnumType.STRING)
    @Column(length = 32, columnDefinition = "varchar(32) default 'USER'")
    private Role role;

    @CreatedDate
    private Date createAt;
    @LastModifiedDate
    private Date updateAt;

    public HashMap toResponseData(){
      HashMap data=new HashMap<String,Object>();

      data.put("id", id);
      data.put("email", email);
      data.put("username",username);
      data.put("isValidate",isValidate);
      data.put("role",role);

      return data;
    }
}
