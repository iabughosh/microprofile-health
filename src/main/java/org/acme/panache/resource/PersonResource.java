package org.acme.panache.resource;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.panache.dao.PersonRepository;
import org.acme.panache.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/person")
@ApplicationScoped
public class PersonResource {

	private static final Logger LOG = LoggerFactory.getLogger(PersonResource.class);
	
	@Inject
	PersonRepository personRepository;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response post(@Valid Person person) {
		
		person.persist();
		return Response.status(Status.CREATED).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Transactional
	public Response formPost(@FormParam("name") String name,
							 @FormParam("birth") String birth,
							 @FormParam("maritalStatus") String maritalStatus) {
		
		LOG.info("Name : {}, Birth : {}, Marital Status: {}", name, birth, maritalStatus);
		
		return Response.status(Status.CREATED).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(@Valid Person person) {
		
		personRepository.update(person);
		return Response.status(Status.ACCEPTED).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		
		List<Person> allPersons = personRepository.listAll();
		return Response.ok(allPersons, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{name}")
	public Response getByName(@PathParam("name") String name) {
		
		Person person = personRepository.findByName(name);
		if(person == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		return Response.ok(person, MediaType.APPLICATION_JSON).build();
	}
	
	@DELETE
	@Transactional
	public Response deleteAll() {
		
		personRepository.deleteAll();
		return Response.status(Status.OK).build();
	}
	
}
