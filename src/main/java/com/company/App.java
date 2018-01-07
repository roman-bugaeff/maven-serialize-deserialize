package com.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class App {
    public static final String PATHFILE  = "resourses/users.bin";
    public static ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        List<User> deserialize = deserialize();
        serialize(deserialize);
        print(deserialize);
    }

    private static void serialize(List<User> list) throws IOException {
        File file = new File(PATHFILE);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        User user = new User();

        System.out.println("Input user name: ");
        user.setUserName(getFromKeyboard(br));

        System.out.println("Input password: ");
        user.setPassword(getFromKeyboard(br));

        System.out.println("Input First name: ");
        user.setFirstName(getFromKeyboard(br));

        System.out.println("Input  Last Name: ");
        user.setLastName(getFromKeyboard(br));

        System.out.println("Input email: ");
        user.setEmail(getFromKeyboard(br));

        list.add(user);

        String json = getJson(list);

        writeContent(json);

    }

    private static void writeContent(String json) throws IOException {
        FileUtils.write(new File(PATHFILE), json);
    }

    private static String getJson(List<User> list) throws JsonProcessingException {
        return mapper.writeValueAsString(list);
    }

    private static List<User> deserialize() throws IOException, ClassNotFoundException {
        List<User> list = null;
        File file = new File(PATHFILE);
        if(!file.exists()){
            return new ArrayList<>();
        }else{
            String fileContent = getFileContent();
            list = getUsersFromFile(fileContent);
        }
        return list;
    }

    private static void print(List<User> list){
        list.forEach(user -> System.out.println(String.format
                ("UserName: %s | Password: %s | FirstName: %s | LastName: %s | e-mail: %s",
                        user.getUserName(),
                        user.getPassword(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail())));
    }

    private static List<User> getUsersFromFile(String jsonContent) throws IOException{
        return mapper.readValue(jsonContent, new TypeReference<List<User>>(){});
    }

    private static String getFileContent() throws IOException{
        List<String> strings = FileUtils.readLines(new File(PATHFILE));
        StringBuilder builder = new StringBuilder();
        strings.forEach(builder::append);
        return builder.toString();
    }

    public static String getFromKeyboard(BufferedReader br) {
        String result = "";
        while (true) {
            try {
                result = br.readLine();
            } catch (IOException e) {
                // nooperation
            }
            if (result != "") {
                break;
            }
            System.out.println("Empty name not allowed!");
        }
        return result;
    }


}


