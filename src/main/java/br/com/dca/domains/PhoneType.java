package br.com.dca.domains;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum PhoneType {
    HOME,
    WORK,
    MOBILE
}
