package com.example.mysmallproject.specifications;
import com.example.mysmallproject.entity.Users;
import jakarta.persistence.criteria.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
@Component
public class UserSpacifications {
    public static Specification<Users> search(String field){
        String param = "%"+field.replaceAll("\\s","").toUpperCase()+"%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(
                        criteriaBuilder.upper(
                        criteriaBuilder.concat(root.get("firstname"),root.get("lastname"))),param),

                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("address")),param)
        );
    };
    public static Specification<Users> filterAndSearch(String address,String type, String search){
        if(address!=null && type!=null && search!=null){
            return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                    criteriaBuilder.like(
                            criteriaBuilder.upper(
                                    criteriaBuilder.concat(root.get("firstname"),root.get("lastname"))),"%"+search.replaceAll("\\s","").toUpperCase()+"%"),
                    criteriaBuilder.like(
                            criteriaBuilder.upper(root.get("address")),"%"+address.replaceAll("\\s","").toUpperCase()+"%"),
                    criteriaBuilder.like(
                            criteriaBuilder.upper(root.get("type")),"%"+type.replaceAll("\\s","").toUpperCase()+"%")
            );
        }else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }


    }
}
