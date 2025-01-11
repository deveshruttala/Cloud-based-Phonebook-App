package com.north.phonebook.app.resource.phonebook;

import com.north.phonebook.app.model.dto.PhoneBookDTO;
import com.north.phonebook.app.service.phonebook.PhoneBookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Phone Book Resource Api.
 */
@RestController
@RequestMapping("/phonebook/contacts")
public class PhoneBookResourceApi {

    private static final Logger logger = LogManager.getLogger(PhoneBookResourceApi.class);

    private final PhoneBookService phoneBookService;

    @Autowired
    public PhoneBookResourceApi(final PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    /**
     * List phonebook contacts based on parameter fields.
     *
     * @param name        the name
     * @param phoneNumber the phoneNumber
     * @return {@link ResponseEntity}
     */
    @GetMapping
    public ResponseEntity<?> searchContacts(@RequestParam(value = "name", required = false) final String name,
                                            @RequestParam(value = "phoneNumber", required = false) final String phoneNumber) {

        logger.info("Received request for searching contacts with name: {}, phoneNumber: {}", name, phoneNumber);
        final List<PhoneBookDTO> response = phoneBookService.findContacts(name, phoneNumber);
        if (response != null && response.size() > 0) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("No search result for the given search criteria", HttpStatus.OK);
    }

    /**
     * Create new phonebook contact.
     *
     * @param phoneBookDTO the {@link PhoneBookDTO}
     * @return {@link ResponseEntity}
     */
    @PostMapping
    public ResponseEntity<?> createContacts(@Valid @RequestBody final PhoneBookDTO phoneBookDTO) {

        logger.info("Received request for creating new phonebook contact with data: {}.", phoneBookDTO);
        phoneBookService.createContact(phoneBookDTO);
        return new ResponseEntity<>("Successfully created phonebook contact", HttpStatus.OK);
    }
}
