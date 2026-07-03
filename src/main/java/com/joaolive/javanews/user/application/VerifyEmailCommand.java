package com.joaolive.javanews.user.application;

public record VerifyEmailCommand(String email, String code) {}
