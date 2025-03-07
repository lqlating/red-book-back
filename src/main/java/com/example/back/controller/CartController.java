package com.example.back.controller;

import com.example.back.pojo.Cart;
import com.example.back.pojo.Result;
import com.example.back.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    public Result listCarts() {
        List<Cart> carts = cartService.listCarts();
        return Result.success(carts);
    }


    @GetMapping("/add")
    public Result addCart(@RequestParam Integer owner_id, @RequestParam Integer book_id) {
        cartService.addCart(owner_id, book_id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateCart(@RequestBody Cart cart) {
        cartService.updateCart(cart);
        return Result.success();
    }

    @DeleteMapping("/delete/{cart_id}")
    public Result deleteCart(@PathVariable Integer cart_id) {
        cartService.deleteCart(cart_id);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result deleteCarts(@RequestBody List<Integer> cart_ids) {
        cartService.deleteCarts(cart_ids);
        return Result.success();
    }

    @GetMapping("/owner/{owner_id}")
    public Result getCartsByOwnerId(@PathVariable Integer owner_id) {
        List<Cart> carts = cartService.getCartsByOwnerId(owner_id);
        return Result.success(carts);
    }
}
