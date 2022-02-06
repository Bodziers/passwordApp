package pl.finmatik.passwordapp.service;

import pl.finmatik.passwordapp.model.Password;

import java.util.List;

public interface PasswordService {
    public String generateRandomChartWithAllSymbols(int noOfDigits);
    public String generateRandomDigits(int noOfDigits);
    public String generateRandomLetters(int noOfDigits);
    public String generateRandomLettersAndDigits(int noOfDigits);
    public String generateRandomLettersWithSpecialChars(int noOfDigits);
    public String generateRandomDigitsWithSpecialChars(int noOfDigits);
    public List<Password> generateRandomWords(int noOfWords);
    public List<Password> generateRandomPassword(int noOfDigits, boolean letters, boolean digits, boolean specialSigns);
}
