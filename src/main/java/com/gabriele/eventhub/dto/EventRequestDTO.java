package com.gabriele.eventhub.dto;

import com.gabriele.eventhub.entity.EventStatus;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class EventRequestDTO {

    @NotBlank(message = "Titolo obbligatorio")
    @Size(min = 3, max = 200, message = "Titolo deve essere tra 3 e 200 caratteri")
    private String title;

    @NotBlank(message = "Descrizione obbligatoria")
    @Size(min = 10, max = 2000, message = "Descrizione deve essere tra 10 e 2000 caratteri")
    private String description;

    @NotNull(message = "Data inizio obbligatoria")
    private LocalDateTime startDate;

    @NotNull(message = "Data fine obbligatoria")
    private LocalDateTime endDate;

    @NotNull(message = "Prezzo standard obbligatorio")
    @DecimalMin(value = "0.01", message = "Prezzo standard deve essere maggiore di 0")
    private BigDecimal standardPrice;

    @NotNull(message = "Prezzo VIP obbligatorio")
    @DecimalMin(value = "0.01", message = "Prezzo VIP deve essere maggiore di 0")
    private BigDecimal vipPrice;

    @NotNull(message = "Stato evento obbligatorio")
    private EventStatus status;

    @NotNull(message = "Sede obbligatoria")
    private Long venueId;

    private List<Long> tagIds;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public BigDecimal getStandardPrice() { return standardPrice; }
    public void setStandardPrice(BigDecimal standardPrice) { this.standardPrice = standardPrice; }

    public BigDecimal getVipPrice() { return vipPrice; }
    public void setVipPrice(BigDecimal vipPrice) { this.vipPrice = vipPrice; }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }

    public Long getVenueId() { return venueId; }
    public void setVenueId(Long venueId) { this.venueId = venueId; }

    public List<Long> getTagIds() { return tagIds; }
    public void setTagIds(List<Long> tagIds) { this.tagIds = tagIds; }
}