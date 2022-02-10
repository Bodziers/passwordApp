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
    @DisplayName("Should generate only letters as array of strings")
    void shouldGenerateRandomLettersListAsStringArrayTest() throws Exception {

        //given
        int noOfDigits = 10;
        //when
        String[] randomLettersList = passwordServiceImpl.lettersList(noOfDigits);
        //then
        assertEquals(noOfDigits, randomLettersList.length);
    }

    @Test
    @DisplayName("Should generate only digits as array of strings")
    void shouldGenerateRandomDigitsListAsStringArrayTest() throws Exception {

        //given
        int noOfDigits = 10;
        //when
        String[] randomDigitsList = passwordServiceImpl.digitsList(noOfDigits);
        //then
        assertEquals(noOfDigits, randomDigitsList.length);
    }

    @Test
    @DisplayName("Should generate only special signs as array of strings")
    void shouldGenerateRandomSpecialSignsListAsStringArrayTest() throws Exception {

        //given
        int noOfDigits = 10;
        //when
        String[] randomSpecialSignsList = passwordServiceImpl.specialSignsList(noOfDigits);
        //then
        assertEquals(noOfDigits, randomSpecialSignsList.length);
    }
    @Test
    @DisplayName("Should generate words as array of strings")
    void shouldGenerateRandomWordsListAsStringArrayTest() throws Exception {

        //given
        int noOfWords = 10;
        //when
        List<String> randomSpecialSignsList = passwordServiceImpl.wordsList(noOfWords);
        //then
        assertEquals(noOfWords, randomSpecialSignsList.size());
    }




    @Test
    @DisplayName("Should generate list with passwords")
    void shouldGenerateListOfPasswordsTest() throws Exception {
        //given
        int sizeOfList = 10;
        int noOfDigits = 10;
        boolean letters = false;
        boolean digits = true;
        boolean specialSign = true;
        boolean words = true;
        int numberOfWords = 4;
        //when
        List<Password> passwordsList= passwordServiceImpl.generateRandomPassword(noOfDigits,
                                                                                letters,
                                                                                digits,
                                                                                specialSign,
                                                                                words,
                                                                             numberOfWords);
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
    @DisplayName("Should find random words from txt file")
    void getRandomWordsFromDictionaryFileNotNullTest() throws Exception {
        //given
        String filePath = "src/main/resources/static/dictionary/words_usa.txt";
        //when
        List<String> words = passwordServiceImpl.generateRandomWords(2);
        //then
        assertNotNull(words);
    }
    @Test
    @DisplayName("Should find 10 random words from txt file")
    void getTenRandomWordsFromDictionaryFileTest() throws Exception {
        //given
        String filePath = "src/main/resources/static/dictionary/words_usa.txt";
        //when
        List<String> words = passwordServiceImpl.generateRandomWords(3);
        //then
        assertEquals(3,words.size());
    }
}
