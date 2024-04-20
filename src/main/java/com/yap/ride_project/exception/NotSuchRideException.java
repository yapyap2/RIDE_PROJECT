package com.yap.ride_project.exception;

public class NotSuchRideException extends RuntimeException{
    public  NotSuchRideException(long rideId) {
        super("주어진 id에 해당하는 라이드가 없습니다. id: " + rideId);
    }
}
