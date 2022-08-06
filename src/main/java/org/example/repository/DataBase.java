package org.example.repository;

import org.example.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataBase extends CrudRepository<User, Long> {

    User getUserById(Long userId);

}

