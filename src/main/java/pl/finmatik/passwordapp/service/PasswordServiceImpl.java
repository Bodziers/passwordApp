package pl.finmatik.passwordapp.service;

import org.springframework.stereotype.Service;
import pl.finmatik.passwordapp.model.Password;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PasswordServiceImpl implements PasswordService {

    private Random random;
    //to check why this is necessary
    {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Password> generateRandomPassword(int noOfDigits, boolean letters, boolean digits, boolean specialSigns) {
        if (noOfDigits < 0 || noOfDigits > 32) {
            throw new IllegalArgumentException("Number of digits must be between 1 and 32");
        } else {

            List<Password> passList = new ArrayList<>();
            String pass;
            long id;

            for (int i = 1; i < 11; i++) {
                id = i;
                if (letters && digits && specialSigns) {
                    pass = generateRandomChartWithAllSymbols(noOfDigits);
                } else if (letters && digits && !specialSigns) {
                    pass = generateRandomLettersAndDigits(noOfDigits);
                } else if (letters && !digits && !specialSigns) {
                    pass = generateRandomLetters(noOfDigits);
                } else if (!letters && digits && !specialSigns) {
                    pass = generateRandomDigits(noOfDigits);
                } else if (letters && !digits && specialSigns) {
                    pass = generateRandomLettersWithSpecialChars(noOfDigits);
                } else if (!letters && digits && specialSigns) {
                    pass = generateRandomDigitsWithSpecialChars(noOfDigits);
                } else {
                    pass = generateRandomChartWithAllSymbols(20);
                }
                Password passwordObj = new Password();
                passwordObj.setId(id);
                passwordObj.setPassword(pass);
                passList.add(passwordObj);
            }
            return passList;
        }
    }


    @Override
    public String generateRandomChartWithAllSymbols(int noOfDigits) {
        StringBuilder password = new StringBuilder();

        for (int i = 1; i<=noOfDigits ; i++) {
            //all ASCII char are from 33 to 125 but without 122
            int randomNo = random.nextInt(125);
            if (randomNo < 33 || randomNo == 122) {
                i--;
            } else {
                //add char to string
                password.append((char) randomNo);
            }
        }
        return password.toString();
    }

    @Override
    public String generateRandomDigits(int noOfDigits) {
        StringBuilder password = new StringBuilder();

        for (int i = 1; i<=noOfDigits ; i++) {
            //all asci digits chart are from 48 to 57
            int randomNo = random.nextInt(57);
            if (randomNo <48) {
                i--;
            } else {
                password.append((char) randomNo);
            }
        }
        return password.toString();
    }

    @Override
    public String generateRandomLetters(int noOfDigits) {
        StringBuilder password = new StringBuilder();

        for (int i = 1; i<=noOfDigits ; i++) {
            //big letters from 65 to 90
            //small letters 97 to 121
            int randomNo = random.nextInt(121);
            if (randomNo < 65 || (randomNo > 90 && randomNo <97)) {
                i--;
            } else {
                password.append((char) randomNo);
            }
        }
        return password.toString();
    }

    @Override
    public String generateRandomLettersAndDigits(int noOfDigits) {
        StringBuilder password = new StringBuilder();

        for (int i = 1; i<=noOfDigits ; i++) {
            //big letters from 65 to 90
            //small letters 97 to 121
            //all asci digits chart are from 48 to 57
            int randomNo = random.nextInt(121);
            if (randomNo < 48) {
                i--;
            } else if (randomNo > 57 && randomNo < 65) {
                i--;
            } else if (randomNo > 90 && randomNo < 97) {
                i--;
            } else {
                password.append((char) randomNo);
            }
        }
        return password.toString();
    }

    @Override
    public String generateRandomLettersWithSpecialChars(int noOfDigits) {
        StringBuilder password = new StringBuilder();
        //big letters from 65 to 90
        //small letters 97 to 121
        //all ASCII special char are from 33 to 47 and 58 to 64 and 91 to 96 and 123 to 125
        for (int i = 1; i <= noOfDigits; i++) {

            int randomNo = random.nextInt(125);
            if (randomNo < 33) {
                i--;
            } else if ((randomNo > 47 && randomNo < 58) || randomNo == 122) {
                i--;
            } else {
                {
                    //add char to string
                    password.append((char) randomNo);
                }
            }
        }
        return password.toString();
    }

    @Override
    public String generateRandomDigitsWithSpecialChars(int noOfDigits) {
        StringBuilder password = new StringBuilder();
        //all asci digits chart are from 48 to 57
        //all ASCII special char are from 33 to 47 and 58 to 64 and 91 to 96 and 123 to 125
        for (int i = 1; i <= noOfDigits; i++) {

            int randomNo = random.nextInt(125);
            if (randomNo < 48) {
                i--;
            } else if (randomNo > 64 && randomNo < 91) {
                i--;
            } else if (randomNo > 96 && randomNo < 122) {
                i--;
            } else {
                {
                    //add char to string
                    password.append((char) randomNo);
                }
            }
        }
        return password.toString();
    }

    @Override
    public List<Password> generateRandomWords(int noOfWords) {

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
        List<Password> words = new ArrayList<>();

        for (int i=1; i<=noOfWords; i++){
            try {
                String filePath = "src/main/resources/static/dictionary/words_usa.txt";
                long randomNo;
                try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
                    file.length();
                    randomNo = random.nextLong(file.length());
                }
                Password word = new Password(((long) i), readLineFromFile(filePath, randomNo));
                words.add(word);
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
