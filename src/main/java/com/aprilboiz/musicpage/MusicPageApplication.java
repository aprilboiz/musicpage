package com.aprilboiz.musicpage;

import com.aprilboiz.musicpage.emotion.EmotionServiceImpl;
import com.aprilboiz.musicpage.emotion.dto.EmotionRequest;
import com.aprilboiz.musicpage.person.Person;
import com.aprilboiz.musicpage.person.PersonServiceImpl;
import com.aprilboiz.musicpage.role.Role;
import com.aprilboiz.musicpage.role.RoleServiceImpl;
import com.aprilboiz.musicpage.user.User;
import com.aprilboiz.musicpage.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@SpringBootApplication
public class MusicPageApplication {
	private static final Logger logger = LoggerFactory.getLogger(MusicPageApplication.class);
	private final UserRepository userRepository;
	private final RoleServiceImpl roleService;
	private final PasswordEncoder passwordEncoder;
	private final EmotionServiceImpl emotionService;
	private final PersonServiceImpl personService;

    public MusicPageApplication(UserRepository userRepository, RoleServiceImpl roleService, PasswordEncoder passwordEncoder, EmotionServiceImpl emotionService, PersonServiceImpl personService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.emotionService = emotionService;
        this.personService = personService;
    }

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	public CommandLineRunner addUsers(){
		return args -> {
			logger.info("Adding roles");
			roleService.save(new Role("USER"));
			roleService.save(new Role("ADMIN"));
			logger.info("Showing added roles");
			roleService.findAll().forEach(role -> logger.info(role.toString()));
			logger.info("Adding users");
			Role userRole = roleService.findByName("USER");
			Role adminRole = roleService.findByName("ADMIN");
			User user = new User("user", passwordEncoder.encode("user"), Set.of(userRole));
			userRepository.save(user);
			Person p = new Person("Alice", "user@gmail.com", "1234567890", "1234 Wonderland");
			personService.save(p);
			User userEmail = new User("user@gmail.com", passwordEncoder.encode("12345678"), Set.of(userRole));
			userEmail.setPerson(p);
			userRepository.save(userEmail);
			User admin = new User("admin", passwordEncoder.encode("admin"), Set.of(adminRole));
			userRepository.save(admin);
			User superUser = new User("superuser", passwordEncoder.encode("superuser"), Set.of(userRole, adminRole));
			userRepository.save(superUser);
		};
	}

	@Bean
	public CommandLineRunner addEmotions(){
		return args -> {
			logger.info("Adding emotions");
			emotionService.save(new EmotionRequest("Happy"));
			emotionService.save(new EmotionRequest("Sad"));
			emotionService.save(new EmotionRequest("Angry"));
			emotionService.save(new EmotionRequest("Excited"));
			emotionService.save(new EmotionRequest("Calm"));
			emotionService.save(new EmotionRequest("Tired"));
			logger.info("Showing added emotions");
			emotionService.findAll().forEach(emotion -> logger.info(emotion.toString()));
		};
	}

    public static void main(String[] args) {
		SpringApplication.run(MusicPageApplication.class, args);
	}

}
