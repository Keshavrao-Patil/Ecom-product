package com.edu.Ecom_product.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
    private String productName;
    private BigDecimal price;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate releaseDate;
    private String description;
    private String brand;

//    private String imageName;
//    private String imageType;
//
//    @Lob //Large Object
//    private byte[] imageData;
}
