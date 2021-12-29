package com.example.meshgrouptest.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import com.example.meshgrouptest.MeshGroupTestApplication;
import com.example.meshgrouptest.controllers.UserController;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = {MeshGroupTestApplication.class})
public class HttpTest{
    @Autowired
    private UserController controller;

    @Test
    public void getAllUsers_returns200(){
        Response response = RestAssured.get("http://127.0.0.1:8080/allusers?page=0&size=2");

        assertThat(response.getStatusCode()).isEqualTo(200);
    }
    @Test
    public void Exists_whenFirstPageIsRetrieved_thenPageContainsResources(){
        Response response = RestAssured.get("http://127.0.0.1:8080/allusers?page=0&size=2");
        assertFalse(response.body().as(List.class).isEmpty());
    }
}
