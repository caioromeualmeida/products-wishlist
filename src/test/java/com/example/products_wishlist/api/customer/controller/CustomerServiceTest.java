package com.example.products_wishlist.api.customer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import com.example.products_wishlist.api.customer.dto.CustomerDetailDto;
import com.example.products_wishlist.api.customer.dto.CustomerDto;
import com.example.products_wishlist.api.product.exception.ProductExistException;
import com.example.products_wishlist.api.secutiry.model.Credentials;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private CustomerDto customerDto;

    private Set<String> products;

    private static final String DEFAULT_PATH = "/api/customers/";
    
    public void generateFakeData() throws Exception {  
        products = new LinkedHashSet<>();
        products.add("7be7243b-e938-fe5d-a71e-905fd7a9fbba");
        products.add("212d0f07-8f56-0708-971c-41ee78aadf2b");
        products.add("1bcd1b21-7205-4f02-227f-4c8c9e845ade");

        String email = UUID.randomUUID().toString() + "@gmail.com";
        String name = UUID.randomUUID().toString();
        
        customerDto = new CustomerDto(email, name, null);
    }

    private String getAuthorizationToken() throws Exception {
        Credentials credentials = new Credentials("admin", "password");
        String credentialsJson = mapper.writeValueAsString(credentials);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(credentialsJson))
        .andReturn();

        return result.getResponse().getHeader("Authorization");
    }
    
    private String createUser() throws Exception{
        String customerDtoJson = mapper.writeValueAsString(customerDto);

        return mockMvc.perform(MockMvcRequestBuilders.post(DEFAULT_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", getAuthorizationToken())
        .content(customerDtoJson))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();
    }

    private void getUser(String customerJsonString) throws Exception {
        //converte pata buscar o id
        CustomerDetailDto customerDetailDto = mapper.readValue(customerJsonString, CustomerDetailDto.class);

        //busca o usuário
        mockMvc.perform(MockMvcRequestBuilders.get(DEFAULT_PATH + customerDetailDto.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", getAuthorizationToken())
        .content(customerJsonString))
        .andExpect(status().isOk());
    }

	private void removeUser(String customerJsonString) throws Exception {        
        CustomerDetailDto customerDetailDto = mapper.readValue(customerJsonString, CustomerDetailDto.class);

        //remove o usuário
        mockMvc.perform(MockMvcRequestBuilders.delete(DEFAULT_PATH + customerDetailDto.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", getAuthorizationToken()))
        .andExpect(status().isNoContent());
    }
    
	public void updateUser(String customerJsonString) throws Exception {
        //Gera um novo nome para o usuario ja cadastrado
        CustomerDetailDto customerDetailDto = mapper.readValue(customerJsonString, CustomerDetailDto.class);
        String newName = UUID.randomUUID().toString();
        customerDetailDto.setName(newName);
        String updatedDtoJson = mapper.writeValueAsString(customerDetailDto);

        //atualiza o usuario
        String updatedJsonString = mockMvc.perform(MockMvcRequestBuilders.put(DEFAULT_PATH + customerDetailDto.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", getAuthorizationToken())
        .content(updatedDtoJson))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();
        
        CustomerDetailDto updatedDetailDto = mapper.readValue(updatedJsonString, CustomerDetailDto.class);
        assertEquals(newName, updatedDetailDto.getName());
    }

    public void createProduct(String customerJsonString) throws Exception {
        CustomerDetailDto customerDetailDto = mapper.readValue(customerJsonString, CustomerDetailDto.class);
        String productsJson = mapper.writeValueAsString(products);

        //cria o produto
        mockMvc.perform(MockMvcRequestBuilders.post(DEFAULT_PATH + customerDetailDto.getId() + "/products")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", getAuthorizationToken())
        .content(productsJson))
        .andExpect(status().isCreated());
    }

    @Test
	public void authenticated() throws Exception {
        Credentials credentials = new Credentials("admin", "password");
        String credentialsJson = mapper.writeValueAsString(credentials);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(credentialsJson))
        .andExpect(status().isOk());
    }

    @Test
	public void notAuthenticated() throws Exception {
        Credentials credentials = new Credentials("admin", "wrongpassword");
        String credentialsJson = mapper.writeValueAsString(credentials);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(credentialsJson))
        .andExpect(status().isUnauthorized());
    }

    @Test
	public void createUserTest() throws Exception {
        this.generateFakeData();
        this.createUser();
    }

    @Test
	public void getUserTest() throws Exception {
        this.generateFakeData();
        String customerJsonString = this.createUser();
        this.getUser(customerJsonString);
    }

    @Test
	public void removeUserTest() throws Exception {
        this.generateFakeData();
        String customerJsonString = this.createUser();
        this.removeUser(customerJsonString);
    }

    @Test
	public void updateUserTest() throws Exception {
        this.generateFakeData();
        String customerJsonString = this.createUser();
        this.updateUser(customerJsonString);
    }

    @Test
	public void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(DEFAULT_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", getAuthorizationToken()))
        .andExpect(status().isOk());
    }

    @Test
	public void createProduct() throws Exception {
        this.generateFakeData();
        String customerJsonString = this.createUser();
        this.createProduct(customerJsonString);
    }

    @Test
    public void createDuplicateProduct() throws Exception {
        this.generateFakeData();
        String customerJsonString = this.createUser();
        this.createProduct(customerJsonString);

        CustomerDetailDto customerDetailDto = mapper.readValue(customerJsonString, CustomerDetailDto.class);
        String productsJson = mapper.writeValueAsString(products);

        //cria o mesmo produto novamente e testa a exception
        mockMvc.perform(MockMvcRequestBuilders.post(DEFAULT_PATH + customerDetailDto.getId() + "/products")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", getAuthorizationToken())
        .content(productsJson))
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductExistException));    
    }

    @Test
	public void removeProduct() throws Exception {
        this.generateFakeData();
        String customerJsonString = this.createUser();
        this.createProduct(customerJsonString);

        CustomerDetailDto customerDetailDto = mapper.readValue(customerJsonString, CustomerDetailDto.class);
        String productsJson = mapper.writeValueAsString(products);

        //remove o produto
        mockMvc.perform(MockMvcRequestBuilders.delete(DEFAULT_PATH + customerDetailDto.getId() + "/products")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", getAuthorizationToken())
        .content(productsJson))
        .andExpect(status().isNoContent());
    }
}
