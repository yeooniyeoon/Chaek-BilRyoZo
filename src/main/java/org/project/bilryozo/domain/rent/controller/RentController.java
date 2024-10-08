package org.project.bilryozo.domain.rent.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.bilryozo.domain.rent.service.RentService;
import org.project.bilryozo.domain.users.dto.response.MessageResponseDto;
import org.project.bilryozo.global.exception.dto.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rent")
public class RentController {
    private final RentService rentService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<MessageResponseDto>> createRent(
            @PathVariable("id") Long id,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servletRequest.getServletPath(),
                        rentService.createRent(id)
                ));
    }
}
