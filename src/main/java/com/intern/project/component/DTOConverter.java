package com.intern.project.component;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class DTOConverter<T,Y>{
    private final ModelMapper modelMapper;
    public Y convertToDTO(Object t,Class<Y> y) {
        return modelMapper.map(t, y);
    }
    public T convertToClass(Object z,Class<T> t) {
        return modelMapper.map(z,t);
    }
}
