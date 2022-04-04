package com.netanel.todolist.repository;

import com.netanel.todolist.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer> {

    @Query(value = "SELECT * FROM item WHERE userid = ?1", nativeQuery = true)
    List<Item> findItemsByUserId(Integer userid);

}
