package com.qlda.nhom6.service;

import com.qlda.nhom6.model.Timming;
import com.qlda.nhom6.repository.ITimingRepository;
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
public class TimingService {
    private final ITimingRepository iTimingRepository;

    public List<Timming> getAllWorkingtime() {
        return iTimingRepository.findAll();
    }
    public Optional<Timming> getWorkingtime(Long id) {
        return iTimingRepository.findById(id);
    }
    public void addLoaithuoc( Timming timming){

        iTimingRepository.save(timming);

    }
    public void updateWorkingtime(@NotNull Timming timming){
        Timming existingWorkingtime= iTimingRepository
                .findById(timming.getId())
                .orElse(null);
        Objects.requireNonNull(existingWorkingtime).setDay(timming.getDay());
        existingWorkingtime.setHours(existingWorkingtime.getHours());
        iTimingRepository.save(existingWorkingtime);
    }
    public void deleteWorkingtime(Long id){
        iTimingRepository.deleteById(id);
    }



}
