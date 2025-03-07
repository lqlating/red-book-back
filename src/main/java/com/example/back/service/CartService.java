package com.example.back.service;

import com.example.back.pojo.Cart;
import java.util.List;

public interface CartService {
    List<Cart> listCarts();

    Cart getCartById(Integer cartId);

    void addCart(Integer ownerId, Integer bookId);

    void updateCart(Cart cart);

    void deleteCart(Integer cartId);

    void deleteCarts(List<Integer> cartIds);

    List<Cart> getCartsByOwnerId(Integer ownerId);

}
