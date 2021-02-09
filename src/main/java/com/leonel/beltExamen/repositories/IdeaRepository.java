package com.leonel.beltExamen.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.leonel.beltExamen.models.Idea;

@Repository
public interface IdeaRepository extends CrudRepository<Idea, Long>{
	Iterable<Idea> findAllByOrderByLikesAsc();
	Iterable<Idea> findAllByOrderByLikesDesc();
}
