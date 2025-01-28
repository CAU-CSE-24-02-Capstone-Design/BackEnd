package Team02.BackEnd.dto.feedbackDto;

import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.dto.userDto.UserDto.UserVoiceDto;
import java.util.List;

public record FeedbackApiDataDto(Feedback feedback, String beforeAudioLink, List<String> pastAudioLinks,
                                 UserVoiceDto userData) {
}
