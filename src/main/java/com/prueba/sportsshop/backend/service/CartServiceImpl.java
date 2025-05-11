package com.prueba.sportsshop.backend.service;

import com.prueba.sportsshop.backend.dto.AddCartItemDto;
import com.prueba.sportsshop.backend.dto.CartItemDto;
import com.prueba.sportsshop.backend.entity.*;
import com.prueba.sportsshop.backend.exception.BadRequestException;
import com.prueba.sportsshop.backend.exception.ResourceNotFoundException;
import com.prueba.sportsshop.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final CartRepository cartRepo;
    private final CartItemRepository itemRepo;
    private final ProductRepository productRepo;

    private Cart getOrCreateCart(User user) {
        return cartRepo.findByUser(user)
                .orElseGet(() -> cartRepo.save(
                        Cart.builder()
                                .user(user)
                                .build()
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemDto> listCartItems(String userEmail) {
        User user = userService.findByEmail(userEmail);
        Optional<Cart> opt = cartRepo.findByUser(user);
        return opt
                .map(cart -> cart.getItems().stream().map(this::toDto).toList())
                .orElse(List.of());
    }


    @Override
    @Transactional
    public CartItemDto addItem(String userEmail, AddCartItemDto dto) {
        User user = userService.findByEmail(userEmail);
        Cart cart = getOrCreateCart(user);

        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(dto.getProductId()))
                .findFirst()
                .orElse(CartItem.builder().cart(cart).product(product).quantity(0).build());

        int newQty = item.getQuantity() + dto.getQuantity();
        if (newQty > product.getQuantityAvailable()) {
            throw new BadRequestException("Cantidad excede stock disponible");
        }
        item.setQuantity(newQty);
        CartItem saved = itemRepo.save(item);
        return toDto(saved);
    }

    @Override
    @Transactional
    public void removeItem(String userEmail, Integer itemId) {
        User user = userService.findByEmail(userEmail);
        Cart cart = getOrCreateCart(user);
        CartItem item = itemRepo.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Ítem no encontrado"));
        if (!item.getCart().getId().equals(cart.getId())) {
            throw new BadRequestException("Ítem no pertenece a tu carrito");
        }
        itemRepo.delete(item);
    }

    private CartItemDto toDto(CartItem i) {
        double price = i.getProduct().getPrice().doubleValue();
        return CartItemDto.builder()
                .itemId(i.getId())
                .productId(i.getProduct().getId())
                .productDescription(i.getProduct().getDescription())
                .imageUrl(i.getProduct().getImageUrl())
                .price(price)
                .quantity(i.getQuantity())
                .subtotal(price * i.getQuantity())
                .build();
    }
}
