package org.example.repository;

import org.example.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

    User getUserById(Long userId);

    User getUserByChatId(Long id);
}


