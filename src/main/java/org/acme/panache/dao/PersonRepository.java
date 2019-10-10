package org.acme.panache.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.acme.panache.model.Person;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {
	
	@Inject
	EntityManager em;
	
	public Person findByName(String name) {
		return find("name", name).firstResult();
	}
	
	@Transactional
	public void update(@Valid Person person) {
		
		Person existed = findByName(person.name);
		
		if(existed == null) {
			throw new PersistenceException("Couldn't find person to update");
		}
		
		person.id = existed.id;
		
		em.merge(person);
	}
}
