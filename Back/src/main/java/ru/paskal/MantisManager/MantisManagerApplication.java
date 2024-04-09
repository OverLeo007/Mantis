package ru.paskal.MantisManager;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class MantisManagerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MantisManagerApplication.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    var mapper = new ModelMapper();
    mapper.getConfiguration()
        .setFieldMatchingEnabled(true)
        .setSkipNullEnabled(true)
        .setFieldAccessLevel(PRIVATE);
    return mapper;
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


}
