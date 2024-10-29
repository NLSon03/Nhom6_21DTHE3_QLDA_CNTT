package com.qlda.nhom6.controller;

import com.qlda.nhom6.model.DonViTinh;
import com.qlda.nhom6.service.DonVTService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dvts")
@RequiredArgsConstructor
public class DvtController {
    final DonVTService donVTService;

    @GetMapping
    public  String ShowallDvt(@NotNull Model model){
        model.addAttribute("dvts",donVTService.getAllDVT());

        return"dvt/list";

    }

    @GetMapping("/add")
    public String addDvt(@NotNull Model model){
        model.addAttribute("dvt",new DonViTinh());
        return "dvt/add";
    }

    @PostMapping("/add")
    public String addLoaiThuoc(@ModelAttribute("dvt") DonViTinh donViTinh, BindingResult bindingResult, Model model    ){
        if(bindingResult.hasErrors()){
            return "dvt/add";
        }
        donVTService.addDVT(donViTinh);
        return "redirect:/dvts";

    }
    @GetMapping("/edit/{id}")
    public String editDvt(@NotNull Model model, @PathVariable Long id){
        var dvt = donVTService.getDVT(id).orElse(null);
        model.addAttribute("dvt",dvt!=null?dvt:new DonViTinh());
        return "dvt/edit";
    }
    @PostMapping("/edit")
    public String editDvt(@ModelAttribute("dvt") DonViTinh donViTinh){
        donVTService.updateLoaithuoc(donViTinh);
        return "redirect:/dvts";
    }

    @GetMapping("/delete/{id}")
    public String deleteDvt(@NotNull Model model, @PathVariable Long id){
        if(donVTService.getDVT(id).isPresent())
            donVTService.deleteDVT(id);
        return "redirect:/dvts";
    }
}

