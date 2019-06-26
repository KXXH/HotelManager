package com.shixi.hotelmanager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "shixi.hotelmanager")
public class SecurityProperties {
    private LoginType loginType = LoginType.REDIRECT;

}
