package com.timeToast.timeToast.repository.jam;

import com.timeToast.timeToast.domain.jam.Jam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JamRepositoryImpl implements JamRepository {
    private final JamJpaRepository jamJpaRepository;

    @Override
    public Jam save(Jam jam) { return jamJpaRepository.save(jam); }

    @Override
    public List<Jam> findByMemberId(final long memberId) {
        return jamJpaRepository.findByMemberId(memberId);
    }

    @Override
    public Jam findByMemberIdAndEventToastId(final long memberId, final long eventToastId) {
        return jamJpaRepository.findByMemberIdAndEventToastId(memberId, eventToastId);
    }
}
