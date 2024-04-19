package com.yap.ride_project.exception;

public class NotSuchUserException extends RuntimeException{
    public NotSuchUserException(long id) {
        super("주어진 id에 해당하는 유저가 없습니다. id: " + id);
    }
}
