package com.example.medchannel.dto;

public record ResponseDTO<T>(String message, T content) {
}
