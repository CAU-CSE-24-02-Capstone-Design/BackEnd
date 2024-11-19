package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.AnswerConverter;
import Team02.BackEnd.dto.AnswerResponseDto;
import Team02.BackEnd.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("/answers/completions")
    @Operation(summary = "오늘 답변 했는지 여부 받아오기 react -> spring", description = "오늘 답변 했는지 여부 받아오기")
    public ApiResponse<AnswerResponseDto.AnswerExistsDto> doAnswerToday(
            @RequestHeader("Authorization") final String authorizationHeader) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        Boolean isAnswerExists = answerService.doAnswerToday(accessToken);
        return ApiResponse.of(SuccessStatus.CHECK_TODAY_ANSWER_EXISTS,
                AnswerConverter.toAnswerExistsDto(isAnswerExists));
    }
}
