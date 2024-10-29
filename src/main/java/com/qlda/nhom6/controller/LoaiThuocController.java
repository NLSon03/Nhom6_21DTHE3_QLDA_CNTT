package com.qlda.nhom6.controller;


import com.qlda.nhom6.model.LoaiThuoc;
import com.qlda.nhom6.service.CategoryService;
import com.qlda.nhom6.service.LoaiThuocService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loaithuocs")
@RequiredArgsConstructor
public class LoaiThuocController {
    final LoaiThuocService loaiThuocService;
    final CategoryService categoryService;

    @GetMapping
    public  String ShowallLoaiThuoc(@NotNull Model model){
        model.addAttribute("loaithuocs",loaiThuocService.getAllLoaiThuoc());

        return"loaithuoc/list";

    }

    @GetMapping("/add")
    public String addLoaiThuoc(@NotNull Model model){
        model.addAttribute("loaithuoc",new LoaiThuoc());
        model.addAttribute("category", categoryService.getAllLoaiThuoc());
        return "loaithuoc/add";
    }

    @PostMapping("/add")
    public String addLoaiThuoc(@ModelAttribute("loaithuoc") LoaiThuoc loaiThuoc, BindingResult bindingResult,Model model    ){
        if(bindingResult.hasErrors()){
            return "loaithuoc/add";
        }
        loaiThuocService.addLoaithuoc(loaiThuoc);
        return "redirect:/loaithuocs";

    }
    @GetMapping("/edit/{id}")
    public String editLoaiThuoc(@NotNull Model model, @PathVariable Long id){
        var loaithuoc = loaiThuocService.getLoaiThuoc(id).orElse(null);
        model.addAttribute("loaithuoc",loaithuoc!=null?loaithuoc:new LoaiThuoc());
        return "loaithuoc/edit";
    }
    @PostMapping("/edit")
    public String editLoaiThuoc(@ModelAttribute("loaithuoc") LoaiThuoc loaithuoc){
        loaiThuocService.updateLoaithuoc(loaithuoc);
        return "redirect:/loaithuocs";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoaiThuoc(@NotNull Model model, @PathVariable Long id){
        if(loaiThuocService.getLoaiThuoc(id).isPresent())
            loaiThuocService.deleteLoaithuoc(id);
        return "redirect:/loaithuocs";
    }

}
