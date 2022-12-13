package com.company.umis.service;

import com.company.umis.model.User;
import com.company.umis.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {

        Optional<User> savedUser = userRepository.findByEmail(user.getEmail());
        if(savedUser.isPresent()){
            System.out.println("User already exist with given email:" + user.getEmail());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(long id) {
//        return userRepository.findById(id);
        Optional<User> findById = userRepository.findById(id);
        if(findById.isPresent()) {
            User user = findById.get();
            return Optional.of(user);
        }
        return null;
    }

    @Override
    public User updateUser(User savedUser) {
        return userRepository.save(savedUser);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
