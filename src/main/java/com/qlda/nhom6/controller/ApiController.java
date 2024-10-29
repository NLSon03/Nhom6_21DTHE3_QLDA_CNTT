package com.qlda.nhom6.controller;

import com.qlda.nhom6.service.NhaSanXuatService;
import com.qlda.nhom6.viewmodels.BookGetVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ApiController {

    private final NhaSanXuatService cartService;

    @GetMapping("/books")
    public ResponseEntity<List<BookGetVm>> getAllBooks(Integer pageNo,
                                                       Integer pageSize, String sortBy) {
        return ResponseEntity.ok(cartService.getAllNSX(
                        pageNo == null ? 0 : pageNo,
                        pageSize == null ? 20 : pageSize,
                        sortBy == null ? "id" : sortBy)
                .stream()
                .map(BookGetVm::from)
                .toList());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookGetVm> cartService(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getLoaiNSX(id)
                .map(BookGetVm::from)
                .orElse(null));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        cartService.deleteNSX(id);
        return ResponseEntity.ok().build();
    }
}