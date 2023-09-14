package com.intern.project.component;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
/*
*   the component generic class for call to map between 2 class by
*   using modelmapper
* */
@Component
@RequiredArgsConstructor
public class DTOConverter<T,Y>{

    private final ModelMapper modelMapper;
    /*
    *   Convert the object to dto class
    * */
    public Y convertToDTO(Object t,Class<Y> y) {
        return modelMapper.map(t, y);
    }
    /*
     *   Convert from  dto to class
     * */
    public T convertToClass(Object z,Class<T> t) {
        return modelMapper.map(z,t);
    }
}
