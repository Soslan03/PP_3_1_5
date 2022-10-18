package org.badtiev.rest;

import org.badtiev.rest.model.User;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Communication {
    private final RestTemplate restTemplate;
    private final String URL="http://94.198.50.185:7081/api/users";
    private StringBuilder result = new StringBuilder();

    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public StringBuilder getResult() {
        return result;
    }
    public String getAllUsers(HttpEntity<User> requestEntity) {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<User>>() {
                });
        List<User> allUsers = responseEntity.getBody();
        System.out.println(allUsers);
        return responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    }
    public void addUser(HttpEntity<User> requestEntity){
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL,
                HttpMethod.POST,
                requestEntity,
                String.class);
        result.append(responseEntity.getBody());
        System.out.println("responseBody - "+result);
    }

    public void editUser(HttpEntity<User> requestEntity){
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity,String.class);
        result.append(responseEntity.getBody());
        System.out.println("responseBody - "+result);
    }
    public void deleteUserById(HttpEntity<User> requestEntity, int id){
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL+"/"+id, HttpMethod.DELETE, requestEntity,String.class);
        result.append(responseEntity.getBody());
    }

}
