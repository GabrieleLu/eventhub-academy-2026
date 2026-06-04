package com.gabriele.eventhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TagRequestDTO {

    @NotBlank(message = "Nome tag obbligatorio")
    @Size(min = 2, max = 50, message = "Nome tag deve essere tra 2 e 50 caratteri")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}