package com.qlda.nhom6.service;

import com.NgocHieu.Buoi22.model.Doctor;
import com.NgocHieu.Buoi22.model.Timming;
import com.NgocHieu.Buoi22.repository.ITimingRepository;
import com.NgocHieu.Buoi22.repository.IdocterRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE,
        rollbackFor = {Exception.class, Throwable.class})
public class DoctorService {

    final private IdocterRepository idocterRepository;

    final private ITimingRepository iTimingRepository;
    public List<Doctor> getAllLoaiThuoc() {
        return idocterRepository.findAll();
    }
    public Optional<Doctor> getLoaiThuoc(Long id) {
        return idocterRepository.findById(id);
    }
    public void addLoaithuoc(Doctor doctor){

        idocterRepository.save(doctor);

    }
    public void updateLoaithuoc(@NotNull Doctor doctor){
        Doctor existingBs= idocterRepository
                .findById(doctor.getId())
                .orElse(null);
        Objects.requireNonNull(existingBs).setName(doctor.getName());
        existingBs.setIntroduction(doctor.getIntroduction());
        existingBs.setEmail(doctor.getEmail());
        existingBs.setAddress(doctor.getAddress());
        existingBs.setExperience(doctor.getExperience());
        existingBs.setEducation(doctor.getEducation());
        existingBs.setSpeciality(doctor.getSpeciality());
        existingBs.setKhoa(doctor.getKhoa());



        idocterRepository.save(existingBs);
    }
    public void deleteLoaithuoc(Long id){
        idocterRepository.deleteById(id);
    }
    public void addTimmingToDoctor(Long doctorId, String day, String hours) {
        Doctor doctor = idocterRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Timming timming = new Timming();
        timming.setDay(day);
        timming.setHours(hours);
        iTimingRepository.save(timming); // Save Timming to get an ID

        doctor.getTimmings().add(timming);
        idocterRepository.save(doctor);
    }

}


