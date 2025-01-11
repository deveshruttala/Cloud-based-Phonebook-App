package com.north.phonebook.app.model.dto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class PhoneBookDTOTest {

    private Validator validator;

    @Before
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testContactNameNullOrEmpty() {
        final PhoneBookDTO contactNameNull = new PhoneBookDTO(null, "+38976668498");
        Set<ConstraintViolation<PhoneBookDTO>> constraintViolationsNameNull = validator.validate(contactNameNull);
        Assert.assertFalse(constraintViolationsNameNull.isEmpty());

        final PhoneBookDTO contactNameEmpty = new PhoneBookDTO("", "+38976668498");
        Set<ConstraintViolation<PhoneBookDTO>> constraintViolationsNameEmpty = validator.validate(contactNameEmpty);
        Assert.assertFalse(constraintViolationsNameEmpty.isEmpty());
    }

    @Test
    public void testContactPhoneNumberNullOrEmpty() {
        final PhoneBookDTO contactPhoneNumberNull = new PhoneBookDTO("Gjurgjica", null);
        Set<ConstraintViolation<PhoneBookDTO>> constraintViolationsPhoneNumberNull = validator.validate(contactPhoneNumberNull);
        Assert.assertTrue(constraintViolationsPhoneNumberNull.isEmpty());

        final PhoneBookDTO contactPhoneNumberEmpty = new PhoneBookDTO("Gjurgjica", "");
        Set<ConstraintViolation<PhoneBookDTO>> constraintViolationsPhoneNumberEmpty = validator.validate(contactPhoneNumberEmpty);
        Assert.assertFalse(constraintViolationsPhoneNumberEmpty.isEmpty());
    }

    @Test
    public void testContactPhoneNumberWrongFormat() {
        final PhoneBookDTO contactPhoneNumberNull = new PhoneBookDTO("Gjurgjica", "+38973963258");
        Set<ConstraintViolation<PhoneBookDTO>> constraintViolationsPhoneNumberNull = validator.validate(contactPhoneNumberNull);
        Assert.assertFalse(constraintViolationsPhoneNumberNull.isEmpty());
    }
}
