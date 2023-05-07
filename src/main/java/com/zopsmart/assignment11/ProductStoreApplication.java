package com.zopsmart.assignment11;

import com.zopsmart.assignment11.swaggerconfig.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@Import(SwaggerConfig.class)
@EnableSwagger2
public class ProductStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductStoreApplication.class, args);
    }

}
