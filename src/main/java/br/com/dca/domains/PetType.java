package br.com.dca.domains;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum PetType {
    DOG,
    CAT,
    FISH,
    BIRD,
    PIG,
    GUINEA_PIG
}
