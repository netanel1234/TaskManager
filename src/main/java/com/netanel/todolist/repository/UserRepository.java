package com.netanel.todolist.repository;

import com.netanel.todolist.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
