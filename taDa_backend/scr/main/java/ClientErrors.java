package oth.ics.wtp.taDa_backend;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientErrors {
    private static final Logger logger = LoggerFactory.getLogger(ClientErrors.class);

    public static ResponseStatusException unauthorized() {
        return log(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "valid Basic credentials required"));
    }

    public static ResponseStatusException missingUpdateInfo() {
        return log(new ResponseStatusException(HttpStatus.BAD_REQUEST, "missing update information of user"));

    }

        public static ResponseStatusException postNotFound(long id) {
        return log(new ResponseStatusException(HttpStatus.NOT_FOUND, "post with id " + id));
    }

    public static ResponseStatusException commentNotFound(long id) {
        return log(new ResponseStatusException(HttpStatus.NOT_FOUND, "comment with id " + id));
    }

    public static ResponseStatusException userNotFound(String name) {
        return log(new ResponseStatusException(HttpStatus.NOT_FOUND, "user with name " + name));
    }

    public static ResponseStatusException userNameTaken(String name) {
        return log(new ResponseStatusException(HttpStatus.BAD_REQUEST, "user name already taken: " + name));
    }
    public static ResponseStatusException userLikeOwnPost() {
        return log(new ResponseStatusException(HttpStatus.BAD_REQUEST, "user likes his/her own post "));
    }
    public static ResponseStatusException userDeleteOtherComment() {
        return log(new ResponseStatusException(HttpStatus.BAD_REQUEST, "user try to delete other user 's comment"));
    }
    public static ResponseStatusException invalidFollow(String followedUserName) {
        return log(new ResponseStatusException(HttpStatus.BAD_REQUEST, " invalid follow" + followedUserName));
    }

    public static ResponseStatusException invalidCredentials() {
        return log(new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid credentials"));
    }

    private static ResponseStatusException log(ResponseStatusException e) {
        logger.error(ExceptionUtils.getMessage(e) + "\n" + ExceptionUtils.getStackTrace(e));
        return e;
    }
}
