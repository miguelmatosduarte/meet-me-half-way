package meet.me.half.way;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import utils.SkyScannerAPIUtils;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		SkyScannerAPIUtils skyScannerAPIUtils = new SkyScannerAPIUtils();
		skyScannerAPIUtils.browseQuotes("PT", "EUR", "pt-PT", "PORT", "anywhere", "2020-09-01");
		skyScannerAPIUtils.pollSessionResults();
	}

/*	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}*/

}
