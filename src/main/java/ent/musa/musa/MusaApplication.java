package ent.musa.musa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MusaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusaApplication.class, args);
	}

	@GetMapping("/home")
	public String home(@RequestParam(value = "name", defaultValue = "World")
			String name
	) {
		return String.format("%s", name);
	}

}
