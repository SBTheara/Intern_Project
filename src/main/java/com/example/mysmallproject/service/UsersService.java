package com.example.mysmallproject.service;

import com.example.mysmallproject.dto.UserDTO;
import com.example.mysmallproject.dto.UserRegistrationDTO;
import com.example.mysmallproject.entity.User;
import com.example.mysmallproject.repository.UsersRepository;
import com.example.mysmallproject.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class UsersService implements HelperGenerics<UserDTO, UserRegistrationDTO> {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserDTO save(UserRegistrationDTO userRegistrationDTO) {
        User userRequest = modelMapper.map(userRegistrationDTO,User.class);
        User user = usersRepository.save(userRequest);
        return modelMapper.map(user,UserDTO.class);
    }
    @Override
    public UserDTO update(UserRegistrationDTO userRegistrationDTO, int id) {
        User users = usersRepository.findById(id).orElseThrow(()->new IllegalStateException("Not found for this user"));
        User userRequest = modelMapper.map(userRegistrationDTO,User.class);
        users.setFirstName(userRegistrationDTO.getFirstName());
        users.setLastName(userRegistrationDTO.getLastName());
        users.setEmail(userRegistrationDTO.getEmail());
        users.setPassword(userRegistrationDTO.getPassword());
        users.setAddress(userRegistrationDTO.getAddress());
        users.setPhone(userRegistrationDTO.getPhone());
        users.setCreateAt(userRegistrationDTO.getCreateAt());
        usersRepository.save(users);
        return modelMapper.map(userRequest,UserDTO.class);
    }
    @Override
    public void delete(int id) {
        usersRepository.deleteById(id);
    }
    public Page<UserDTO> filter(String address,String search, String sortBy, int offset, int pageSize) {
        Pageable pageable = PageRequest.of(offset,pageSize, Sort.by(Sort.Direction.ASC,sortBy));
        if(address.isEmpty()&&search.isEmpty()){
            List<UserDTO> userDTOS = usersRepository.findAll(pageable).stream().map(p->modelMapper.map(p,UserDTO.class)).toList();
            return new PageImpl<>(userDTOS,Pageable.unpaged(),userDTOS.size());
        }else{
            List<UserDTO> userDTOS = usersRepository.findAll(where(UserSpecification.filterAndSearch(address,search)),pageable).stream().map(p->modelMapper.map(p,UserDTO.class)).toList();
            return new PageImpl<>(userDTOS,Pageable.unpaged(),userDTOS.size());
        }
    }
}
