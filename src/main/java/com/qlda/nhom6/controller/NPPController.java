package com.qlda.nhom6.controller;


import com.qlda.nhom6.model.NhaPhanPhoi;
import com.qlda.nhom6.service.NPPService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nhapps")
@RequiredArgsConstructor
public class NPPController {
   private final NPPService nppservice;

    @GetMapping
    public  String ShowallNpp(@NotNull Model model){
        model.addAttribute("nhapps",nppservice.getAllNPP());

        return"nhapp/list";

    }

    @GetMapping("/add")
    public String addNSX(@NotNull Model model){
        model.addAttribute("nhapp",new NhaPhanPhoi());
        return "nhapp/add";
    }

    @PostMapping("/add")
    public String addNSX(@RequestBody @ModelAttribute("nhapp") NhaPhanPhoi nhaPhanPhoi){

        nppservice.addNpp(nhaPhanPhoi);

        return "redirect:/nhapps";

    }
    @GetMapping("/edit/{id}")
    public String editLoaiThuoc(@NotNull Model model, @PathVariable Long id){
        var NPP = nppservice.getNPP(id).orElse(null);
        model.addAttribute("nhapp",NPP!=null?NPP:new NhaPhanPhoi());
        return "nhasanxuat/edit";
    }
    @PostMapping("/edit")
    public String editNsx(@ModelAttribute("nhapp") NhaPhanPhoi nhaPhanPhoi){
        nppservice.updateNPP(nhaPhanPhoi);
        return "redirect:/nhapps";
    }

    @GetMapping("/delete/{id}")
    public String deleteNSX(@NotNull Model model, @PathVariable Long id){
        if(nppservice.getNPP(id).isPresent())
            nppservice.deleteNpp(id);
        return "redirect:/nhapps";
    }

}
