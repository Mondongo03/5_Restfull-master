package com.example.dades;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    public List<UserDto> readAll() {
        List<UserDto> userDtos;
        userDtos = userService.readAllUsers().stream().map(UserDto::new).toList();
        return userDtos;
    }
    public UserDto getUserById(Integer id){
        return new UserDto(userService.getUserById(id));
    }

    public UserDto addUser(User user) {
        return new  UserDto(userService.addUser(user));
    }

    public void removeUser(Integer id) {
        userService.removeUser(id);
    }

    public UserDto putUser(Integer id, User user) {
        User putuser = userService.userDAO.findById(id).orElse(null);
        putuser.setId(user.getId());
        putuser.setEmail(user.getEmail());
        putuser.setPassword(user.getPassword());
        putuser.setFullName(user.getFullName());
        return new UserDto(userService.putUser(putuser));
    }
    public UserDto pathUser(Integer id, JsonPatch jsonPatch) throws JsonPatchException, JsonProcessingException {
        User userPatch;
        try {
            User user = userService.userDAO.findById(id).orElse(null);
            userPatch = applyPatchToUser(jsonPatch, user);
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new UserDto(userService.patchUser(userPatch));
    }

    private User applyPatchToUser(JsonPatch patch, User targetCustomer) throws JsonPatchException, JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, User.class);
    }
}
