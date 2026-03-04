package com.ringkhang.quizmindweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class ReTestSubmitRequest {
    private List<Questions> questions;
    private int hisId;
}
