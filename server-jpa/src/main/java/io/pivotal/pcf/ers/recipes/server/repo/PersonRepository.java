package io.pivotal.pcf.ers.recipes.server.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import io.pivotal.pcf.ers.recipes.server.model.Person;

@RepositoryRestResource(collectionResourceRel = "persons", path = "persons")
public interface PersonRepository extends JpaRepository<Person, Long> {
	
    @RestResource(path = "name", rel = "name")
    Page<Person> findByNameIgnoreCase(@Param("q") String name, Pageable pageable);

    @RestResource(path = "nameContains", rel = "nameContains")
    Page<Person> findByNameContainsIgnoreCase(@Param("q") String name, Pageable pageable);

}
