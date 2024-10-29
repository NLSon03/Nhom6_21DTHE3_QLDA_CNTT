package com.qlda.nhom6.controller;

import com.qlda.nhom6.service.DatlichService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/datlichs")
@RequiredArgsConstructor
public class DatlichController {
    final DatlichService datlichService;

    @GetMapping
    public  String ShowallNSX(@NotNull Model model){
        model.addAttribute("datlichs",datlichService.getAllDatLich());

        return"datlich/list";

    }
}

