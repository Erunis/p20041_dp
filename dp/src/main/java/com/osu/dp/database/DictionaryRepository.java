package com.osu.dp.database;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DictionaryRepository extends CrudRepository<Dictionary, Long> {
    List<Dictionary> findAll();
}
