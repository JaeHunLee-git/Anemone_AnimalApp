//package com.sds.animalapp.model.animal;
//
//import com.sds.animalapp.domain.Animal;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//import java.util.List;
//
//@Service
//public class AnimalApiScheduler {
//
//    @Autowired
//    private AnimalApiService animalApiService;
//
//    @Autowired
//    private AnimalService animalService; // 기존 데이터를 삭제하고 삽입할 서비스
//
//    private static final String LAST_EXECUTION_TIME_FILE = "lastExecutionTime.txt";
//    private LocalDateTime lastExecutionTime;
//
//    @PostConstruct
//    public void init() {
//        // 마지막 실행 시간을 파일에서 읽어옴
//        lastExecutionTime = readLastExecutionTime();
//        if (lastExecutionTime == null || lastExecutionTime.isBefore(LocalDateTime.now().minusDays(1))) {
//            // 마지막 실행 시간이 없거나, 마지막 실행 시간이 하루 이상 지난 경우
//            fetchData();
//        }
//    }
//
//    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
//    public void fetchData() {
//        try {
//            List<Animal> animalList = animalApiService.call(); // API 호출로 새로운 데이터 받아옴
//
//            // 기존 데이터 업데이트 및 새 데이터 추가 로직
//            for (Animal animal : animalList) {
//                animal.setSexCd(convertSexCd(animal.getSexCd()));
//                animal.setNeuterYn(convertNeuterYn(animal.getNeuterYn()));
//                Animal existingAnimal = animalService.selectByDesertionNo(animal.getDesertionNo());
//                if (existingAnimal == null) {
//                    // 새로운 데이터라면 추가
//                    animalService.insert(animal);
//                } else {
//                    // 기존 데이터를 업데이트
//                    animalService.update(animal);
//                }
//            }
//
//            lastExecutionTime = LocalDateTime.now();
//            writeLastExecutionTime(lastExecutionTime);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void callAnimalApiAndUpdateLastExecutionTime() {
//        try {
//            List<Animal> animalList = animalApiService.call(); // API 호출로 새로운 데이터 받아옴
//            animalService.delete(); // 기존 데이터 삭제
//            animalService.insertAll(animalList); // 새로운 데이터 삽입
//
//            lastExecutionTime = LocalDateTime.now();
//            writeLastExecutionTime(lastExecutionTime);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private LocalDateTime readLastExecutionTime() {
//        File file = new File(LAST_EXECUTION_TIME_FILE);
//        if (!file.exists()) {
//            return null;
//        }
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String lastExecutionTimeStr = reader.readLine();
//            if (lastExecutionTimeStr != null) {
//                return LocalDateTime.parse(lastExecutionTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private void writeLastExecutionTime(LocalDateTime lastExecutionTime) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LAST_EXECUTION_TIME_FILE))) {
//            writer.write(lastExecutionTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String convertSexCd(String sexCd) {
//        switch (sexCd) {
//            case "M":
//                return "남";
//            case "F":
//                return "여";
//            default:
//                return "-";
//        }
//    }
//
//    private String convertNeuterYn(String neuterYn) {
//        List<String> validValues = Arrays.asList("Y", "N", "U");
//        if (validValues.contains(neuterYn)) {
//            switch (neuterYn) {
//                case "Y":
//                    return "완료";
//                case "N":
//                    return "미완료";
//                case "U":
//                    return "미상";
//            }
//        }
//        return "-";
//    }
//}
