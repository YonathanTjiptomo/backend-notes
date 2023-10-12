package com.example.projectnotes.controller;

import com.example.projectnotes.model.TbNotes;
import com.example.projectnotes.repo.TbNotesRepository;
import com.example.projectnotes.request.NotesRequest;
import com.example.projectnotes.response.NotesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dataNotes")
public class NotesController {
    @Autowired
    TbNotesRepository tbNotesRepository;

    @PostMapping(value = {"/saveNote"}, produces = "application/json")
    public @ResponseBody NotesResponse saveNote (@RequestBody NotesRequest notesRequest) {
        TbNotes tbNotes = new TbNotes();
        String titleNotes = notesRequest.getTitle();
        String subtitleNotes = notesRequest.getSubtitle();
        tbNotes.setTitle(titleNotes);
        tbNotes.setSubtitle(subtitleNotes);
        tbNotesRepository.save(tbNotes);
        NotesResponse response = new NotesResponse();
        response.setTitle(titleNotes);
        response.setSubtitle(subtitleNotes);
        return response;
    }

    @GetMapping(value = {"/getNote"}, produces = "application/json")
    public List<TbNotes> getNote() {
        return tbNotesRepository.findAll();
    }

    @PutMapping(value = {"/updateNote"}, produces = "application/json")
    public TbNotes updateNote (@RequestParam("title") String title, @RequestParam("newTitle") String newTitle, @RequestParam("subtitle") String subtitle) {
        Optional<TbNotes> notes = tbNotesRepository.findByTitle(title);
        if (notes.isEmpty()) {
            return null;
        }
        TbNotes oldNotes = notes.get();
        oldNotes.setTitle(newTitle);
        oldNotes.setSubtitle(subtitle);
        return tbNotesRepository.save(oldNotes);
    }

    @DeleteMapping(value = {"/deleteNote"}, produces = "application/json")
    public TbNotes deleteNote (@RequestParam("title") String title) {
        Optional<TbNotes> notes = tbNotesRepository.findByTitle(title);
        if (notes.isEmpty()) {
            return null;
        }
        TbNotes currentNote = notes.get();
        tbNotesRepository.delete(currentNote);
        return currentNote;
    }
}
