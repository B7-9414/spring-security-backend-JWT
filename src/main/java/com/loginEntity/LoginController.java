/*
package com.loginEntity;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LoginController {

	@Autowired
	private Repo repo;

	@GetMapping("/all/login/users")
	public List<Login> getAll() {
		return repo.findAll();
	}

	@GetMapping("/{username}/search")
	public Login getAlllogin(@PathVariable String username) {
		return repo.findByUsername(username);
	}
	
	
	
	
	
	
	@GetMapping(value= "/")
	public String home() {
		System.out.println("Showing home page to the user.");
		return "home";
	}
	
	@GetMapping(value= "/login" )
	public String login() {
		System.out.println("Showing login page to the user.");
		return "login";
	}
	
	@GetMapping("/{username}/login/{id}")
	public Login getlogin(@PathVariable String username, @PathVariable long id) {
		return repo.findById(id).get();
	}

	@DeleteMapping("/{username}/login/{id}")
	public ResponseEntity<Void> deletelogin(@PathVariable String username, @PathVariable long id) {
		repo.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{username}/login/{id}")
	public ResponseEntity<Login> updatelogin(@PathVariable String username, @PathVariable long id,
			@RequestBody Login login) {
		Login loginUpdated = repo.save(login);
		return new ResponseEntity<Login>(login, HttpStatus.OK);
	}

	@PostMapping("/{username}/login")
	public ResponseEntity<Void> createTodo(@PathVariable String username, @RequestBody Login login) {
		login.setUsername(username);
		Login createdlogin = repo.save(login);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdlogin.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

}
*/

