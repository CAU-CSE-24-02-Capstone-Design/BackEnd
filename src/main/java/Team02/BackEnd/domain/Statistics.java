package Team02.BackEnd.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Statistics extends BaseEntity {
    @Id
    @Column(name = "statistics_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gantourCount;
    private Long silentTime;
    private Long wrongWordCount;
    private Long wrongContextCount;

    @OneToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;
}
