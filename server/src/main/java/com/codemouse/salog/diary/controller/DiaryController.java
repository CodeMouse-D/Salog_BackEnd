package com.codemouse.salog.diary.controller;

import com.codemouse.salog.diary.dto.DiaryDto;
import com.codemouse.salog.diary.service.DiaryService;
import com.codemouse.salog.dto.MultiResponseDto;
import com.codemouse.salog.dto.SingleResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/diary")
@Validated
@AllArgsConstructor
@Slf4j
public class DiaryController {
    private final DiaryService diaryService;

    // post
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void createDiary (@RequestHeader(name = "Authorization") String token,
                             @Valid @RequestBody DiaryDto.Post requestBody){
        diaryService.postDiary(token, requestBody);
    }

    // patch
    @PatchMapping("/update/{diary-id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateDiary (@RequestHeader(name = "Authorization") String token,
                             @PathVariable("diary-id") @Positive long diaryId,
                             @Valid @RequestBody DiaryDto.Patch requestBody){
        diaryService.patchDiary(token, diaryId, requestBody);
    }

    // get
    @GetMapping("/{diary-id}")
    public ResponseEntity<?> getDiary (@RequestHeader(name = "Authorization") String token,
                                    @PathVariable("diary-id") @Positive long diaryId){
        DiaryDto.Response response = diaryService.findDiary(token, diaryId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // total list get
    @GetMapping
    public ResponseEntity<?> getAllDiaries (@RequestHeader(name = "Authorization") String token,
                                        @Positive @RequestParam int page,
                                        @Positive @RequestParam int size,
                                        @Valid @RequestParam(required = false) String diaryTag,
                                        @RequestParam(required = false) String date){
        MultiResponseDto<DiaryDto.Response> pageDiaries =
                diaryService.findAllDiaries(token, page, size, diaryTag, date);

        return new ResponseEntity<>(pageDiaries, HttpStatus.OK);
    }

    // 제목, 내용, 제목+내용 검색으로 일기 리스트 조회
    @GetMapping("/search")
    public ResponseEntity<?> getSearchDiaries (@RequestHeader(name = "Authorization") String token,
                                               @Positive @RequestParam int page,
                                               @Positive @RequestParam int size,
                                               @Valid @RequestParam String searchType,
                                               @Valid @RequestParam String query){

        MultiResponseDto<DiaryDto.Response> pageDiaries =
                diaryService.findSearchDiaries(token, page, size, searchType, query);

        return new ResponseEntity<>(pageDiaries, HttpStatus.OK);
    }

    // diaryCalendar get
    @GetMapping("/calendar")
    public ResponseEntity<?> getDiaryCalendar(@RequestHeader(name = "Authorization") String token,
                                              @RequestParam String date){
        List<DiaryDto.ResponseCalender> response = diaryService.getDiaryCalendar(token, date);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/delete/{diary-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiary (@RequestHeader(name = "Authorization") String token,
                             @PathVariable("diary-id") @Positive long diaryId){
        diaryService.deleteDiary(token, diaryId);
    }
}
