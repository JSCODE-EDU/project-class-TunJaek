package com.example.jscode.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table (name ="board_tb")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="boardTitle",nullable = false)
    private String boardTitle;

    @Column(name="boardContents",nullable = false)
    private String boardContents;


}
