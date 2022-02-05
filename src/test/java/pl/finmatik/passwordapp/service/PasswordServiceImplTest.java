package pl.finmatik.passwordapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.finmatik.passwordapp.model.Password;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    @DisplayName("Should find first line from txt file")
    void getFirstLineFromDictionaryFileTest() throws Exception {
        //given
        String filePath = "src/main/resources/static/dictionary/words_usa.txt";
        //when
        String word = passwordServiceImpl.readLineFromFile(filePath, 0);
        //then
        assertEquals("a", word);
    }

    @Test
    @DisplayName("Should find last line from txt file")
    void getLastLineFromDictionaryFileTest() throws Exception {
        //given
        String filePath = "src/main/resources/static/dictionary/words_usa.txt";
        long filesize = 0;
        try {
            RandomAccessFile file = new RandomAccessFile(filePath, "r");
            filesize = file.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //when
        String word = passwordServiceImpl.readLineFromFile(filePath, filesize-8);
        //then
        assertEquals("poque", word);
    }

    @Test
    @DisplayName("Should find random word from txt file")
    void getRandomWordFromDictionaryFileTest() throws Exception {
        //given
        String filePath = "src/main/resources/static/dictionary/words_usa.txt";
        //when
        String word = passwordServiceImpl.generateRandomWords();
        //then
        assertNotNull(word);
    }
}
