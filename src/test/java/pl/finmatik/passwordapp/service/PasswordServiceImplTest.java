package pl.finmatik.passwordapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.finmatik.passwordapp.model.Password;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordServiceImplTest {
    PasswordServiceImpl passwordServiceImpl;

    @BeforeEach
    void setUp() {
        passwordServiceImpl = new PasswordServiceImpl();
    }

    @Test
    @DisplayName("Should generate password with all symbols, digits and letters and desire number of chars")
    void shouldGenerateRandomChartWithAllSymbolsTest() throws Exception {

        //given
        int noOfDigits = 10;
        //when
        String randomPassword = passwordServiceImpl.generateRandomChartWithAllSymbols(noOfDigits);
        //then
        assertEquals(noOfDigits, randomPassword.length());
    }
    @Test
    @DisplayName("Should generate password with letters and desire number of chars")
    void shouldGenerateRandomPasswordWithLettersTest() throws Exception {
        //given
        int noOfDigits = 10;
        //when
        String randomPassword = passwordServiceImpl.generateRandomLetters(noOfDigits);
        //then
        assertEquals(noOfDigits, randomPassword.length());
    }


    @Test
    @DisplayName("Should generate password with digits and desire number of chars")
    void shouldGenerateRandomPasswordWithDigitsTest() throws Exception {
        //given
        int noOfDigits = 10;
        //when
        String randomPassword = passwordServiceImpl.generateRandomDigits(noOfDigits);
        //then
        assertEquals(noOfDigits, randomPassword.length());
    }

    @Test
    @DisplayName("Should generate password with letters, digits and desire number of chars")
    void shouldGenerateRandomPasswordWithDigitsAndLettersTest() throws Exception {
        //given
        int noOfDigits = 10;
        //when
        String randomPassword = passwordServiceImpl.generateRandomLettersAndDigits(noOfDigits);
        //then
        assertEquals(noOfDigits, randomPassword.length());
    }

    @Test
    @DisplayName("Should generate password with letters, special signs with desire number of chars")
    void shouldGenerateRandomPasswordWithLettersAndSpecialSignsTest() throws Exception {
        //given
        int noOfDigits = 10;
        //when
        String randomPassword = passwordServiceImpl.generateRandomLettersWithSpecialChars(noOfDigits);
        //then
        assertEquals(noOfDigits, randomPassword.length());
    }
    @Test
    @DisplayName("Should generate password with digits, special signs with desire number of chars")
    void shouldGenerateRandomPasswordWithDigitsAndSpecialSignsTest() throws Exception {
        //given
        int noOfDigits = 10;
        //when
        String randomPassword = passwordServiceImpl.generateRandomDigitsWithSpecialChars(noOfDigits);
        //then
        assertEquals(noOfDigits, randomPassword.length());
    }

    @Test
    @DisplayName("Should generate list with passwords")
    void shouldGenerateListOfPasswordsTest() throws Exception {
        //given
        int sizeOfList = 10;
        int noOfDigits = 10;
        boolean letters = false;
        boolean digits = false;
        boolean specialSign = true;
        //when
        List<Password> passwordsList= passwordServiceImpl.generateRandomPassword(noOfDigits,letters,digits,specialSign);
        //then
        assertEquals(sizeOfList,passwordsList.size());
    }
}
