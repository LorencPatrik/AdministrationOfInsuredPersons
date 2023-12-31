package cz.lorsoft.administrationOfTheInsureds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ApplicationMain {
    public static void main(String[]args){
        SpringApplication.run(ApplicationMain.class, args);
    }
}
