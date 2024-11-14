package com.timeToast.timeToast.repository.jam;

import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.JAM_NOT_FOUNT;

@Repository
@RequiredArgsConstructor
public class JamRepositoryImpl implements JamRepository {
    private final JamJpaRepository jamJpaRepository;

    @Override
    public Jam save(Jam jam) { return jamJpaRepository.save(jam); }

    @Override
    public List<Jam> findAllByMemberId(final long memberId) {
        return jamJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public List<Jam> findAllByEventToastId(final long eventToastId) {
        return jamJpaRepository.findAllByEventToastId(eventToastId);
    }

    @Override
    public Jam findByMemberIdAndEventToastId(final long memberId, final long eventToastId) {
        return jamJpaRepository.findByMemberIdAndEventToastId(memberId, eventToastId);
    }

    @Override
    public Jam getById(final long jamId) {
        return jamJpaRepository.findById(jamId).orElseThrow(() -> new NotFoundException(JAM_NOT_FOUNT.getMessage()));
    }

    @Override
    public void deleteById(final long jamId) {
        jamJpaRepository.deleteById(jamId);
    }

    @Override
    public void deleteAllByEventToastId(final long eventToastId){
        jamJpaRepository.deleteAllByEventToastId(eventToastId);
    }
}
