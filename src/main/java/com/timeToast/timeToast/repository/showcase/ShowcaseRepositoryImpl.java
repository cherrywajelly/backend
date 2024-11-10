package com.timeToast.timeToast.repository.showcase;

import com.timeToast.timeToast.domain.showcase.Showcase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ShowcaseRepositoryImpl implements ShowcaseRepository{

    private final ShowcaseJpaRepository showcaseJpaRepository;


    public ShowcaseRepositoryImpl(ShowcaseJpaRepository showcaseJpaRepository) {
        this.showcaseJpaRepository = showcaseJpaRepository;
    }

    @Override
    public Showcase save(final Showcase showcase){
        return showcaseJpaRepository.save(showcase);
    }

    @Override
    public Optional<Showcase> findByShowcaseId(final long showcaseId){
        return showcaseJpaRepository.findById(showcaseId);
    }

    @Override
    public List<Showcase> findAllByMemberId(final long memberId){
        return showcaseJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public void deleteShowcase(final Showcase showcase){
        showcaseJpaRepository.delete(showcase);
    }

    @Override
    public void deleteAllByEventToastId(final long eventToastId){
        showcaseJpaRepository.deleteAllByEventToastId(eventToastId);
    }
}
