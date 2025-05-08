package com.cinepointer.dto;

import java.util.Date;
import lombok.Data;

@Data
public class reviewDto {
	private int reviewNum;        
	private int userNum;          
	private int movieNum;         
	private String reviewTitle;
	private String reviewContent;
	private double reviewRating;
	private Date reviewTime;

}
