package example.aggregationbestcontents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AggregationBestContentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AggregationBestContentsApplication.class, args);
	}

}
