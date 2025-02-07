package Team02.BackEnd.apiPayload;

import Team02.BackEnd.apiPayload.code.SuccessCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 성공한 경우 result 있을 때
    public static <T> ApiResponse<T> of(final SuccessCode code, final T result) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }

    // 성공한 경우 result 없을 때
    public static <T> ApiResponse<T> ofNoting(final SuccessCode code) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), null);
    }

    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(final String code, final String message, final T data) {
        return new ApiResponse<>(true, code, message, data);
    }
}
