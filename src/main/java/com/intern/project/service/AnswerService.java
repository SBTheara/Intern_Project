package com.intern.project.service;

import com.intern.project.dto.AnswerDTO;
import com.intern.project.dto.PaginateRequest;
import com.intern.project.dto.QuestionDTO;
import com.intern.project.entity.Answer;
import com.intern.project.repository.AnswerRepository;
import com.intern.project.utils.AnswerSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private static final List<String> SORTABLE_FIELD = List.of("type", "level");
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;
    public Answer save(Answer answer){
        return answerRepository.save(answer);
    }

    public List<Answer> listAll(){
        return answerRepository.findAll();
    }

    public Page<AnswerDTO> filter(PaginateRequest paginateRequest, String sortBy, String correct) {
        Pageable pageable =
                PageRequest.of(
                        paginateRequest.getPage(),
                        paginateRequest.getSize(),
                        Sort.by(Sort.Direction.fromString(paginateRequest.getDirection()), sortBy));
        Specification<Answer> specification = Specification.where(null);
        if(StringUtils.hasText(correct)){
            specification = specification.and(AnswerSpecification.correct(correct));
        }
        List<AnswerDTO> answerResponse =
                answerRepository
                        .findAll(specification,pageable)
                        .stream()
                        .map(answer -> this.modelMapper.map(answer, AnswerDTO.class))
                        .toList();
        return new PageImpl<>(answerResponse);
    }
}
