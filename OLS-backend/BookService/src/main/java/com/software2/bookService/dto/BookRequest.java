package com.software2.bookService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    private Integer rackNumber;
    private String ISBN;
    private String title;
    private String author;
    private int nocopies;
}