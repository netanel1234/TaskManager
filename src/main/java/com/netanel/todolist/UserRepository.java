package com.netanel.todolist;

import com.netanel.todolist.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
