package za.co.onespark.excercise.guidetothegalaxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import za.co.onespark.excercise.guidetothegalaxy.usecases.ProcessTransactions;
import za.co.onespark.excercise.guidetothegalaxy.utils.InputDataParser;
import za.co.onespark.excercise.guidetothegalaxy.utils.InputFileReader;
import za.co.onespark.excercise.guidetothegalaxy.utils.RomanNumeralConverter;

import java.util.List;

@SpringBootApplication
public class GuideToTheGalaxyApplication implements CommandLineRunner {
	@Autowired
	private ProcessTransactions processTransactions;

	public static void main(String[] args) {
		SpringApplication.run(GuideToTheGalaxyApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			List<String> answers = processTransactions.execute("testinput.txt");
			answers.forEach(System.out::println);
		} catch (Exception e) {
			System.out.println("An error occured :: " + e.getMessage());
		}
	}

}
