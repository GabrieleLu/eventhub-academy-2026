package com.gabriele.eventhub.dto;

import com.gabriele.eventhub.entity.TicketStatus;
import com.gabriele.eventhub.entity.TicketType;
import java.math.BigDecimal;

public class TicketResponseDTO {

    private Long id;
    private String eventTitle;
    private String username;
    private TicketType type;
    private BigDecimal pricePaid;
    private TicketStatus status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public TicketType getType() { return type; }
    public void setType(TicketType type) { this.type = type; }

    public BigDecimal getPricePaid() { return pricePaid; }
    public void setPricePaid(BigDecimal pricePaid) { this.pricePaid = pricePaid; }

    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }
}