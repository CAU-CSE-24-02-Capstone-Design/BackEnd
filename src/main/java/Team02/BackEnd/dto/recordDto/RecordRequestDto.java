package Team02.BackEnd.dto.recordDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RecordRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetVoiceUrlDto {
        String voiceUrl;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetRespondDto {
        Long answerId;
        String beforeAudioLink;
    }
}
