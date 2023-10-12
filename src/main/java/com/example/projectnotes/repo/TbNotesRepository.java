package com.example.projectnotes.repo;

import com.example.projectnotes.model.TbNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TbNotesRepository extends JpaRepository<TbNotes, Integer>, JpaSpecificationExecutor<TbNotes> {
    Optional<TbNotes> findByTitle(String title);
}
