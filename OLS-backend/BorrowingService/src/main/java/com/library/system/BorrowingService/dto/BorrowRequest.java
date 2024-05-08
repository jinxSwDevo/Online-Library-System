package com.library.system.BorrowingService.dto;

import java.time.LocalDate;

import com.library.system.BorrowingService.entity.RequestStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequest {
    
    private Long userId;
    private Long bookId;
    private RequestStatus status;
    private LocalDate borrowDate;
    private LocalDate returnDate;

}
