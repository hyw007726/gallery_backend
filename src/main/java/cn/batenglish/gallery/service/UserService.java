package cn.batenglish.gallery.service;

import cn.batenglish.gallery.entity.User;
import cn.batenglish.gallery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EncryptionService encryptionService;

    public User getNewUser(){
        return new User();
    }
    public User addNewUser (User user) {
        try {
            user.setPassword(encryptionService.encrypt(user.getPassword()));
          User savedUser=userRepository.save(user);
          if(savedUser==null){
             return null;
          }
            return savedUser;
        }catch (Exception ex) {
          System.out.println(ex);
          return null;
        }
    }
    public Optional<User> findById(Integer id){
        return userRepository.findById(id);
    }
    public User findByEmail (String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }

    }
    public Boolean verifyPassword (String password, User user) throws NoSuchAlgorithmException {
       if( user.getPassword().equals(encryptionService.encrypt(password))){
           return true;
       }
        return false;
    }

}



