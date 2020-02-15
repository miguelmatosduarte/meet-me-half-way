package meetmehalfway;

import meetmehalfway.model.skyscanner.response.BrowseQuotesResponse;
import meetmehalfway.utils.QuoteComparer;
import meetmehalfway.utils.SkyScannerAPIUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
/*		SkyScannerAPIUtils skyScannerAPIUtils = new SkyScannerAPIUtils();

		List<BrowseQuotesResponse> quotes = new ArrayList<>();
		quotes.add(skyScannerAPIUtils.browseQuotes("PT", "EUR", "pt-PT", "RIOA", "anywhere", "2020-07-02"));
		quotes.add(skyScannerAPIUtils.browseQuotes("PT", "EUR", "pt-PT", "MAD", "anywhere", "2020-07-02"));
		quotes.add(skyScannerAPIUtils.browseQuotes("PT", "EUR", "pt-PT", "RGN", "anywhere", "2020-07-02"));

		// BrowseQuotesResponse quotes = skyScannerAPIUtils.browseQuotes("PT", "EUR", "pt-PT", "PORT", "anywhere", "2020-09-01");
		QuoteComparer quoteComparer = new QuoteComparer(quotes);
		quoteComparer.compareQuotes();*/
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
