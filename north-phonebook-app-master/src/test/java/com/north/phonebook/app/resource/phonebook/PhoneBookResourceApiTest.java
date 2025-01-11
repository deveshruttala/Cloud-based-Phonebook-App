package com.north.phonebook.app.resource.phonebook;

import com.north.phonebook.app.model.dto.PhoneBookDTO;
import com.north.phonebook.app.service.phonebook.PhoneBookService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class PhoneBookResourceApiTest {

    @InjectMocks
    private PhoneBookResourceApi phoneBookResourceApi;

    @Mock
    private PhoneBookService phoneBookService;

    @Test
    public void testCreateContactValidPayload() {
        final PhoneBookDTO contact = new PhoneBookDTO("Gjurgjica", "+38976668498");
        Mockito.doNothing().when(phoneBookService).createContact(contact);
        final ResponseEntity<?> responseEntity = phoneBookResourceApi.createContacts(contact);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
