package br.com.fiap.epicbooks.books;

import br.com.fiap.epicbooks.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Books {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String title;

    @Size(min = 10)
    String description;

    @Min(1) @Max(100)
    Integer score;

    @Min(0) @Max(100)
    Integer status;

    @ManyToOne
    User user;

}

