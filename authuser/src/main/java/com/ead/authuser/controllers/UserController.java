package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<UserModel> userModelPage = userService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModalOptional = userService.findById(userId);

        if(!userModalOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");

        }
        return ResponseEntity.status(HttpStatus.OK).body(userModalOptional.get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModalOptional = userService.findById(userId);
        if(!userModalOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");

        }
        userService.delete(userModalOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody
                                             @Validated(UserDto.UserView.UserPut.class)
                                             @JsonView(UserDto.UserView.UserPut.class)  UserDto userDto ) {
        Optional<UserModel> userModalOptional = userService.findById(userId);
        if(!userModalOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        var userModel = userModalOptional.get();
        userModel.setFullName(userDto.getFullName());
        userModel.setPhoneNumber(userDto.getPhoneNumber());
        userModel.setCpf(userDto.getCpf());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@RequestBody
                                                 @PathVariable(value = "userId") UUID userId,
                                                 @Validated(UserDto.UserView.PasswordPut.class)
                                                 @JsonView(UserDto.UserView.PasswordPut.class)  UserDto userDto ) {
        Optional<UserModel> userModalOptional = userService.findById(userId);
        if(!userModalOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        if(!userModalOptional.get().getPassword().equals(userDto.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Senha diferente da cadastrada");
        }

        var userModel = userModalOptional.get();
        userModel.setPassword(userDto.getPassword());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body("Senha atualizada com sucesso!");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
                                              @RequestBody
                                              @Validated(UserDto.UserView.ImagePut.class)
                                              @JsonView(UserDto.UserView.ImagePut.class)  UserDto userDto ) {
        Optional<UserModel> userModalOptional = userService.findById(userId);
        if(!userModalOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        var userModel = userModalOptional.get();
        userModel.setImageUrl(userDto.getImageUrl());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body("Imagem atualizada com sucesso!");
    }
}
