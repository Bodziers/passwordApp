package pl.finmatik.passwordapp.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import pl.finmatik.passwordapp.model.Password;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PasswordServiceImpl implements PasswordService {

    private static Random random = new Random();
    private static final String dictionaryPath = "src/main/resources/dictionary/words_usa.txt";
    static {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Password> generateRandomPassword(int noOfDigits,
                                                 boolean letters,
                                                 boolean digits,
                                                 boolean specialSigns,
                                                 boolean words,
                                                 int numberOfWords) {
        if (noOfDigits < 0 || noOfDigits > 32) {
            throw new IllegalArgumentException("Number of digits must be between 1 and 32");
        } else {

            List<Password> passList = new ArrayList<>();
            String pass;
            List<String> charactersList = new ArrayList<>();
            //setting default value to number of words that allows to generate passwords
            if (numberOfWords <1 || numberOfWords >4) {
                numberOfWords=3;
            }
            //new password generation method
            for (int i = 1; i < 11; i++) {
                if (digits) {
                    charactersList.addAll(List.of(digitsList(noOfDigits)));
                }
                if (letters) {
                    charactersList.addAll(List.of(lettersList(noOfDigits)));
                }
                if (specialSigns) {
                    charactersList.addAll(List.of(specialSignsList(noOfDigits)));
                }
                Collections.shuffle(charactersList);
                List<String> pipeline = charactersList.stream().limit(noOfDigits).collect(Collectors.toList());

                if (words) {
                    pipeline.addAll(wordsList(numberOfWords));
                    Collections.shuffle(pipeline);
                }
                pass = pipeline.stream().map(Objects::toString).collect(Collectors.joining());
                Password password = new Password ((long) i,pass);
                passList.add(password);
            }
            return passList;

        }
    }

    //new password methods to generate array of chars as string object
    //this allows to connect needed arrays and randomize them
    @Override
    public String[] digitsList(int noOfDigits) {
        String digitsString = RandomStringUtils.randomNumeric(noOfDigits);
        return digitsString.codePoints()
                .mapToObj(cp -> new String(Character.toChars(cp)))
                .toArray(size -> new String[size]);
    }

    @Override
    public String[] lettersList(int noOfDigits) {
        String lettersString = RandomStringUtils.randomAlphabetic(noOfDigits);
        return lettersString.codePoints()
                .mapToObj(cp -> new String(Character.toChars(cp)))
                .toArray(size -> new String[size]);
    }

    @Override
    public String[] specialSignsList(int noOfDigits) {
        char[] chars = { '!','@','#','$','%','^','&','*','(',')','{','}','[',']','?','>','<','.','/','\\'};
        String specialSignsString = RandomStringUtils.random(noOfDigits,
                0,chars.length,false,false,chars);
        return specialSignsString.codePoints()
                .mapToObj(cp -> new String(Character.toChars(cp)))
                .toArray(size -> new String[size]);
    }

    public List<String> wordsList(int noOfWords) {
        List<String> words = new ArrayList<>();
        for (int i=1; i<=noOfWords; i++){
            try {

                long randomNo;
                try (RandomAccessFile file = new RandomAccessFile(dictionaryPath, "r")) {
                    file.length();
                    randomNo = random.nextLong(file.length());
                }
                String word = readLineFromFile(dictionaryPath, randomNo);
                words.add(word);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return words;
    }


    @Override
    public List<String> generateRandomWords(int noOfWords) {

         /*  1. random line (77722 lines in words_us)
         * 2. query random line
         * 3. read word
         * 4. repeat until all words are received
         * 5. make array off all needed words (10 password * number of selected words in password, max 50 words)
         * 5. choose words from list and combine them with characters in password
         * 6. return password
         * 7. return list of all 10 passwords
         */

        //should i use already made object password? or create words - same variables?
        List<String> words = new ArrayList<>();

        for (int i=1; i<=noOfWords; i++){
            try {
                long randomNo;
                try (RandomAccessFile file = new RandomAccessFile(dictionaryPath, "r")) {
                    file.length();
                    randomNo = random.nextLong(file.length());
                }
                words.add(readLineFromFile(dictionaryPath, randomNo));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return words;
    }

    /*
    * Method to read line from file pointed to by filePath and starting from byte position in file
    * pointed by seek long parameter. Firs line from seek position is read until end of line character "/n",
    * second line is red in full length and return as string value.
     */
    public String readLineFromFile(String filePath, long seek) throws IOException {
        String line;
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            file.length();
            file.seek(seek);
            file.readLine();
            line = file.readLine();
        }
        return line;
    }
}
