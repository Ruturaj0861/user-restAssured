package utils;

import java.util.UUID;

public class RandomData {

    public static String randomEmail(){

        return "tests/user" + UUID.randomUUID() + "@test.com";

    }

}