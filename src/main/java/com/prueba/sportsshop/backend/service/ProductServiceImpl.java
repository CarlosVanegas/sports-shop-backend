package com.prueba.sportsshop.backend.service;

import com.prueba.sportsshop.backend.dto.ProductDto;
import com.prueba.sportsshop.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    @Override
    public List<ProductDto> findAll() {
        return productRepo.findAll().stream()
                .map(p -> ProductDto.builder()
                        .id(p.getId())
                        .imageUrl(p.getImageUrl())
                        .description(p.getDescription())
                        .price(p.getPrice())
                        .quantityAvailable(p.getQuantityAvailable())
                        .createdAt(p.getCreatedAt())
                        .build()
                )
                .toList();
    }
}
