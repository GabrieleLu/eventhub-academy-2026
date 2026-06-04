package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.TagRequestDTO;
import com.gabriele.eventhub.entity.Tag;
import com.gabriele.eventhub.service.TagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<Tag> create(@Valid @RequestBody TagRequestDTO dto) {
        return ResponseEntity.ok(tagService.create(dto));
    }
}