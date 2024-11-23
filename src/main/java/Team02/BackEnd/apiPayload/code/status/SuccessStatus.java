package Team02.BackEnd.apiPayload.code.status;


import Team02.BackEnd.apiPayload.code.BaseCode;
import Team02.BackEnd.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    _OK(HttpStatus.OK, "COMMON200", "성공"),

    //Reissue
    REISSUE_TOKEN(HttpStatus.OK, "REISSUE2000", "토큰 재발급 성공"),

    //User
    OAUTH_REDIRECT(HttpStatus.OK, "OAUTH2000", "소셜 로그인 리다이렉트 성공"),
    OAUTH_LOGIN(HttpStatus.OK, "OAUTH2001", "소셜 로그인 성공"),
    SIGN_OUT_USER(HttpStatus.OK, "USER2000", "회원 탈퇴 성공"),

    //Answer
    SAVE_INSIGHT(HttpStatus.OK, "ANSWER2000", "인사이트 저장 성공"),
    GET_INSIGHT(HttpStatus.OK, "ANSWER2001", "인사이트 가져오기 성공"),
    CHECK_TODAY_ANSWER_EXISTS(HttpStatus.OK, "ANSWER2002", "오늘 답변 했는 지 여부 가져오기 성공"),
    SAVE_EVALUATION(HttpStatus.OK, "ANSWER2003", "스스로 평가 저장 성공"),
    GET_EVALUATION(HttpStatus.OK, "ANSWER2004", "스스로 평가 가져오기 성공"),

    //Record
    SAVE_VOICE_URL(HttpStatus.OK, "RECORD2000", "첫 로그인 시 녹음 파일 저장 성공"),
    SAVE_BEFORE_AUDIO_LINK(HttpStatus.OK, "RECORD2001", "1분 스피치 녹음 파일 저장 성공"),

    //Question
    GET_QUESTION(HttpStatus.OK, "QUESTION2000", "질문 가져오기 성공"),

    //Feedback
    SAVE_FEEDBACK(HttpStatus.OK, "FEEDBACK2000", "피드백 저장 성공"),
    GET_FEEDBACK(HttpStatus.OK, "FEEDBACK2001", "피드백 가져오기 성공"),

    //Statistics
    SAVE_STATISTICS(HttpStatus.OK, "STATISTICS2000", "통계 횟수 저장 성공"),
    GET_DATES_WHEN_USER_DID(HttpStatus.OK, "USER2000", "유저가 참여한 날짜 가져오기 성공"),
    GET_STATISTICS(HttpStatus.OK, "STATISTICS2001", "유저 통계 데이터 가져오기 성공"),

    //SelfFeedback
    SAVE_SELF_FEEDBACK(HttpStatus.CREATED, "SELFFEEDBACK2000", "셀프 피드백 저장 성공"),
    GET_SELF_FEEDBACK(HttpStatus.OK, "SELFFEEDBACK2001", "셀프 피드백 가져오기 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}
