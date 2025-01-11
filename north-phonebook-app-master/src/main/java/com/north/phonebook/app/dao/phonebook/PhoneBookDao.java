package com.north.phonebook.app.dao.phonebook;

import com.north.phonebook.app.model.entity.PhoneBook;

import java.util.List;

public interface PhoneBookDao {

    /**
     * List phonebook contacts.
     *
     * @param name        the name
     * @param phoneNumber the phoneNumber
     * @return {@link List} of {@link PhoneBook}
     */
    List<PhoneBook> findContacts(String name, String phoneNumber);

    /**
     * Create new phonebook contact.
     *
     * @param phoneBook the {@link PhoneBook}
     */
    void createContact(PhoneBook phoneBook);

}
