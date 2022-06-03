package com.task.repositories;

import com.task.domain.UrlToShort;
import org.springframework.data.repository.CrudRepository;

public interface UrlToShortRepository extends CrudRepository<UrlToShort, String> {
}
