package com.prueba.sportsshop.backend.service;

import com.prueba.sportsshop.backend.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();
}
