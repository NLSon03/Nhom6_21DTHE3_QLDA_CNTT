package com.qlda.nhom6.service;

import com.NgocHieu.Buoi22.model.LoaiThuoc;
import com.NgocHieu.Buoi22.repository.IloaiThuoc;
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
public class LoaiThuocService {
    private final IloaiThuoc iloaiThuoc;

    public List<LoaiThuoc> getAllLoaiThuoc() {
        return iloaiThuoc.findAll();
    }
    public Optional<LoaiThuoc> getLoaiThuoc(Long id) {
        return iloaiThuoc.findById(id);
    }
    public void addLoaithuoc(LoaiThuoc loaiThuoc){

        iloaiThuoc.save(loaiThuoc);

    }
    public void updateLoaithuoc(@NotNull  LoaiThuoc loaiThuoc){
        LoaiThuoc existingLoaiThuoc= iloaiThuoc
                .findById(loaiThuoc.getId())
                .orElse(null);
        Objects.requireNonNull(existingLoaiThuoc).setTenLoai(loaiThuoc.getTenLoai());
        existingLoaiThuoc.setGhiChu(loaiThuoc.getGhiChu());
        iloaiThuoc.save(existingLoaiThuoc);
    }
    public void deleteLoaithuoc(Long id){
        iloaiThuoc.deleteById(id);
    }



}
