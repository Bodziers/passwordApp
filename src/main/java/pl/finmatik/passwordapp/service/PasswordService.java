package pl.finmatik.passwordapp.service;

import pl.finmatik.passwordapp.model.Password;

import java.util.List;

public interface PasswordService {
    public String[] digitsList(int noOfDigits);
    public String[] lettersList(int noOfDigits);
    public String[] specialSignsList(int noOfDigits);

    public List<String> generateRandomWords(int noOfWords);
    public List<Password> generateRandomPassword(int noOfDigits,
                                                 boolean letters,
                                                 boolean digits,
                                                 boolean specialSigns,
                                                 boolean words,
                                                 int numberOfWords);
}
