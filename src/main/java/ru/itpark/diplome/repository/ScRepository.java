package ru.itpark.diplome.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itpark.diplome.domain.Picture;

import java.util.List;

public interface ScRepository extends CrudRepository<Picture,Long> {

    List<Picture> findByTags(String tags);
}
