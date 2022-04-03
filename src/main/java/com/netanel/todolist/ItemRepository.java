package com.netanel.todolist;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer> {

    @Query(value = "SELECT task FROM item WHERE userid = ?1", nativeQuery = true)
    Iterable<Item> findItemsByUserId(Integer userid);

}
