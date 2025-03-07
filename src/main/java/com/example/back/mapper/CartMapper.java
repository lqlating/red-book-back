package com.example.back.mapper;

import com.example.back.pojo.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {
    List<Cart> listCarts();

    Cart getCartById(@Param("cart_id") Integer cartId);

    void addCart(Cart cart);

    void updateCart(Cart cart);

    void deleteCart(@Param("cart_id") Integer cartId);

    List<Cart> getCartsByOwnerId(@Param("owner_id") Integer ownerId);

    // 添加 deleteCarts 方法
    void deleteCarts(@Param("cartIds") List<Integer> cartIds);
}