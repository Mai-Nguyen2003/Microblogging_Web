package oth.ics.wtp.taDa_backend.dtos;
import java.time.Instant;
import java.util.List;

public record PostDto(long id, String messageText, Instant createdAt, AppUserBriefDto user,List<CommentBriefDto> comments, List<LikeBriefDto> likes) {
}
