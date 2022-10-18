package org.badtiev.rest;

import org.badtiev.rest.configuration.MyConfig;
import org.badtiev.rest.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication= context.getBean("communication", Communication.class);
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String sessionID = communication.getAllUsers(new HttpEntity<>(httpHeaders));
        System.out.println("SessionID-"+sessionID);

        httpHeaders.set("Cookie", String.join(";", sessionID));
        User newUser = new User(3L, "James", "Brown", (byte)44);
        HttpEntity<User> httpEntity = new HttpEntity<>(newUser, httpHeaders);

        communication.addUser(httpEntity);
        newUser.setName("Thomas");
        newUser.setLastName("Shelby");

        communication.editUser(httpEntity);
        communication.deleteUserById(httpEntity, 3);
        System.out.println("");
        System.out.println("-----------------------");
        System.out.println(communication.getResult());
        System.out.println("-----------------------");
    }
}
