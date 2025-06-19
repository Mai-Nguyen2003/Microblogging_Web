package oth.ics.wtp.taDa_backend.dtos;

import java.time.Instant;

public record CommentDto(long id, String textComment, Instant createdAt, AppUserBriefDto user, PostBriefDto post) {
}
