package com.qlda.nhom6.controller;

import com.qlda.nhom6.model.Timming;
import com.qlda.nhom6.service.DoctorService;
import com.qlda.nhom6.service.TimingService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/timings")
public class workingController {
    final TimingService loaiThuocService;
    final DoctorService doctorService;

    @GetMapping
    public  String ShowallLoaiThuoc(@NotNull Model model){
        model.addAttribute("Timings",loaiThuocService.getAllWorkingtime());
        model.addAttribute("doctors", doctorService.getAllLoaiThuoc());
        return"Timing/list";

    }

    @GetMapping("/add")
    public String addLoaiThuoc(@NotNull Model model){

        model.addAttribute("Timing",new Timming());
        model.addAttribute("doctors", doctorService.getAllLoaiThuoc());
        return "Timing/add";
    }

    @PostMapping("/add")
    public String addLoaiThuoc(@ModelAttribute("Timing") Timming timming, BindingResult bindingResult, Model model    ){
        if(bindingResult.hasErrors()){
            return "Timing/add";
        }
        loaiThuocService.addLoaithuoc(timming);
        return "redirect:/timings";

    }
    @GetMapping("/edit/{id}")
    public String editLoaiThuoc(@NotNull Model model, @PathVariable Long id){
        var loaithuoc = loaiThuocService.getWorkingtime(id).orElse(null);
        model.addAttribute("Timing",loaithuoc!=null?loaithuoc:new Timming());
        return "Timing/edit";
    }
    @PostMapping("/edit")
    public String editLoaiThuoc(@ModelAttribute("Timing") Timming timming){
        loaiThuocService.updateWorkingtime(timming);
        return "redirect:/timings";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoaiThuoc(@NotNull Model model, @PathVariable Long id){
        if(loaiThuocService.getWorkingtime(id).isPresent())
            loaiThuocService.deleteWorkingtime(id);
        return "redirect:/timings";
    }
}
