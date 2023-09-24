package com.intern.project.service;

import com.intern.project.dto.ProductDTO;
import com.intern.project.dto.QuestionResponse;
import com.intern.project.entity.Answer;
import com.intern.project.entity.Product_;
import com.intern.project.entity.Questions;
import com.intern.project.repository.QuestionRepository;
import com.intern.project.utils.ProductSpecification;
import com.intern.project.utils.QuestionSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;
    private final ModelMapper modelMapper;

    public Questions save(Questions request) {
        Questions questions = new Questions();
        List<Answer> answerList = request.getAnswers();
        Answer answer = new Answer();
        request.setAnswers(null);
        long id = getid(questionRepository.save(request));
        answerList.forEach(r->r.setQuestionId((int) id));
        request.setAnswers(answerList);
        return questionRepository.save(request);
    }

    private long getid(Questions save) {
        return save.getId();
    }
    public QuestionResponse listById(long id){
        return this.modelMapper.map(questionRepository.findById(id),QuestionResponse.class);
    }
    public List<QuestionResponse> listAll() {
        return questionRepository.findAll()
                .stream()
                .map(question -> this.modelMapper.map(question, QuestionResponse.class))
                .toList();
    }

    public Page<QuestionResponse> filter(
            String type,
            int offset,
            int pageSize
    ) {
        Pageable pageable = PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC));
        try {
            List<QuestionResponse> questionResponses = questionRepository
                    .findAll(QuestionSpecification.filter(type), pageable)
                    .stream()
                    .map(questions -> this.modelMapper.map(questions, QuestionResponse.class))
                    .toList();
            log.debug("Product found !!!!");
            return new PageImpl<>(questionResponses,Pageable.unpaged(),questionResponses.size());
        } catch (IllegalStateException exception) {
            log.error("Product not found !!!!");
            return null;
        }
    }
}
