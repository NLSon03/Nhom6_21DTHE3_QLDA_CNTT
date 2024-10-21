package com.qlda.nhom6.controller;

import com.NgocHieu.Buoi22.model.*;
import com.NgocHieu.Buoi22.service.DatlichService;
import com.NgocHieu.Buoi22.service.DoctorService;
import com.NgocHieu.Buoi22.service.KhoaService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {


    private final DoctorService doctorService;
    private  final KhoaService khoaService;

    private  final DatlichService datlichService;

    @GetMapping
    public  String ShowallBacSiAdmin(@NotNull Model model){
        model.addAttribute("doctors",doctorService.getAllLoaiThuoc());

        return"doctor/list";

    }
    @GetMapping("/list-user")
    public  String ShowallBacSi(@NotNull Model model){
        model.addAttribute("doctors",doctorService.getAllLoaiThuoc());

        return"doctor/list-user";

    }

    @GetMapping("/add")
    public String addLoaiThuoc(@NotNull Model model){
        model.addAttribute("doctor",new Doctor());
        model.addAttribute("khoas",khoaService.getAllLoaiThuoc());
        return "doctor/add";
    }
    @PostMapping("/add")
    public String addDoctor(@ModelAttribute Doctor doctor,  @RequestParam("imageFile") MultipartFile imageFile) {
        for (Timming timming : doctor.getTimmings()) {
            timming.setDoctor(doctor);
        }
        try {
            String fileName = imageFile.getOriginalFilename();
            String uploadDir = "uploads";  // Đường dẫn thư mục bên ngoài
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = imageFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                doctor.setImage("/uploads/" + fileName); // Đường dẫn tương đối
            } catch (IOException ioe) {
                throw new IOException("Không thể lưu tệp hình ảnh: " + fileName, ioe);
            }

            doctorService.addLoaithuoc(doctor);
            return "redirect:/doctors";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/errorPage";
        }


    }
    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/edit/{id}")
    public String editLoaiThuoc(@NotNull Model model, @PathVariable Long id){
        var loaithuoc = doctorService.getLoaiThuoc(id).orElse(null);
        model.addAttribute("doctor",loaithuoc!=null?loaithuoc:new LoaiThuoc());
        return "doctor/edit";
    }
    @PostMapping("/edit")
    public String editLoaiThuoc(@ModelAttribute("doctor") Doctor loaithuoc){
        doctorService.updateLoaithuoc(loaithuoc);
        return "redirect:/doctors";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoaiThuoc(@NotNull Model model, @PathVariable Long id){
        if(doctorService.getLoaiThuoc(id).isPresent())
            doctorService.deleteLoaithuoc(id);
        return "redirect:/doctors";
    }
    @GetMapping("/detail/{id}")
    public String showScheduleForm(@PathVariable Long id, Model model) {
        Doctor doctor = doctorService.getLoaiThuoc(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor Id:" + id));
        Datlich datlich = new Datlich();
        datlich.setDoctor(doctor); // Set the doctor in the appointment

        model.addAttribute("doctor", doctor);
        model.addAttribute("appointment", datlich); // Pass appointment object to the form
        return "doctor/detail"; // Assuming the form name is 'form.html'
    }

    @PostMapping("/detail")
    public String scheduleAppointment(@ModelAttribute("appointment") Datlich datlich, BindingResult bindingResult) {


        // Save the appointment
        datlichService.addLoaithuoc(datlich);

        return "redirect:/doctors/list-user"; // Redirect to doctor list or another appropriate page
    }

}
