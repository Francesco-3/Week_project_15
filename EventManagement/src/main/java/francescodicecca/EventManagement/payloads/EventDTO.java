package francescodicecca.EventManagement.payloads;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record EventDTO(
        @NotBlank(message = "Il titolo è obbligatorio")
        @Size(max = 100, message = "Il titolo deve avere al massimo 100 caratteri")
        String title,

        @NotBlank(message = "La descrizione è obbligatoria")
        @Size(max = 1000, message = "La descrizione deve avere al massimo 1000 caratteri")
        String description,

        @FutureOrPresent(message = "La data dell'evento deve essere presente o futura")
        LocalDateTime date,

        @NotBlank(message = "Il luogo è obbligatorio")
        String place,

        @Min(value = 1, message = "Deve esserci almeno un posto disponibile")
        Integer availablePlaces
) {}
