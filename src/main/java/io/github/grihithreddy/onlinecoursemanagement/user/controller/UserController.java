package io.github.grihithreddy.onlinecoursemanagement.user.controller;

import io.github.grihithreddy.onlinecoursemanagement.user.model.User;
import io.github.grihithreddy.onlinecoursemanagement.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("databaseUserService") UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     *
     * @param user The user information to create
     * @return The created user with HTTP status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User created = userService.createUser(user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Retrieves all users from the system.
     *
     * @return ResponseEntity containing a list of all users with HTTP status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id the unique identifier of the user
     * @return ResponseEntity containing the user with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the user doesn't exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @Positive int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates the name of a specific user.
     *
     * @param id   the unique identifier of the user to update
     * @param name the new name (must be between 3 and 50 characters)
     * @return ResponseEntity containing the updated user with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the user doesn't exist
     */
    @PutMapping("/{id}/name")
    public ResponseEntity<User> updateUserName(
            @PathVariable @Positive int id,
            @RequestParam @Size(min = 3, max = 50) String name
    ) {
        User updated = userService.updateUserName(id, name);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates the email of a specific user.
     *
     * @param id    the unique identifier of the user to update
     * @param email the new email (must be a valid format and length constraints if needed)
     * @return ResponseEntity containing the updated user with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the user doesn't exist
     */
    @PutMapping("/{id}/email")
    public ResponseEntity<User> updateUserEmail(
            @PathVariable @Positive int id,
            @RequestParam String email
    ) {
        User updated = userService.updateUserEmail(id, email);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates the password of a specific user.
     *
     * @param id       the unique identifier of the user to update
     * @param password the new password (should follow security constraints ideally)
     * @return ResponseEntity containing the updated user with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the user doesn't exist
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<User> updateUserPassword(
            @PathVariable @Positive int id,
            @RequestParam String password
    ) {
        User updated = userService.updateUserPassword(id, password);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Deletes a specific user from the system.
     *
     * @param id the unique identifier of the user to delete
     * @return ResponseEntity with a success message and HTTP status 200 (OK) if deleted,
     * or an error message and HTTP status 404 (Not Found) if the user doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable @Positive int id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}
