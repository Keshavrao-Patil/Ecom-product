package com.edu.Ecom_product.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotNull(message = "Product name cannot be null")
    private String productName;

    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate releaseDate;

    @NotBlank(message = "Description cannot be blank")
    private String description;
    private String brand;

//    private String imageName;
//    private String imageType;
//
//    @Lob //Large Object
//    private byte[] imageData;
}
