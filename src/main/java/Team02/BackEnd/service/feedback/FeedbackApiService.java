package Team02.BackEnd.service.feedback;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_HEADER_NAME;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.FeedbackHandler;
import Team02.BackEnd.converter.FeedbackConverter;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.feedbackDto.FeedbackRequestDto.GetComponentToMakeFeedbackDto;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackApiService {

    private static final String FASTAPI_API_URL = "https://peachmentor.com/api/fastapi/records/feedbacks";
    private static final String FASTAPI_API_URL_LOCAL = "http://localhost:8000/api/fastapi/records/feedbacks";

    private final RestTemplate restTemplate;

    public GetFeedbackToFastApiDto getFeedbackFromFastApi(final String accessToken,
                                                          final String beforeAudioLink,
                                                          final List<String> pastAudioLinks,
                                                          final User user,
                                                          final Long answerId) {

        GetComponentToMakeFeedbackDto getComponentToMakeFeedbackDto =
                FeedbackConverter.toGetComponentToMakeFeedback(beforeAudioLink, user, pastAudioLinks,
                        answerId);
        ResponseEntity<GetFeedbackToFastApiDto> response = makeApiCallToFastApi(accessToken,
                getComponentToMakeFeedbackDto);
        validateFeedbackFromFastApi(response);
        return response.getBody();
    }

    private ResponseEntity<GetFeedbackToFastApiDto> makeApiCallToFastApi(final String accessToken,
                                                                         final GetComponentToMakeFeedbackDto getComponentToMakeFeedbackDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(ACCESS_TOKEN_HEADER_NAME, ACCESS_TOKEN_PREFIX + accessToken);
        HttpEntity<GetComponentToMakeFeedbackDto> request = new HttpEntity<>(getComponentToMakeFeedbackDto,
                headers);
        return restTemplate.postForEntity(FASTAPI_API_URL, request, GetFeedbackToFastApiDto.class);
    }

    private void validateFeedbackFromFastApi(final ResponseEntity<GetFeedbackToFastApiDto> response) {
        if (response.getBody() == null) {
            throw new FeedbackHandler(ErrorStatus._FAST_API_FEEDBACK_NULL);
        }
    }
}
