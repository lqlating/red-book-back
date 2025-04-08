package com.example.back.service.impl;

import com.example.back.mapper.CartMapper;
import com.example.back.pojo.Book;
import com.example.back.pojo.Cart;
import com.example.back.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> listCarts() {
        return cartMapper.listCarts();
    }

    @Override
    public Cart getCartById(Integer cartId) {
        return cartMapper.getCartById(cartId);
    }

    @Override
    public void addCart(Integer ownerId, Integer bookId) {
        Cart cart = new Cart();
        cart.setOwner_id(ownerId);
        Book book = new Book();
        book.setBook_id(bookId);
        cart.setBook(book);
        cartMapper.addCart(cart);
    }

    @Override
    public void updateCart(Cart cart) {
        cartMapper.updateCart(cart);
    }

    @Override
    public void deleteCart(Integer cartId) {
        cartMapper.deleteCart(cartId);
    }

    @Override
    public void deleteCarts(List<Integer> cartIds) {
        cartMapper.deleteCarts(cartIds);
    }

    @Override
    public List<Cart> getCartsByOwnerId(Integer ownerId) {
        return cartMapper.getCartsByOwnerId(ownerId);
    }

}