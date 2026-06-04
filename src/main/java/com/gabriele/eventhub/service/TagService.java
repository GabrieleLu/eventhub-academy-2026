package com.gabriele.eventhub.service;

import com.gabriele.eventhub.dto.TagRequestDTO;
import com.gabriele.eventhub.entity.Tag;
import com.gabriele.eventhub.exception.ValidationException;
import com.gabriele.eventhub.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag create(TagRequestDTO dto) {
        if (tagRepository.findByName(dto.getName()).isPresent()) {
            throw new ValidationException("Tag già esistente");
        }

        Tag tag = new Tag();
        tag.setName(dto.getName());
        return tagRepository.save(tag);
    }
}