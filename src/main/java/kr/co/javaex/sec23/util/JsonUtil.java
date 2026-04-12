package kr.co.javaex.sec23.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtil {

    private ObjectMapper mapper = new ObjectMapper();

    // 파일 저장
    public void save(String fileName, Object data) {
        try {
            mapper.writeValue(new File(fileName), data);
        } catch (IOException e) {
            System.out.println("파일 저장 실패");
        }
    }

    // 파일 읽기
    public <T> T load(String fileName, Class<T> clazz) {
        try {
            return mapper.readValue(new File(fileName), clazz);
        } catch (IOException e) {
            System.out.println("파일 읽기 실패");
            return null;
        }
    }
}