package Team02.BackEnd.service.analysis;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_HEADER_NAME;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.FeedbackHandler;
import Team02.BackEnd.converter.FeedbackConverter;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.analysisDto.AnalysisRequestDto.GetComponentToMakeAnalysisDto;
import Team02.BackEnd.dto.feedbackDto.FeedbackRequestDto.GetComponentToMakeFeedbackDto;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import Team02.BackEnd.service.user.UserCheckService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
@Service
public class AnalysisApiService {
    private static final String FASTAPI_API_URL = "https://peachmentor.com/api/fastapi/records/analyses";
    private static final String FASTAPI_API_URL_LOCAL = "http://localhost:8000/api/fastapi/records/analyses";
    private static final int NUMBER_OF_USER_SPEECH = 5;

    private final UserCheckService userCheckService;

    private final RestTemplate restTemplate;

    public void createAnalysis(String accessToken) {
        User user = userCheckService.getUserByToken(accessToken);


    }

    public GetFeedbackToFastApiDto getAnalysisFromFastApi(final String accessToken,
                                                          final List<String> questions,
                                                          final List<String> beforeScripts,
                                                          final User user) {

        GetComponentToMakeAnalysisDto getComponentToMakeAnalysisDto =
                FeedbackConverter.toGetComponentToMakeFeedback(beforeAudioLink, user, pastAudioLinks,
                        answerId);
        ResponseEntity<GetFeedbackToFastApiDto> response = makeApiCallToFastApi(accessToken,
                getComponentToMakeAnalysisDto);
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
