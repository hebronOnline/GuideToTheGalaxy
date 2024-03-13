package za.co.onespark.excercise.guidetothegalaxy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import za.co.onespark.excercise.guidetothegalaxy.usecases.ProcessTransactions;

import java.util.List;

@SpringBootApplication
public class GuideToTheGalaxyApplication implements CommandLineRunner {

	private final ProcessTransactions processTransactions;

	public GuideToTheGalaxyApplication(ProcessTransactions processTransactions) {
		this.processTransactions = processTransactions;
	}

	public static void main(String[] args) {
		SpringApplication.run(GuideToTheGalaxyApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			List<String> answers = processTransactions.execute("testinput.txt");
			if (!answers.isEmpty()) {
				System.out.println("================== Galaxy Transactions ================");
				answers.forEach(System.out::println);
			}
		} catch (Exception e) {
			System.out.println("An error occured :: " + e.getMessage());
		}
	}

}
