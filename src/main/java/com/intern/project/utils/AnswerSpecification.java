package com.intern.project.utils;

import com.intern.project.entity.Answer;
import com.intern.project.entity.Answer_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerSpecification {
    public static Specification<Answer> correct(String correct){
        if(correct.equals("true")){
            return (root,query,criteriaBuilder) -> criteriaBuilder.isTrue(root.get(Answer_.isCorrect));
        }
        else {
            return (root,query,criteriaBuilder) -> criteriaBuilder.isFalse(root.get(Answer_.isCorrect));
        }
    }
}
