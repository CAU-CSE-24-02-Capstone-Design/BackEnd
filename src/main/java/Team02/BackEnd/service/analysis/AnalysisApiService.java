package Team02.BackEnd.service.analysis;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_HEADER_NAME;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;

import Team02.BackEnd.converter.AnalysisConverter;
import Team02.BackEnd.dto.analysisDto.AnalysisRequestDto.GetComponentToMakeAnalysisDto;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAnalysisFromFastApiDto;
import Team02.BackEnd.validator.AnalysisValidator;
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

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(propagation = Propagation.NEVER)
public class AnalysisApiService {
    private static final String FASTAPI_API_URL = "https://peachmentor.com/api/fastapi/records/analyses";

    private final AnalysisValidator analysisValidator;
    private final RestTemplate restTemplate;

    public GetAnalysisFromFastApiDto getAnalysisFromFastApi(final String accessToken,
                                                            final List<String> questions,
                                                            final List<String> beforeScripts) {
//        List<List<String>> dummyAnalysisText = List.of(
//                List.of("Dummy analysis line 1 for user", "Dummy analysis line 2 for user"),
//                List.of("Dummy analysis line 3 for user", "Dummy analysis line 4 for user")
//        );
//        GetAnalysisFromFastApiDto response = GetAnalysisFromFastApiDto.builder()
//                .analysisText(dummyAnalysisText)
//                .build();
//        return response;

        GetComponentToMakeAnalysisDto getComponentToMakeAnalysisDto =
                AnalysisConverter.toGetComponentToMakeAnalysisDto(questions, beforeScripts);
        ResponseEntity<GetAnalysisFromFastApiDto> response = makeApiCallToFastApi(accessToken,
                getComponentToMakeAnalysisDto);
        analysisValidator.validateResponseFromFastApi(response.getBody());
        return response.getBody();
    }

    private ResponseEntity<GetAnalysisFromFastApiDto> makeApiCallToFastApi(final String accessToken,
                                                                           final GetComponentToMakeAnalysisDto componentToMakeAnalysisDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(ACCESS_TOKEN_HEADER_NAME, ACCESS_TOKEN_PREFIX + accessToken);
        HttpEntity<GetComponentToMakeAnalysisDto> request = new HttpEntity<>(componentToMakeAnalysisDto,
                headers);
        return restTemplate.postForEntity(FASTAPI_API_URL, request, GetAnalysisFromFastApiDto.class);
    }
}
