package com.sds.animalapp.model.shelter;

import com.sds.animalapp.domain.Shelter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelterApiScheduler {

    private final ShelterApiService shelterApiService;

    private final ShelterService shelterService;

    private static final String LAST_EXECUTION_TIME_FILE = "shelterlastExecutionTime.txt";
    private LocalDateTime lastExecutionTime;

    @PostConstruct
    public void init() {
        // 마지막 실행 시간을 파일에서 읽어옴
        lastExecutionTime = readLastExecutionTime();
        if (lastExecutionTime == null || lastExecutionTime.isBefore(LocalDateTime.now().minusDays(1))) {
            // 마지막 실행 시간이 없거나, 마지막 실행 시간이 하루 이상 지난 경우
            callShelterApiAndUpdateLastExecutionTime();
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
    public void fetchData() {
        callShelterApiAndUpdateLastExecutionTime();
    }

    private void callShelterApiAndUpdateLastExecutionTime() {
        try {
            List<Shelter> shelterAllList = shelterApiService.getShelterList();

            // null 체크 및 삭제 후 삽입
            if (shelterAllList != null && !shelterAllList.isEmpty()) {
                shelterService.delete(shelterAllList);
                shelterService.insert(shelterAllList);
            }

            // 모든 레코드를 불러와 null 확인
            List<Shelter> allShelterList = shelterService.getAllRecord();
            if (allShelterList != null && !allShelterList.isEmpty()) {
                shelterService.mapSigngu(allShelterList);
            }

            // 마지막 실행 시간 기록
            lastExecutionTime = LocalDateTime.now();
            writeLastExecutionTime(lastExecutionTime);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // 예상치 못한 오류도 처리
            e.printStackTrace();
        }
    }


    private LocalDateTime readLastExecutionTime() {
        File file = new File(LAST_EXECUTION_TIME_FILE);
        if (!file.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String lastExecutionTimeStr = reader.readLine();
            if (lastExecutionTimeStr != null) {
                return LocalDateTime.parse(lastExecutionTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeLastExecutionTime(LocalDateTime lastExecutionTime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LAST_EXECUTION_TIME_FILE))) {
            writer.write(lastExecutionTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
