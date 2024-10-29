package com.qlda.nhom6.service;

import com.qlda.nhom6.model.NhaSanXuat;
import com.qlda.nhom6.repository.NSXRepsitory;
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
public class NhaSanXuatService {
    private final NSXRepsitory inhaSanXuatRepository;

    public List<NhaSanXuat> getAllNSX(Integer pageNo,
                                      Integer pageSize,
                                      String sortBy) {
        return inhaSanXuatRepository.findAllBooks(pageNo,pageSize,sortBy);
    }
    public List<NhaSanXuat> getAllNSX() {
        return inhaSanXuatRepository.findAll();
    }
    public Optional<NhaSanXuat> getLoaiNSX(Long id) {
        return inhaSanXuatRepository.findById(id);
    }
    public void addNSX(NhaSanXuat nhaSanXuat){

        inhaSanXuatRepository.save(nhaSanXuat);

    }
    public void updateNSX(NhaSanXuat nhaSanXuat){
        NhaSanXuat existingNSX= inhaSanXuatRepository
                .findById(nhaSanXuat.getId())
                .orElse(null);
        Objects.requireNonNull(existingNSX).setTenNSX(nhaSanXuat.getTenNSX());
        existingNSX.setDiachi(nhaSanXuat.getDiachi());
        existingNSX.setDienThoai(nhaSanXuat.getDienThoai()) ;
        inhaSanXuatRepository.save(existingNSX);
    }
    public void deleteNSX(Long id){
        inhaSanXuatRepository.deleteById(id);
    }

}
