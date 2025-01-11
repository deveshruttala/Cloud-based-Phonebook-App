package com.north.phonebook.app.service.phonebook;

import com.north.phonebook.app.model.dto.PhoneBookDTO;

import java.util.List;

public interface PhoneBookService {

    /**
     * List phonebook contacts.
     *
     * @param name        the name
     * @param phoneNumber the phoneNumber
     * @return {@link List} of {@link PhoneBookDTO}
     */
    List<PhoneBookDTO> findContacts(String name, String phoneNumber);

    /**
     * Create new phonebook contact.
     *
     * @param phoneBookDTO the {@link PhoneBookDTO}
     */
    void createContact(PhoneBookDTO phoneBookDTO);
}
