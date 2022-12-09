package com.company.umis.service;

import com.company.umis.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public abstract class UserService {
    public abstract User saveUser(User user);
    public abstract List<User> getAllUsers();
    public abstract Optional<User> getUserById(long id);
    public abstract User updateUser(User updatedUser);
    public abstract void deleteUser(long id);
}
