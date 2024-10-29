package com.qlda.nhom6.service;

import com.qlda.nhom6.model.DonViTinh;
import com.qlda.nhom6.repository.IDVTRepository;
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
public class DonVTService {
    private final IDVTRepository idvtRepository;

    public List<DonViTinh> getAllDVT() {
        return idvtRepository.findAll();
    }
    public Optional<DonViTinh> getDVT(Long id) {
        return idvtRepository.findById(id);
    }
    public void addDVT(DonViTinh donViTinh){

        idvtRepository.save(donViTinh);

    }
    public void updateLoaithuoc(@NotNull DonViTinh donViTinh){
        DonViTinh existingDVT= idvtRepository
                .findById(donViTinh.getMaDVT())
                .orElse(null);
        Objects.requireNonNull(existingDVT).setTenDVT(donViTinh.getTenDVT());


        idvtRepository.save(existingDVT);
    }
    public void deleteDVT(Long id){
        idvtRepository.deleteById(id);
    }
}
