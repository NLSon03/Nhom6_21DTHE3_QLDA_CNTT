package com.qlda.nhom6.service;

import com.qlda.nhom6.model.khoa;
import com.qlda.nhom6.repository.IkhoaRepository;
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
public class KhoaService {


    private final IkhoaRepository ikhoaRepository;
    public List<khoa> getAllLoaiThuoc() {
        return ikhoaRepository.findAll();
    }
    public Optional<khoa> getLoaiThuoc(Long id) {
        return ikhoaRepository.findById(id);
    }
    public void addLoaithuoc(khoa Khoa){

        ikhoaRepository.save(Khoa);

    }
    public void updateLoaithuoc(@NotNull khoa Khoa){
        khoa existingkhoa= ikhoaRepository
                .findById(Khoa.getId())
                .orElse(null);
        Objects.requireNonNull(existingkhoa).setName(Khoa.getName());

        ikhoaRepository.save(existingkhoa);
    }
    public void deleteLoaithuoc(Long id){
        ikhoaRepository.deleteById(id);
    }

}
