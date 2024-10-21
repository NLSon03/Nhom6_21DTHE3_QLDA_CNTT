package com.qlda.nhom6.controller;

import com.NgocHieu.Buoi22.model.khoa;
import com.NgocHieu.Buoi22.service.KhoaService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/khoas")
public class KhoaController {



    final KhoaService loaiThuocService;

    @GetMapping
    public  String ShowallLoaiThuoc(@NotNull Model model){
        model.addAttribute("khoas",loaiThuocService.getAllLoaiThuoc());

        return"khoa/list";

    }
    @GetMapping("/list-user")
    public  String ShowallLoaiThuocUser(@NotNull Model model){
        model.addAttribute("khoas",loaiThuocService.getAllLoaiThuoc());

        return"khoa/list-user";

    }

    @GetMapping("/add")
    public String addLoaiThuoc(@NotNull Model model){
        model.addAttribute("khoa",new khoa());
        return "khoa/add";
    }

    @PostMapping("/add")
    public String addLoaiThuoc(@ModelAttribute("khoa") khoa loaiThuoc, BindingResult bindingResult, Model model    ){
        if(bindingResult.hasErrors()){
            return "khoa/add";
        }
        loaiThuocService.addLoaithuoc(loaiThuoc);
        return "redirect:/khoas";

    }
    @GetMapping("/edit/{id}")
    public String editLoaiThuoc(@NotNull Model model, @PathVariable Long id){
        var loaithuoc = loaiThuocService.getLoaiThuoc(id).orElse(null);
        model.addAttribute("Khoa",loaithuoc!=null?loaithuoc:new khoa());
        return "Khoa/edit";
    }
    @PostMapping("/edit")
    public String editLoaiThuoc(@ModelAttribute("Khoa") khoa loaithuoc){
        loaiThuocService.updateLoaithuoc(loaithuoc);
        return "redirect:/khoas";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoaiThuoc(@NotNull Model model, @PathVariable Long id){
        if(loaiThuocService.getLoaiThuoc(id).isPresent())
            loaiThuocService.deleteLoaithuoc(id);
        return "redirect:/khoas";
    }
}
