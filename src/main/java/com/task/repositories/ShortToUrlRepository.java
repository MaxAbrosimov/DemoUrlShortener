package com.task.repositories;

import com.task.domain.ShortToUrl;
import org.springframework.data.repository.CrudRepository;

public interface ShortToUrlRepository extends CrudRepository<ShortToUrl, Long> {
}
