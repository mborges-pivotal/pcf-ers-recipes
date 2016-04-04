package io.pivotal.pcf.ers.recipes.server;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import io.pivotal.pcf.ers.recipes.Application;
import io.pivotal.pcf.ers.recipes.server.model.Person;
import io.pivotal.pcf.ers.recipes.server.repo.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {
	
	@Autowired
	private PersonRepository personRepository;

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void personRepository() throws ParseException {
		
		personRepository.deleteAll();
		
		Person a1 = new Person();
		a1.setName("Phil");
		a1.setEmailAddress("pberman@pivotal.io");
		personRepository.save(a1);
		
		a1 = new Person();
		a1.setName("Marcelo");
		a1.setEmailAddress("mborges@pivotal.io");
		personRepository.save(a1);
		
		List<Person> persons = personRepository.findAll();
		assertThat(persons.size(), is(2));
		
	}

}
