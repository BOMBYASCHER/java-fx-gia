package io.hexlet.javafx.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vendor {
    private Long id;
    private String type;
    private String name;
    private String director;
    private String email;
    private String phone;
    private String address;
    private String inn;
    private Integer rating;
}
