package com.mitocode.service.impl;


import com.mitocode.model.Enroll;
import com.mitocode.model.EnrollDetail;
import com.mitocode.repo.IEnrollRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IEnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class EnrollServiceImpl extends CRUDImpl<Enroll, Integer> implements IEnrollService {

    private final IEnrollRepo repo;
    @Override
    protected IGenericRepo<Enroll, Integer> getRepo() {
        return repo;
    }

    @Override
    public Map<String, List<String>> getCoursesAndStudents() {

        Stream<Enroll> enrollStream = repo.findAll().stream();
        Stream<List<EnrollDetail>> listStream = enrollStream.map(Enroll::getDetails);

        Stream<EnrollDetail> enrollDetailStream = listStream.flatMap(Collection::stream);

        Map<String, List<String>> coursesAndStudents = enrollDetailStream
                .collect(groupingBy(
                        e -> e.getCourse().getName(),
                        mapping(
                                e -> e.getEnroll().getStudent().getFirstName().concat(" " + e.getEnroll().getStudent().getLastName()),
                                toList()
                        )
                ));

        return coursesAndStudents;
    }
}
