package kpfu.itis.g804.bots_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "game_session")
public class Session {
    @Id
    private Long id;
    private int lastId;
    private boolean isGuessed;
    private Long gameStarterId;
    private String imgUrl;
    private String answer;
    private boolean isOpen;
}
