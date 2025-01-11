package com.north.phonebook.app.service.phonebook.impl;

import com.north.phonebook.app.dao.phonebook.PhoneBookDao;
import com.north.phonebook.app.model.dto.PhoneBookDTO;
import com.north.phonebook.app.model.entity.PhoneBook;
import com.north.phonebook.app.service.phonebook.PhoneBookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneBookServiceImpl implements PhoneBookService {

    private static final Logger logger = LogManager.getLogger(PhoneBookServiceImpl.class);

    private final PhoneBookDao phoneBookDao;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public PhoneBookServiceImpl(final PhoneBookDao phoneBookDao) {
        this.phoneBookDao = phoneBookDao;
    }

    @Override
    public List<PhoneBookDTO> findContacts(final String name, final String phoneNumber) {

        logger.info("Find phonebook contacts with name: {} and phoneNumber: {}", name, phoneNumber);

        final List<PhoneBookDTO> phoneBookDTOS = new ArrayList<>();
        final List<PhoneBook> phoneBook = phoneBookDao.findContacts(name, phoneNumber);
        phoneBook.forEach(pB -> phoneBookDTOS.add(modelMapper.map(pB, PhoneBookDTO.class)));

        logger.info("Successfully returned phonebook contacts. Number of records: {}.", phoneBookDTOS.size());
        return phoneBookDTOS;
    }

    @Transactional
    @Override
    public void createContact(final PhoneBookDTO phoneBookDTO) {

        logger.info("Create phonebook contact with data: {}", phoneBookDTO);

        phoneBookDao.createContact(modelMapper.map(phoneBookDTO, PhoneBook.class));

        logger.info("Successfully created phonebook contact");
    }
}
