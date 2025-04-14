package io.github.grihithreddy.onlinecoursemanagement.user.service;

import io.github.grihithreddy.onlinecoursemanagement.user.exception.UserNotFoundException;
import io.github.grihithreddy.onlinecoursemanagement.user.exception.UserValidationException;
import io.github.grihithreddy.onlinecoursemanagement.user.model.User;
import io.github.grihithreddy.onlinecoursemanagement.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("databaseUserService")
public class DatabaseUserService  implements UserService {
    private final UserRepository userRepository;



    public DatabaseUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User createUser(User user) throws UserValidationException {
        if(user ==null){
            throw new UserValidationException("User is null");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() throws UserNotFoundException {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        return users;
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User updateUserName(int id, String newName) throws UserValidationException {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserValidationException("User not found with id: " + id));
        if(newName==null||newName.isEmpty()){
            throw new UserValidationException("Name is null or empty");
        }
        user.setUsername(newName);
        return userRepository.save(user);
    }

    @Override
    public User updateUserEmail(int id, String newEmail) throws UserValidationException {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserValidationException("User not found with id: " + id));
        if(newEmail==null||newEmail.isEmpty()){
            throw new UserValidationException("Email is null or empty");
        }
        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    @Override
    public User updateUserPassword(int id, String newPassword) throws UserValidationException {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserValidationException("User not found with id: " + id));
        if(newPassword==null||newPassword.isEmpty()){
            throw new UserValidationException("Password is null or empty");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUserPhone(int id, String newPhone) throws UserValidationException {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserValidationException("User not found with id: " + id));
        if(newPhone==null||newPhone.isEmpty()){
            throw new UserValidationException("please, provide a valid phone number");
        }
        user.setPhone(newPhone);
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(int id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User: " + id+ " not found"));
        userRepository.delete(user);
        return true;
    }
}
