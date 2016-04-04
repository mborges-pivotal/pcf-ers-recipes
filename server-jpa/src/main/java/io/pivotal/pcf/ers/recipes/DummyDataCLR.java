package io.pivotal.pcf.ers.recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import io.pivotal.pcf.ers.recipes.server.repo.PersonRepository;

@Component
public class DummyDataCLR  implements CommandLineRunner {
	
	@Autowired
	private PersonRepository personRepository;

	@Override
	public void run(String... arg0) throws Exception {

		// MMB: Left here because I like the new Java 8 Stream API. A reminder for further research
		//Stream.of("Roy", "Darrel").forEach(a -> attendeeRepository.save(new Attendee(a)));
		personRepository.findAll().forEach(System.out::println);
		personRepository.findByNameIgnoreCase("Roy", new PageRequest(1, 10)).forEach(System.out::println);
	}
	
}
