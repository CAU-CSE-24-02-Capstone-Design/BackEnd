package Team02.BackEnd.service.feedback;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_HEADER_NAME;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;

import Team02.BackEnd.converter.FeedbackConverter;
import Team02.BackEnd.dto.feedbackDto.FeedbackRequestDto.GetComponentToMakeFeedbackDto;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import Team02.BackEnd.dto.userDto.UserDto.UserVoiceDto;
import Team02.BackEnd.validator.FeedbackValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.NEVER)
public class FeedbackApiService {

    private static final String FASTAPI_API_URL = "https://peachmentor.com/api/fastapi/records/feedbacks";

    private final FeedbackValidator feedbackValidator;
    private final RestTemplate restTemplate;

    public GetFeedbackToFastApiDto getFeedbackFromFastApi(final String accessToken,
                                                          final String beforeAudioLink,
                                                          final List<String> pastAudioLinks,
                                                          final UserVoiceDto userData,
                                                          final Long answerId) {
        GetComponentToMakeFeedbackDto getComponentToMakeFeedbackDto =
                FeedbackConverter.toGetComponentToMakeFeedback(beforeAudioLink, userData, pastAudioLinks, answerId);
        ResponseEntity<GetFeedbackToFastApiDto> response = this.makeApiCallToFastApi(accessToken,
                getComponentToMakeFeedbackDto);
        feedbackValidator.validateResponseFromFastApi(response.getBody());
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
}
