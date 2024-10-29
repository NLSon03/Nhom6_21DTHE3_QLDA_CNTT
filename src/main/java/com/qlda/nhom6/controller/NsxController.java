package com.qlda.nhom6.controller;


import com.qlda.nhom6.model.NhaSanXuat;
import com.qlda.nhom6.service.NhaSanXuatService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nhasanxuats")
@RequiredArgsConstructor
public class NsxController {
    final NhaSanXuatService nhaSanXuatService;

    @GetMapping
    public  String ShowallNSX(@NotNull Model model){
        model.addAttribute("nhasanxuats",nhaSanXuatService.getAllNSX());

        return"nhasanxuat/list";

    }

    @GetMapping("/add")
    public String addNSX(@NotNull Model model){
        model.addAttribute("nhasanxuat",new NhaSanXuat());
        return "nhasanxuat/add";
    }

    @PostMapping("/add")
    public String addNSX(@RequestBody @ModelAttribute("nhasanxuat") NhaSanXuat nhaSanXuat){
        if(nhaSanXuatService.getLoaiNSX(nhaSanXuat.getId()).isEmpty());
        nhaSanXuatService.addNSX(nhaSanXuat);

        return "redirect:/nhasanxuats";

    }
    @GetMapping("/edit/{id}")
    public String editLoaiThuoc(@NotNull Model model, @PathVariable Long id){
        var nhasanxuat = nhaSanXuatService.getLoaiNSX(id).orElse(null);
        model.addAttribute("nhasanxuat",nhasanxuat!=null?nhasanxuat:new NhaSanXuat());
        return "nhasanxuat/edit";
    }
    @PostMapping("/edit")
    public String editNsx(@ModelAttribute("nhasanxuat") NhaSanXuat nhaSanXuat){
        nhaSanXuatService.updateNSX(nhaSanXuat);
        return "redirect:/nhasanxuats";
    }

    @GetMapping("/delete/{id}")
    public String deleteNSX(@NotNull Model model, @PathVariable Long id){
        if(nhaSanXuatService.getLoaiNSX(id).isPresent())
            nhaSanXuatService.deleteNSX(id);
        return "redirect:/nhasanxuats";
    }

}
