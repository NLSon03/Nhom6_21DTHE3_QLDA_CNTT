package com.qlda.nhom6.service;

import com.NgocHieu.Buoi22.model.Datlich;
import com.NgocHieu.Buoi22.model.Doctor;
import com.NgocHieu.Buoi22.repository.IDatlichRepository;
import com.NgocHieu.Buoi22.repository.ITimingRepository;
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
public class DatlichService {
    final private IDatlichRepository iDatlichRepository;

    private   final   DoctorService doctorService;

    final private ITimingRepository iTimingRepository;
    public List<Datlich> getAllLoaiThuoc() {
        return iDatlichRepository.findAll();
    }
    public Optional<Datlich> getLoaiThuoc(Long id) {
        return iDatlichRepository.findById(id);
    }
    public void addLoaithuoc(Datlich datlich) {
        Doctor doctor = datlich.getDoctor();
        if (doctor != null && doctorService.getLoaiThuoc(doctor.getId()).isPresent()) {
            iDatlichRepository.save(datlich);
        } else {
            throw new IllegalArgumentException("Doctor not found or not saved properly.");
        }
    }
    public void updateLoaithuoc(@NotNull Datlich datlich){
        Datlich existingBs= iDatlichRepository
                .findById(datlich.getId())
                .orElse(null);
        Objects.requireNonNull(existingBs).setName(datlich.getName());
        existingBs.setNgay(datlich.getNgay());
        existingBs.setEmail(datlich.getEmail());
        existingBs.setContent(datlich.getContent());
        iDatlichRepository.save(existingBs);
    }
    public void deleteLoaithuoc(Long id){
        iDatlichRepository.deleteById(id);
    }

    public List<Datlich> getAllDatLich() {
        return iDatlichRepository.findAll();
    }


}
