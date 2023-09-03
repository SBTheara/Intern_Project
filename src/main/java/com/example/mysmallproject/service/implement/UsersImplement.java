package com.example.mysmallproject.service.implement;

import com.example.mysmallproject.dto.ProductDTO;
import com.example.mysmallproject.dto.UserDTO;
import com.example.mysmallproject.dto.UserRegistrationDTO;
import com.example.mysmallproject.entity.User;
import com.example.mysmallproject.exception.RequestException;
import com.example.mysmallproject.repository.UsersRepository;
import com.example.mysmallproject.service.UsersService;
import com.example.mysmallproject.specification.UserSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class UsersImplement implements UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDTO saveUser(UserRegistrationDTO userRegistrationDTO) {
        User userRequest = modelMapper.map(userRegistrationDTO,User.class);
        User user = usersRepository.save(userRequest);
        return modelMapper.map(user,UserDTO.class);
    }
    @Override
    public List<UserDTO> getUser(){
        return usersRepository.findAll().stream().map(user->modelMapper.map(user,UserDTO.class)).collect(Collectors.toList());
    }
    @Override
    public UserDTO getUserById(int id) {
        User userRequest = usersRepository.findById(id).get();
        return modelMapper.map(userRequest,UserDTO.class);
    }
    @Override
    public UserDTO updateUser(UserRegistrationDTO userRegistrationDTO, int id) {
        User users = usersRepository.findById(id).orElseThrow(()->new RequestException("Not found for this user"));
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
    public void deleteUser(int id) {
        usersRepository.deleteById(id);
    }

    @Override
    public Page<UserDTO> filter(String address, String type, String search, String sortBy, int offset, int pageSize) {
        Pageable pageable = PageRequest.of(offset,pageSize, Sort.by(Sort.Direction.ASC,sortBy));
        if(address.isEmpty()&&type.isEmpty()){
            List<UserDTO> userDTOS = usersRepository.findAll(pageable).stream().map(p->modelMapper.map(p,UserDTO.class)).toList();
            Page<UserDTO> productDTOPage = new PageImpl<>(userDTOS,Pageable.unpaged(),userDTOS.size());
            return productDTOPage;
        }else{
            List<UserDTO> userDTOS = usersRepository.findAll(where(UserSpecification.filterAndSearch(address,type,search)),pageable).stream().map(p->modelMapper.map(p,UserDTO.class)).toList();
            Page<UserDTO> productDTOPage = new PageImpl<>(userDTOS,Pageable.unpaged(),userDTOS.size());
            return productDTOPage;
        }
    }

}
