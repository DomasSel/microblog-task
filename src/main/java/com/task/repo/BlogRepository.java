package com.task.repo;

import org.springframework.data.repository.CrudRepository;

import com.task.entity.Blog;

public interface BlogRepository extends CrudRepository<Blog, Long> {
	Blog getById(long id);
}
