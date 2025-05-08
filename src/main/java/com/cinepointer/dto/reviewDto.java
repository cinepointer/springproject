package com.cinepointer.dto;

import java.util.Date;
import lombok.Data;

@Data
public class reviewDto {
	 int reviewNum;        
    int userNum;          
    int movieNum;         
    String reviewTitle;
    String reviewContent;
    double reviewRating;
    Date reviewTime;

}
