package com.shixi.hotelmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeleteDTO {
    private ArrayList ids;
}
