package com.example.snipprv2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

}

@Entity
public class User {

    private @Id @GeneratedValue Long id;
    private String email;
    private String password;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @RestController
    public static class UserController {

        private final UserRepository repository;

        public UserController(UserRepository repository) {
            this.repository = repository;
        }

        @GetMapping("/users")
        List<User> all() {
            return repository.findAll();
        }

        @GetMapping("/users/{id}")
        User one(@PathVariable Long id) {
            return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        }

        @PostMapping("/users")
        User newUser(@RequestBody User newUser) {
            return  repository.save(newUser);
        }

        @PutMapping("/users/{id}")
        User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
            return repository.findById(id).map(user -> {
                user.setEmail(newUser.getEmail());
                user.setPassword(newUser.getPassword());
                return repository.save(user);
            }).orElseGet(() -> {
                newUser.setId(id);
                return repository.save(newUser);
            });
        }

        @DeleteMapping("/users/{id}")
        void deleteUser(@PathVariable Long id) {
            repository.deleteById(id);
        }

    }
}
