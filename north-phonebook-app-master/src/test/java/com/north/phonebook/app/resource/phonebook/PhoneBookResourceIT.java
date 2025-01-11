package com.north.phonebook.app.resource.phonebook;

import com.north.phonebook.app.NorthPhonebookAppApplication;
import com.north.phonebook.app.dao.phonebook.PhoneBookDao;
import com.north.phonebook.app.model.dto.PhoneBookDTO;
import com.north.phonebook.app.model.entity.PhoneBook;
import com.north.phonebook.app.service.phonebook.PhoneBookService;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.*;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NorthPhonebookAppApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PhoneBookResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    @Autowired
    private PhoneBookDao phoneBookDao;

    @Autowired
    private PhoneBookService phoneBookService;

    @Test
    @Transactional
    public void testSearchContactsEmptyResult() throws JSONException {

        final String name = "Gjurgjica";
        final String phoneNumber = "+38976668498";

        final List<PhoneBook> phoneBookList = phoneBookDao.findContacts(name, phoneNumber);
        Assert.assertEquals(0, phoneBookList.size());

        final List<PhoneBookDTO> phoneBookDTOS = phoneBookService.findContacts(name, phoneNumber);
        Assert.assertEquals(0, phoneBookDTOS.size());

        final String baseUrl = "http://localhost:" + port;
        final String path = "/phonebook/contacts";
        URI uri = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .path(path)
                .queryParam("name", name)
                .queryParam("phoneNumber", phoneNumber)
                .build()
                .toUri();
        final ResponseEntity<?> responseEntity = this.restTemplate.getForEntity(uri, String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals("No search result for the given search criteria", responseEntity.getBody());
    }

    // Problem with the encoding of the special character, like '+'
    // When the call is executed from REST API, in the dao always returns 0.
    // When the call is executed from the DAO or Service, it returns that one record.
    @Test
    @Transactional
    public void testSearchContactResultNotEmpty() {

        final String name = "Gjurgjica";
        final String phoneNumber = "+38976668498";

        final ModelMapper modelMapper = new ModelMapper();
        final PhoneBookDTO phoneBookDTO = new PhoneBookDTO(name, phoneNumber);
        final PhoneBook phoneBook = modelMapper.map(phoneBookDTO, PhoneBook.class);
        phoneBookDao.createContact(phoneBook);

        final List<PhoneBook> phoneBookList = phoneBookDao.findContacts(name, phoneNumber);
        Assert.assertEquals(1, phoneBookList.size());

        final List<PhoneBookDTO> phoneBookDTOResult = phoneBookService.findContacts(name, phoneNumber);
        Assert.assertEquals(1, phoneBookDTOResult.size());

        final String baseUrl = "http://localhost:" + port;
        final String path = "/phonebook/contacts";
        URI uri = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .path(path)
                .queryParam("name", name)
                .queryParam("phoneNumber", UriEncoder.encode(phoneNumber))
                .build().toUri();
        final ResponseEntity<?> responseEntity = this.restTemplate.getForEntity(uri, String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals("No search result for the given search criteria", responseEntity.getBody());
    }

    // When the call is executed from REST API, in the dao always returns 0.
    // When the call is executed from the DAO or Service, it returns that one record.
    @Test
    @Transactional
    public void testSearchContactResultPhoneNumberNull() {

        final String name = "Gjurgjica";
        final String phoneNumber = null;

        final ModelMapper modelMapper = new ModelMapper();
        final PhoneBookDTO phoneBookDTO = new PhoneBookDTO(name, phoneNumber);
        final PhoneBook phoneBook = modelMapper.map(phoneBookDTO, PhoneBook.class);
        phoneBookDao.createContact(phoneBook);

        final List<PhoneBook> phoneBookList = phoneBookDao.findContacts(name, phoneNumber);
        Assert.assertEquals(1, phoneBookList.size());

        final List<PhoneBookDTO> phoneBookDTOResult = phoneBookService.findContacts(name, phoneNumber);
        Assert.assertEquals(1, phoneBookDTOResult.size());

        final String baseUrl = "http://localhost:" + port;
        final String path = "/phonebook/contacts";
        URI uri = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .path(path)
                .queryParam("name", name)
                .build().toUri();
        final ResponseEntity<?> responseEntity = this.restTemplate.getForEntity(uri, String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals("No search result for the given search criteria", responseEntity.getBody());
    }

    @Test
    public void testCreatePhonebookContactInvalidParameters() throws Exception {
        final PhoneBookDTO phoneBookDTO = new PhoneBookDTO("Gjurgjica", "++3897666849");
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            validateParameter(phoneBookDTO);
        });

        final String baseUrl = "http://localhost:" + port;
        final String path = "/phonebook/contacts";
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + path))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse();
        Assert.assertEquals(400, response.getStatus());
    }

    private void validateParameter(final PhoneBookDTO phoneBookDTO) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PhoneBookDTO>> violations = validator.validate(phoneBookDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
