package org.example.service;

import org.example.model.User;
import org.example.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao repository;

    public void saveUser(User user){
        repository.save(user);
    }

    public void deleteUser(User user){
        repository.delete(user);
    }

    public void getUserById(Long id){
        repository.getUserById(id);
    }

    public User getUserByChatId(Long id) {
        return repository.getUserByChatId(id);
    }

    public Iterable<User> getAllUser() {
        return repository.findAll();
    }
}
