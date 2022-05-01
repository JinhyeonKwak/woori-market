package com.mayy5.admin.model.res;

import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import com.mayy5.admin.type.UserMetaType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRTO {

    @ApiModelProperty(value = "User ID", example = "admin", position = 1)
    private String id;

    @NotBlank
    @ApiModelProperty(value = "Password", required = true, example = "admin", position = 2)
    private String password;

    @ApiModelProperty(
        value = "A UserPropType map of key/value pairs",
        example = "{'MAIL': 'xxx@yyy.com', 'ROLE' : 'ROLE_MARKET_AGENT'}",
        dataType = "Map[String,String]", position = 4)
    private Map<UserMetaType, String> meta;

    @ApiModelProperty(hidden = true)
    private LocalDateTime createAt;

    @ApiModelProperty(hidden = true)
    private LocalDateTime updateAt;

}
