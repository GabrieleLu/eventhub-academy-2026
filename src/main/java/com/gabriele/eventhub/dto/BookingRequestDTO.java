package com.gabriele.eventhub.dto;

import com.gabriele.eventhub.entity.TicketType;
import jakarta.validation.constraints.NotNull;

public class BookingRequestDTO {

    @NotNull(message = "Il tipo di biglietto è obbligatorio")
    private TicketType type;

    public TicketType getType() { return type; }
    public void setType(TicketType type) { this.type = type; }
}