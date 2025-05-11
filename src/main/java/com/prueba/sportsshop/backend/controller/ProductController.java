package com.prueba.sportsshop.backend.controller;

import com.prueba.sportsshop.backend.dto.ProductDto;
import com.prueba.sportsshop.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> listAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
