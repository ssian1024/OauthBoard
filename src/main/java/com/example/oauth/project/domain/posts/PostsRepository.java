package com.example.oauth.project.domain.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    Page<Posts> findAllDesc(Pageable pageable);

    @Modifying
    @Query("update Posts p set p.view = p.view + 1 where p.id = :id")
    int updateView(Long id);
}

