package com.employee.employeemicroservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PropertySource {

    @Value("${file.location}")
    private String filePath;
}
