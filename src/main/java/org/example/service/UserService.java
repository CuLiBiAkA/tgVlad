package org.example.service;

import org.example.model.User;
import org.example.repository.DataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private DataBase repository;

    public void saveUser(User user){
        repository.save(user);
    }

    public void deleteUser(User user){
        repository.delete(user);
    }

    public void getUserById(Long id){
        repository.getUserById(id);
    }
    public List<User> getUserList(){
        return List.of(new User(1L,"Хуисос","123","123"));
    }
}
