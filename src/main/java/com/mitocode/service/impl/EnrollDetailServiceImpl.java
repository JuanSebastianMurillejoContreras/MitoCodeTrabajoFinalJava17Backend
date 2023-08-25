package com.mitocode.service.impl;

import com.mitocode.model.EnrollDetail;
import com.mitocode.repo.IEnrollDetailRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IEnrollDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollDetailServiceImpl extends CRUDImpl<EnrollDetail, Integer> implements IEnrollDetailService {

    private final IEnrollDetailRepo repo;
    @Override
    protected IGenericRepo<EnrollDetail, Integer> getRepo() {
        return repo;
    }
}
