package pl.finmatik.passwordapp.service;

import org.springframework.stereotype.Service;
import pl.finmatik.passwordapp.model.Password;

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
            String pass = "";
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
                password.append(Character.toString ((char) randomNo));
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
                password.append(Character.toString ((char) randomNo));
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
                password.append(Character.toString ((char) randomNo));
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
                password.append(Character.toString ((char) randomNo));
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
                    password.append(Character.toString((char) randomNo));
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
                    password.append(Character.toString((char) randomNo));
                }
            }
        }
        return password.toString();
    }
}
